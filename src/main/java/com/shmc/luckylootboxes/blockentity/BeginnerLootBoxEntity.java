package com.shmc.luckylootboxes.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import static com.shmc.luckylootboxes.registry.LootBoxBlockEntities.BEGINNER_LOOT_BOX_ENTITY;

public class BeginnerLootBoxEntity extends BlockEntity {

  public BeginnerLootBoxEntity(BlockPos pos, BlockState state) {
    super(BEGINNER_LOOT_BOX_ENTITY, pos, state);
  }
}
