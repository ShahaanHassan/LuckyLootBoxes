package com.shmc.luckylootboxes.block;

import com.shmc.luckylootboxes.item.BeginnerTicketItem;
import net.minecraft.item.ItemStack;

public class BeginnerLootBoxBlock extends AbstractLootBoxBlock {

  public BeginnerLootBoxBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean correctTicket(ItemStack itemStack) {
    return itemStack.getItem() instanceof BeginnerTicketItem;
  }
}
