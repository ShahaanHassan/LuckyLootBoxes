package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.blockentity.BeginnerLootBoxEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BeginnerLootBoxBlock extends AbstractLootBoxBlock {

  public BeginnerLootBoxBlock(Settings settings) {
    super(settings);
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new BeginnerLootBoxEntity(pos, state);
  }
}
