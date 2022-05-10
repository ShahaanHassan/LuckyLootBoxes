package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.api.ItemEntryAccessor;
import com.shmc.luckylootboxes.api.LeafEntryAccessor;
import com.shmc.luckylootboxes.api.LootTableEntryAccessor;
import com.shmc.luckylootboxes.block.entity.LootBoxBlockEntity;
import com.shmc.luckylootboxes.enums.PullRarity;
import net.fabricmc.fabric.api.loot.v1.FabricLootPool;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootPoolEntryTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;
import static com.shmc.luckylootboxes.registry.LootBoxBlockEntities.LOOT_BOX_BLOCK_ENTITY;

public abstract class AbstractLootBoxBlock extends BlockWithEntity {

  public static final EnumProperty<PullRarity> LOOTBOX_RARITY = EnumProperty.of("rarity", PullRarity.class);

  private final Random seed;

  protected AbstractLootBoxBlock(Settings settings) {
    super(settings);
    this.seed = new Random();
    setDefaultState(getStateManager().getDefaultState().with(LOOTBOX_RARITY, PullRarity.BASE));
  }

  @Override
  public ActionResult onUse(
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockHitResult blockHitResult) {
    LootBoxBlockEntity be = (LootBoxBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos));
    if (world.isClient) {
      return ActionResult.SUCCESS;
    } else if (!be.getRolling() && hand == Hand.MAIN_HAND && canPull(player.getInventory().getMainHandStack())) {
      LootTable lootTable = getLootTable(getLootTableIdentifier(), world);
      LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
              .random(seed)
              .parameter(LootContextParameters.THIS_ENTITY, player)
              .luck(player.getLuck());
      List<ItemStack> loot = lootTable.generateLoot(
              builder.build(lootTable.getType()));
      if (!loot.isEmpty()) {
        ItemStack drop = loot.get(0);
        PullRarity pullRarity = getRarity(lootTable, drop, world);
        world.playSound(null, pos, getSoundEvent(pullRarity), SoundCategory.MASTER, 1f, 1f);
        world.setBlockState(pos, state.with(LOOTBOX_RARITY, pullRarity));
        be.setPulling(pullRarity);
        dropReward(world, pos, drop);
        if (!player.isCreative()) {
          player.getInventory().getMainHandStack().decrement(ticketCost());
        }
      }
    }
    return ActionResult.CONSUME;
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new LootBoxBlockEntity(pos, state);
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return checkType(type, LOOT_BOX_BLOCK_ENTITY, LootBoxBlockEntity::tick);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  protected abstract boolean correctTicket(ItemStack itemStack);

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(LOOTBOX_RARITY);
  }

  protected boolean canPull(ItemStack itemStack) {
    return correctTicket(itemStack) && haveEnough(itemStack);
  }

  protected boolean haveEnough(ItemStack itemStack) {
    return itemStack.getCount() >= ticketCost();
  }

  protected int ticketCost() {
    return 1;
  }

  private LootTable getLootTable(Identifier id, World world) {
    return Objects.requireNonNull(world.getServer())
            .getLootManager()
            .getTable(id);
  }

  private PullRarity getRarity(LootTable lootTable, ItemStack itemStack, World world) {
    Optional<LootPoolEntry> itemEntry = findInLootTable(lootTable, itemStack, world);
    return itemEntry.map(entry -> PullRarity.getRarity(((LeafEntryAccessor) entry).getWeight())).orElse(PullRarity.COMMON);
  }

  private Optional<LootPoolEntry> findInLootTable(LootTable lootTable, ItemStack itemStack, World world) {
    return ((FabricLootSupplier) lootTable).getPools().stream()
            .findFirst().flatMap(pool -> ((FabricLootPool) pool).getEntries().stream()
                    .filter(entry -> filterEntry(entry, itemStack, world)).findFirst());
  }

  private boolean filterEntry(LootPoolEntry entry, ItemStack itemStack, World world) {
    if (entry.getType() == LootPoolEntryTypes.ITEM) {
      return ((ItemEntryAccessor) entry).getItem() == itemStack.getItem();
    } else if (entry.getType() == LootPoolEntryTypes.LOOT_TABLE) {
      LootTable lootTable = getLootTable(((LootTableEntryAccessor) entry).getId(), world);
      return ((FabricLootSupplier) lootTable).getPools().stream()
              .findFirst().filter(pool -> ((FabricLootPool) pool).getEntries().stream()
                      .anyMatch(e -> filterEntry(e, itemStack, world))).isPresent();
    }
    // Only Item and LootTable entries are supported
    return false;
  }

  private Identifier getLootTableIdentifier() {
    return new Identifier(
        MOD_ID, String.format("loot_box/%s", Registry.BLOCK.getId(this).getPath()));
  }

  private void dropReward(World world, BlockPos pos, ItemStack stack) {
    double x = pos.getX() + 0.5f;
    double y = pos.getY() + 1.0f - EntityType.ITEM.getHeight() / 2.0f;
    double z = pos.getZ() + 0.5f;
    if (!world.isClient() && !stack.isEmpty()) {
      ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);
      itemEntity.setToDefaultPickupDelay();
      world.spawnEntity(itemEntity);
    }
  }

  private SoundEvent getSoundEvent(PullRarity pullRarity) {
    return switch (pullRarity) {
      case LEGENDARY -> SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE;
      case EPIC -> SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME;
      case RARE -> SoundEvents.BLOCK_HONEY_BLOCK_HIT;
      case UNCOMMON -> SoundEvents.BLOCK_METAL_HIT;
      default -> SoundEvents.BLOCK_BONE_BLOCK_HIT;
    };
  }
}
