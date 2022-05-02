package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.block.entity.BeginnerLootBoxEntity;
import com.shmc.luckylootboxes.item.BeginnerTicketItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.shmc.luckylootboxes.registry.LootBoxBlockEntities.BEGINNER_LOOT_BOX_ENTITY;

public class BeginnerLootBoxBlock extends AbstractLootBoxBlock {

  public BeginnerLootBoxBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean correctTicket(ItemStack itemStack) {
    return itemStack.getItem() instanceof BeginnerTicketItem;
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new BeginnerLootBoxEntity(pos, state);
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return checkType(type, BEGINNER_LOOT_BOX_ENTITY, BeginnerLootBoxEntity::tick);
  }
}
