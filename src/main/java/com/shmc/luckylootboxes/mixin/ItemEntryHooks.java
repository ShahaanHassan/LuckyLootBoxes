package com.shmc.luckylootboxes.mixin;

import com.shmc.luckylootboxes.api.ItemEntryAccessor;
import net.minecraft.item.Item;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(ItemEntry.class)
public abstract class ItemEntryHooks implements ItemEntryAccessor {
  @Accessor
  public abstract Item getItem();
}
