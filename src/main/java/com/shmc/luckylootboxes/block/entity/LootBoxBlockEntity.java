package com.shmc.luckylootboxes.block.entity;

import com.shmc.luckylootboxes.enums.PullRarity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import javax.annotation.Nullable;

import static com.shmc.luckylootboxes.block.AbstractLootBoxBlock.LOOTBOX_RARITY;
import static com.shmc.luckylootboxes.registry.LootBoxBlockEntities.LOOT_BOX_BLOCK_ENTITY;

public class LootBoxBlockEntity extends BlockEntity {
  private boolean rolling;
  private int timer;

  public LootBoxBlockEntity(BlockPos pos, BlockState state) {
    super(LOOT_BOX_BLOCK_ENTITY, pos, state);
    resetState();
  }

  public void decrementTimer() {
    this.timer -= 1;
  }

  public boolean getRolling() {
    return this.rolling;
  }

  public int getTimer() {
    return this.timer;
  }

  public void resetState() {
    setState(false);
  }

  public void setPulling() {
    setState(true);
  }

  public void setState(boolean rolling) {
    this.rolling = rolling;
    this.timer = 20;
  }

  @Override
  public void readNbt(NbtCompound tag) {
    super.readNbt(tag);
    rolling = tag.getBoolean("rolling");
    timer = tag.getInt("timer");
  }

  @Override
  public void writeNbt(NbtCompound tag) {
    tag.putBoolean("rolling", rolling);
    tag.putInt("timer", timer);
    super.writeNbt(tag);
  }

  @Nullable
  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt() {
    return createNbt();
  }

  public static void tick(World world, BlockPos pos, BlockState state, BlockEntity be) {
    LootBoxBlockEntity lbe = (LootBoxBlockEntity) be;
    if (lbe.getTimer() <= 0) {
      world.setBlockState(pos, state.with(LOOTBOX_RARITY, PullRarity.BASE));
      lbe.resetState();
    } else {
      lbe.decrementTimer();
    }
  }
}
