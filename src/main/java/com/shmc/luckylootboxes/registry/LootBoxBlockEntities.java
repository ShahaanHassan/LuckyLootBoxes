package com.shmc.luckylootboxes.registry;

import com.shmc.luckylootboxes.block.entity.LootBoxBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.shmc.luckylootboxes.LuckyLootBoxes.LOGGER;
import static com.shmc.luckylootboxes.LuckyLootBoxes.MOD_ID;
import static com.shmc.luckylootboxes.registry.LootBoxBlocks.BEGINNER_LOOT_BOX_BLOCK;

public final class LootBoxBlockEntities {
    public static final Map<String, BlockEntityType<?>> BLOCK_ENTITIES = new HashMap<>();
    public static final BlockEntityType<?> LOOT_BOX_BLOCK_ENTITY = createBlockEntityType("beginner_loot_box_block_entity", LootBoxBlockEntity::new, BEGINNER_LOOT_BOX_BLOCK);


    private LootBoxBlockEntities() {}

    private static <T extends BlockEntity> BlockEntityType<? extends T> createBlockEntityType(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... block) {
        if (block.length == 0) {
            LOGGER.warn("Block entity: {} is not registered to any blocks", id);
        }
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
