package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.block.BeginnerLootBoxBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public class LootBoxBlocks {

  private LootBoxBlocks() {}

  public static void registerLootBoxBlocks() {
    Registry.register(
        Registry.BLOCK,
        new Identifier(MOD_ID, "beginner_loot_box_block"),
        new BeginnerLootBoxBlock(FabricBlockSettings.of(Material.METAL)));
  }
}
