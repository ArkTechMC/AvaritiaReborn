package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.block.ExtremeCraftingTableBlock;
import com.iafenvoy.avaritia.item.block.NeutronCollectorBlock;
import com.iafenvoy.avaritia.item.block.NeutroniumCompressorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final Block EXTREME_CRAFTING_TABLE = register("extreme_crafting_table", new ExtremeCraftingTableBlock(FabricBlockSettings.create()));
    public static final Block NEUTRON_COLLECTOR = register("neutron_collector", new NeutronCollectorBlock(FabricBlockSettings.create()));
    public static final Block COMPRESSOR = register("compressor", new NeutroniumCompressorBlock(FabricBlockSettings.create()));
    public static final Block NEUTRONIUM_BLOCK = register("neutronium_block", new Block(FabricBlockSettings.create()));
    public static final Block COMPRESSED_CRAFTING_TABLE = register("compressed_crafting_table", new CraftingTableBlock(FabricBlockSettings.create()));
    public static final Block DOUBLE_COMPRESSED_CRAFTING_TABLE = register("double_compressed_crafting_table", new CraftingTableBlock(FabricBlockSettings.create()));
    public static final Block CRYSTAL_MATRIX_BLOCK = register("crystal_matrix_block", new Block(FabricBlockSettings.create()));
    public static final Block INFINITY_BLOCK = register("infinity_block", new Block(FabricBlockSettings.create()));

    private static <T extends Block> T register(String name, T block) {
        ModItems.register(name, new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, new Identifier(AvaritiaReborn.MOD_ID, name), block);
    }

    public static void init() {
    }
}
