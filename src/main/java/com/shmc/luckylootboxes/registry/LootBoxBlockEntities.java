package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.blockentity.BeginnerLootBoxEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;

public final class LootBoxBlockEntities {
    public static final Map<String, BlockEntityType<?>> BLOCK_ENTITIES = new HashMap<>();
    public static final BlockEntityType<?> BEGINNER_LOOT_BOX_ENTITY = createBlockEntityType("beginner_loot_box_block_entity", BeginnerLootBoxEntity::new);


    private LootBoxBlockEntities() {}

    private static <T extends BlockEntity> BlockEntityType<? extends T> createBlockEntityType(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... block) {
        BlockEntityType<? extends T> type = FabricBlockEntityTypeBuilder.create(factory, block).build(null);
        BLOCK_ENTITIES.put(id, type);
        return type;
    }

    public static void init() {
        for (Map.Entry<String, BlockEntityType<?>> entry: BLOCK_ENTITIES.entrySet()) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, entry.getKey()), entry.getValue());
        }
    }
}
