package com.shmc.luckylootboxes.item;

import net.minecraft.item.Item;

import static com.shmc.luckylootboxes.registry.LootBoxItems.COPPER_MINECRAFT_COIN_ITEM;

public abstract class AbstractCopperCoinBundle extends AbstractCoinBundleItem {

    protected AbstractCopperCoinBundle(Settings settings, int coinReward) {
        super(settings, coinReward);
    }

    @Override
    public Item getCoinItem() {
        return COPPER_MINECRAFT_COIN_ITEM;
    }
}
