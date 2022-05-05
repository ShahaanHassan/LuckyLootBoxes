package com.shmc.luckylootboxes.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

import static com.shmc.luckylootboxes.util.TooltipStylesUtil.getGoldTooltip;

public class LargeCopperCoinBundleItem extends AbstractCopperCoinBundle {

    public LargeCopperCoinBundleItem(Settings settings) {
        super(settings, 64);
    }

    @Override
    public void appendTooltip(
            ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(getGoldTooltip("item.luckylootboxes.large_copper_coin_bundle.tooltip"));
    }
}
