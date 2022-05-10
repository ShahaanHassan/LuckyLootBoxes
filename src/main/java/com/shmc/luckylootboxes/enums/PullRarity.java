package com.shmc.luckylootboxes.enums;

import net.minecraft.util.StringIdentifiable;

public enum PullRarity implements StringIdentifiable {
    BASE("base", 0),
    LEGENDARY("legendary", 1),
    EPIC("epic", 5),
    RARE("rare", 19),
    UNCOMMON("uncommon", 25),
    COMMON("common", 50);

    private final String name;
    private final int probability;

    PullRarity(String name, int probability) {
        this.name = name;
        this.probability = probability;
    }

    public int getProbability() {
        return this.probability;
    }

    public static PullRarity getRarity(int probability) {
        for (PullRarity rarity : PullRarity.values()) {
            if (rarity.getProbability() >= probability) {
                return rarity;
            }
        }
        return PullRarity.COMMON;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
