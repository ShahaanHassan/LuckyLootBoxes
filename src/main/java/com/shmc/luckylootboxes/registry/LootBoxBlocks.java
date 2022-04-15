package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.block.BeginnerLootBoxBlock;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public class LootBoxBlocks {

  public static final BeginnerLootBoxBlock BEGINNER_LOOT_BOX_BLOCK =
      new BeginnerLootBoxBlock(
          FabricBlockSettings.of(Material.METAL).strength(2.0f).requiresTool());

  public static final ItemGroup LOOT_BOX_BLOCK_ITEM_GROUP =
      FabricItemGroupBuilder.create(new Identifier(MOD_ID, "loot_box_blocks")).build();

  private LootBoxBlocks() {}

  public static void init() {
    Registry.register(
        Registry.BLOCK, new Identifier(MOD_ID, "beginner_loot_box_block"), BEGINNER_LOOT_BOX_BLOCK);

    Registry.register(
        Registry.ITEM,
        new Identifier(MOD_ID, "beginner_loot_box_block"),
        new BlockItem(
            BEGINNER_LOOT_BOX_BLOCK, new FabricItemSettings().group(LOOT_BOX_BLOCK_ITEM_GROUP)));
  }
}
