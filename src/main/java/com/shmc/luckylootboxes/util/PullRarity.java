package com.shmc.luckylootboxes.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public enum PullRarity {
    LEGENDARY(1),
    EPIC(4),
    RARE(15),
    UNCOMMON(20),
    COMMON(60);

    final int probability;

    PullRarity(int probability) {
        this.probability = probability;
    }

    public int getProbability() {
        return this.probability;
    }

    public static PullRarity getRarity(int probability) {
        for (PullRarity rarity : PullRarity.values()) {
            if (rarity.getProbability() > probability) {
                return rarity;
            }
        }
        return PullRarity.COMMON;
    }
}
