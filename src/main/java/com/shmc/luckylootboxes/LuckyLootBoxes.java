package com.shmc.luckylootboxes;

import com.shmc.luckylootboxes.registry.LootBoxBlockEntities;
import com.shmc.luckylootboxes.registry.LootBoxBlocks;
import com.shmc.luckylootboxes.registry.LootBoxItems;
import com.shmc.luckylootboxes.registry.LootBoxLootContextTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyLootBoxes implements ModInitializer {

  public static final String MOD_ID = "luckylootboxes";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    LOGGER.info("Implementing Surprise Mechanics!");
    LootBoxBlocks.init();
    LootBoxBlockEntities.init();
    LootBoxItems.init();
    LootBoxLootContextTypes.init();
  }
}
