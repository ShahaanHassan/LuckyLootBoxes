package com.shmc.luckylootboxes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import javax.annotation.Nullable;

import static com.shmc.luckylootboxes.LuckyLootBoxes.LOGGER;
import static com.shmc.luckylootboxes.registry.LootBoxBlockEntities.BEGINNER_LOOT_BOX_ENTITY;

public class BeginnerLootBoxEntity extends BlockEntity {

  private boolean rolling;
  private int timer;

  public BeginnerLootBoxEntity(BlockPos pos, BlockState state) {
    super(BEGINNER_LOOT_BOX_ENTITY, pos, state);
    resetState();
  }

  public void setRolling(boolean rolling) {
    this.rolling = rolling;
  }

  public void setTimer(int timer) {
    this.timer = timer;
  }

  public boolean getRolling() {
    return this.rolling;
  }

  public int getTimer() {
    return this.timer;
  }

  public void resetState() {
    this.rolling = false;
    this.timer = 500;
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
    BeginnerLootBoxEntity b = (BeginnerLootBoxEntity) be;
//    LOGGER.info(Integer.toString(b.getTimer()));
    if (b.getTimer() <= 0) {
      b.setTimer(50);
//      LOGGER.info("I have ticked");
    } else {
      b.setTimer(b.getTimer() - 1);
    }
    b.markDirty();
  }
}
