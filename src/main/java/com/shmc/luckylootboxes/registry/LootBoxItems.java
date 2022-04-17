package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.item.CopperMinecraftCoinItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;
import static com.shmc.luckylootboxes.registry.LootBoxBlocks.BLOCKS;

public final class LootBoxItems {

  public static final Map<String, Item> ITEMS = new HashMap<>();

  private LootBoxItems() {}  public static final ItemGroup LOOT_BOX_ITEM_GROUP = createLootBoxItemGroup();

  private static void createItems() {
    for (Map.Entry<String, Item> entry : ITEMS.entrySet()) {
      Registry.register(Registry.ITEM, new Identifier(MOD_ID, entry.getKey()), entry.getValue());
    }
  }  public static final Item COPPER_MINECRAFT_COIN_ITEM =
      createCoinItem(
          "copper_minecraft_coin", () -> new CopperMinecraftCoinItem(getDefaultCoinItemSettings()));

  private static <T extends Item> Item createCoinItem(String id, Supplier<T> itemSupplier) {
    T item = itemSupplier.get();
    ITEMS.put(id, item);
    return item;
  }

  private static FabricItemSettings getDefaultCoinItemSettings() {
    return new FabricItemSettings().group(LOOT_BOX_ITEM_GROUP);
  }

  private static ItemGroup createLootBoxItemGroup() {
    return FabricItemGroupBuilder.create(new Identifier(MOD_ID, "loot_boxes"))
        .icon(() -> new ItemStack(COPPER_MINECRAFT_COIN_ITEM))
        .build();
  }

  private static void createBlockItems() {
    for (Map.Entry<String, Block> entry : BLOCKS.entrySet()) {
      Registry.register(
          Registry.ITEM, new Identifier(MOD_ID, entry.getKey()), createBlockItem(entry.getValue()));
    }
  }

  private static FabricItemSettings defaultBlockItemSettings() {
    return new FabricItemSettings().group(LOOT_BOX_ITEM_GROUP);
  }

  private static BlockItem createBlockItem(Block block) {
    return new BlockItem(block, defaultBlockItemSettings());
  }

  public static void init() {
    createBlockItems();
    createItems();
  }




}
