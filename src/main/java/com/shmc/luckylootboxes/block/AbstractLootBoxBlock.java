package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.LuckyLootBoxes;
import com.shmc.luckylootboxes.api.ItemEntryAccessor;
import com.shmc.luckylootboxes.api.LeafEntryAccessor;
import com.shmc.luckylootboxes.util.PullRarity;
import net.fabricmc.fabric.api.loot.v1.FabricLootPool;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public abstract class AbstractLootBoxBlock extends BlockWithEntity {

  private final Random seed;

  protected AbstractLootBoxBlock(Settings settings) {
    super(settings);
    this.seed = new Random();
  }

  @Override
  public ActionResult onUse(
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockHitResult blockHitResult) {
    if (hand == Hand.MAIN_HAND && canPull(player.getInventory().getMainHandStack())) {
      if (world.isClient) {
        return ActionResult.SUCCESS;
      } else {
        LootTable lootTable =
            Objects.requireNonNull(world.getServer())
                .getLootManager()
                .getTable(getLootTableIdentifier());
        LootContext.Builder builder =
            new LootContext.Builder((ServerWorld) world)
                .random(seed)
                .parameter(LootContextParameters.THIS_ENTITY, player)
                .luck(player.getLuck());
//        lootTable.generateLoot(
//            builder.build(lootTable.getType()), stack -> dropReward(world, pos, stack));
        List<ItemStack> loot = lootTable.generateLoot(
                builder.build(lootTable.getType()));
        ItemStack drop = loot.stream().findFirst().orElseGet(() -> new ItemStack(Items.DIRT));
        PullRarity a = getRarity(lootTable, drop);
        dropReward(world, pos, drop);
        LuckyLootBoxes.LOGGER.info(a.toString());
        if (!player.isCreative()) {
          player.getInventory().getMainHandStack().decrement(ticketCost());
        }
      }
    }
    return ActionResult.CONSUME;
  }

  private PullRarity getRarity(LootTable lt, ItemStack item) {
    Optional<LootPoolEntry> itemEntry = ((FabricLootSupplier) lt).getPools().stream()
            .findFirst().flatMap(pool -> ((FabricLootPool) pool).getEntries().stream()
                    .filter(entry -> ((ItemEntryAccessor) entry).getItem() == item.getItem()).findFirst());
    return itemEntry.map(e -> (PullRarity.getRarity(((LeafEntryAccessor) e).getWeight()))).orElse(PullRarity.COMMON);
  }

  protected boolean canPull(ItemStack itemStack) {
    return correctTicket(itemStack) && haveEnough(itemStack);
  }

  protected boolean haveEnough(ItemStack itemStack) {
    return itemStack.getCount() >= ticketCost();
  }

  protected abstract boolean correctTicket(ItemStack itemStack);

  protected int ticketCost() {
    return 1;
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

//  @Nullable
//  @Override
//  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
//    return new BeginnerLootBoxEntity(pos, state);
//  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }
}
