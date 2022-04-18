package com.shmc.luckylootboxes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public abstract class AbstractLootBoxBlock extends Block implements BlockEntityProvider {

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
        lootTable.generateLoot(
            builder.build(lootTable.getType()), stack -> dropReward(world, pos, stack));
        player.getInventory().getMainHandStack().decrement(ticketCost());
      }
    }
    return ActionResult.CONSUME;
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

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return null;
  }
}
