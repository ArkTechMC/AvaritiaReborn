package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.registration.RegisterAll;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.block.entity.ExtremeCraftingTableBlockEntity;
import com.iafenvoy.avaritia.item.block.entity.NeutronCollectorBlockEntity;
import com.iafenvoy.avaritia.item.block.entity.NeutroniumCompressorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

@ModId(AvaritiaReborn.MOD_ID)
@RegisterAll
public class ModBlockEntities implements IAnnotatedRegistryEntry {
    public static BlockEntityType<ExtremeCraftingTableBlockEntity> EXTREME_CRAFTING_TABLE = FabricBlockEntityTypeBuilder.create(ExtremeCraftingTableBlockEntity::new, ModBlocks.EXTREME_CRAFTING_TABLE).build();
    public static BlockEntityType<NeutronCollectorBlockEntity> NEUTRON_COLLECTOR = FabricBlockEntityTypeBuilder.create(NeutronCollectorBlockEntity::new, ModBlocks.NEUTRON_COLLECTOR).build();
    public static BlockEntityType<NeutroniumCompressorBlockEntity> NEUTRONIUM_COMPRESSOR = FabricBlockEntityTypeBuilder.create(NeutroniumCompressorBlockEntity::new, ModBlocks.COMPRESSOR).build();
}
