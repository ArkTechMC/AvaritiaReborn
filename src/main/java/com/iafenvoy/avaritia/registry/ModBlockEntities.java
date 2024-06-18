package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.block.entity.ExtremeCraftingTableBlockEntity;
import com.iafenvoy.avaritia.item.block.entity.NeutronCollectorBlockEntity;
import com.iafenvoy.avaritia.item.block.entity.NeutroniumCompressorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ExtremeCraftingTableBlockEntity> EXTREME_CRAFTING_TABLE = register("extreme_crafting_table", FabricBlockEntityTypeBuilder.create(ExtremeCraftingTableBlockEntity::new, ModBlocks.EXTREME_CRAFTING_TABLE).build());
    public static final BlockEntityType<NeutronCollectorBlockEntity> NEUTRON_COLLECTOR = register("neutron_collector", FabricBlockEntityTypeBuilder.create(NeutronCollectorBlockEntity::new, ModBlocks.NEUTRON_COLLECTOR).build());
    public static final BlockEntityType<NeutroniumCompressorBlockEntity> NEUTRONIUM_COMPRESSOR = register("neutronium_compressor", FabricBlockEntityTypeBuilder.create(NeutroniumCompressorBlockEntity::new, ModBlocks.COMPRESSOR).build());

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(AvaritiaReborn.MOD_ID, name), type);
    }

    public static void init() {
    }
}
