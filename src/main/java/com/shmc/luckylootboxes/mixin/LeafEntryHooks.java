package com.shmc.luckylootboxes.mixin;

import com.shmc.luckylootboxes.api.LeafEntryAccessor;
import net.minecraft.loot.entry.LeafEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LeafEntry.class)
public abstract class LeafEntryHooks implements LeafEntryAccessor {
  @Accessor
  public abstract int getWeight();
}
