package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.block.BeginnerLootBoxBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public final class LootBoxBlocks {

  public static final Map<String, Block> BLOCKS = new HashMap<>();

  public static final Block BEGINNER_LOOT_BOX_BLOCK = createBlock("beginner_loot_box_block", () -> new BeginnerLootBoxBlock(defaultLootBlockSettings()));

  private LootBoxBlocks() {}

  private static FabricBlockSettings defaultLootBlockSettings() {
    return FabricBlockSettings.of(Material.METAL).strength(2.0f).requiresTool();
  }

  private static <T extends Block> Block createBlock(String id, Supplier<T> blockSupplier) {
    T block = blockSupplier.get();
    BLOCKS.put(id, block);
    return block;
  }

  public static void init() {
    for (Map.Entry<String, Block> entry: BLOCKS.entrySet()) {
      Registry.register(Registry.BLOCK, new Identifier(MOD_ID, entry.getKey()), entry.getValue());
    }
  }
}
