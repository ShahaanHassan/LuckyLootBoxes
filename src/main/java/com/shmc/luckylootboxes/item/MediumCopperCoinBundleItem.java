package com.shmc.luckylootboxes.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

import static com.shmc.luckylootboxes.util.TooltipStylesUtil.getGoldTooltip;

public class MediumCopperCoinBundleItem extends AbstractCopperCoinBundle {

    public MediumCopperCoinBundleItem(Settings settings) {
        super(settings, 35);
    }

    @Override
    public void appendTooltip(
            ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(getGoldTooltip("item.luckylootboxes.medium_copper_coin_bundle.tooltip"));
    }
}
