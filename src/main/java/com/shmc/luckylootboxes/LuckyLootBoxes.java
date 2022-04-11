package com.shmc.luckylootboxes;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.shmc.luckylootboxes.registry.LootBoxBlocks.registerLootBoxBlocks;
import static com.shmc.luckylootboxes.registry.LootBoxItems.registerLootBoxItems;

public class LuckyLootBoxes implements ModInitializer {

  public static final String MOD_ID = "luckylootboxes";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    LOGGER.info("Implementing Surprise Mechanics!");
    registerLootBoxBlocks();
    registerLootBoxItems();
  }
}
