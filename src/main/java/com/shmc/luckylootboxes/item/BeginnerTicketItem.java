package com.shmc.luckylootboxes.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

import static com.shmc.luckylootboxes.util.StylesUtil.getGoldTooltip;

public class BeginnerTicketItem extends Item {

  public BeginnerTicketItem(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(
      ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(getGoldTooltip("item.luckylootboxes.beginner_ticket_tooltip"));
  }
}
