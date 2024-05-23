package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.registration.RegisterAll;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.block.ExtremeCraftingTableBlock;
import com.iafenvoy.avaritia.item.block.NeutronCollectorBlock;
import com.iafenvoy.avaritia.item.block.NeutroniumCompressorBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;

@ModId(AvaritiaReborn.MOD_ID)
@RegisterAll
@SuppressWarnings("unused")
public class ModBlocks implements IAnnotatedRegistryEntry {
    public static final Block EXTREME_CRAFTING_TABLE = new ExtremeCraftingTableBlock(FabricBlockSettings.create());
    public static final Block NEUTRON_COLLECTOR = new NeutronCollectorBlock(FabricBlockSettings.create());
    public static final Block COMPRESSOR = new NeutroniumCompressorBlock(FabricBlockSettings.create());
    public static final Block NEUTRONIUM_BLOCK = new NeutroniumCompressorBlock(FabricBlockSettings.create());
    public static final Block COMPRESSED_CRAFTING_TABLE = new CraftingTableBlock(FabricBlockSettings.create());
    public static final Block DOUBLE_COMPRESSED_CRAFTING_TABLE = new CraftingTableBlock(FabricBlockSettings.create());
    public static final Block CRYSTAL_MATRIX_BLOCK=new Block(FabricBlockSettings.create());
    public static final Block INFINITY_BLOCK=new Block(FabricBlockSettings.create());
}
