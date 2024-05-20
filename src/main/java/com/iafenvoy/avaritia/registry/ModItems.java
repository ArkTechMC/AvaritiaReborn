package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.TargetId;
import com.iafenvoy.annotationlib.annotation.registration.Group;
import com.iafenvoy.annotationlib.annotation.registration.ItemReg;
import com.iafenvoy.annotationlib.annotation.registration.Link;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.annotationlib.util.TargetType;
import com.iafenvoy.avaritia.AvaritiaReborn;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

@ModId(AvaritiaReborn.MOD_ID)
public class ModItems implements IAnnotatedRegistryEntry {
    @ItemReg(group = @TargetId("main"))
    public static final Item DIAMOND_LATTICE = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item CRYSTAL_MATRIX_INGOT = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRON_PILE = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRON_NUGGET = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRONIUM_INGOT = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))//TODO
    public static final Item ENDEST_PEARL = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))//TODO
    public static final Item SINGULARITY = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_CATALYST = new Item(new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_INGOT = new Item(new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item RECORD_FRAGMENT = new Item(new FabricItemSettings().fireproof());
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("extreme_crafting_table"))
    public static Item EXTREME_CRAFTING_TABLE = null;
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("neutron_collector"))
    public static Item NEUTRON_COLLECTOR = null;
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("compressor"))
    public static Item COMPRESSOR = null;
}
