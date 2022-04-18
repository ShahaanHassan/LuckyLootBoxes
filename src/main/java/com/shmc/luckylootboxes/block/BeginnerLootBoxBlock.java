package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.blockentity.BeginnerLootBoxEntity;
import com.shmc.luckylootboxes.item.BeginnerTicketItem;
import com.shmc.luckylootboxes.item.CopperMinecraftCoinItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

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
}
