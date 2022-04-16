package com.shmc.luckylootboxes.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;
import static com.shmc.luckylootboxes.registry.LootBoxBlocks.BLOCKS;

public final class LootBoxItems {

  public static final ItemGroup LOOT_BOX_BLOCK_ITEM_GROUP = createBlockGroup();

  private LootBoxItems() {}

  private static ItemGroup createBlockGroup() {
    return FabricItemGroupBuilder.create(new Identifier(MOD_ID, "loot_box_blocks")).build();
  }

  public static void init() {
    createBlockItems();
  }

  private static void createBlockItems() {
    for (Map.Entry<String, Block> entry: BLOCKS.entrySet()) {
      Registry.register(Registry.ITEM, new Identifier(MOD_ID, entry.getKey()), createBlockItem(entry.getValue()));
    }
  }

  private static FabricItemSettings defaultBlockItemSettings() {
    return new FabricItemSettings().group(LOOT_BOX_BLOCK_ITEM_GROUP);
  }

  private static BlockItem createBlockItem(Block block) {
    return new BlockItem(block, defaultBlockItemSettings());
  }
}
