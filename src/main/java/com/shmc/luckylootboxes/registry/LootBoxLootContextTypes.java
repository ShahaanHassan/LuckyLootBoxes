package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.mixin.LootContextTypesInvoker;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;

import java.util.function.Consumer;

public final class LootBoxLootContextTypes {

  public static final LootContextType LOOT_BOX_CONTEXT =
      LootContextTypesInvoker.register("lootbox", createLootBoxLootContextType());

  private LootBoxLootContextTypes() {}

  private static Consumer<LootContextType.Builder> createLootBoxLootContextType() {
    return builder -> builder.require(LootContextParameters.THIS_ENTITY);
  }

  public static void init() {}
}
