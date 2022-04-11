package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.item.BeginnerLootBoxItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public class LootBoxItems {

  private static final FabricItemGroupBuilder LOOTBOX_BLOCKS_BUILDER =
      FabricItemGroupBuilder.create(new Identifier(MOD_ID, "loot_boxes"));

  private LootBoxItems() {}

  public static void registerLootBoxItems() {

    BeginnerLootBoxItem beginnerLootBoxItem =
        Registry.register(
            Registry.ITEM,
            new Identifier(MOD_ID, "beginner_loot_box_block"),
            new BeginnerLootBoxItem(new FabricItemSettings()));

    LOOTBOX_BLOCKS_BUILDER
        .icon(() -> new ItemStack(beginnerLootBoxItem))
        .appendItems(itemStacks -> itemStacks.add(new ItemStack(beginnerLootBoxItem)))
        .build();
  }
}
