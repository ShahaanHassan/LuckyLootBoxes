package com.shmc.luckylootboxes.mixin;

import com.shmc.luckylootboxes.accessors.LootTableEntryAccessor;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootTableEntry.class)
public abstract class LootTableEntryHooks implements LootTableEntryAccessor {
  @Accessor
  public abstract Identifier getId();
}
