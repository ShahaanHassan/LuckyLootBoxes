package com.shmc.luckylootboxes.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class AbstractCoinBundleItem extends Item {

    private final int coinReward;

    public AbstractCoinBundleItem(Settings settings, int coinReward) {
        super(settings);
        this.coinReward = coinReward;
    }

    public abstract Item getCoinItem();

    @Override
    public int getMaxUseTime(ItemStack itemStack) {
        return 1;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player ) {
            if (!player.getInventory().insertStack(coinStack())) {
                player.dropStack(coinStack());
            }
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (stack.isEmpty()) {
                return coinStack();
            }
        }
        return stack;
      }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    private ItemStack coinStack() {
        return new ItemStack(this::getCoinItem, coinReward);
    }
}
