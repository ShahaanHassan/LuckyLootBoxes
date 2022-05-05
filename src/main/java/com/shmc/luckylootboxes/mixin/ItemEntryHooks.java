package com.shmc.luckylootboxes.mixin;

import com.shmc.luckylootboxes.api.ItemEntryAccessor;
import net.minecraft.item.Item;
import net.minecraft.loot.entry.ItemEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntry.class)
public abstract class ItemEntryHooks implements ItemEntryAccessor {
  @Accessor
  public abstract Item getItem();
}
