package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.TargetId;
import com.iafenvoy.annotationlib.annotation.registration.Group;
import com.iafenvoy.annotationlib.annotation.registration.ItemReg;
import com.iafenvoy.annotationlib.annotation.registration.Link;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.annotationlib.util.TargetType;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.SingularityItem;
import com.iafenvoy.avaritia.item.armor.InfinityArmor;
import com.iafenvoy.avaritia.item.tool.InfinityTools;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

@ModId(AvaritiaReborn.MOD_ID)
public class ModItems implements IAnnotatedRegistryEntry {
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("compressed_crafting_table"))
    public static Item COMPRESSED_CRAFTING_TABLE = null;
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("double_compressed_crafting_table"))
    public static Item DOUBLE_COMPRESSED_CRAFTING_TABLE = null;
    @ItemReg(group = @TargetId("main"))
    public static final Item DIAMOND_LATTICE = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item CRYSTAL_MATRIX_INGOT = new Item(new FabricItemSettings());
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("crystal_matrix_block"))
    public static Item CRYSTAL_MATRIX_BLOCK = null;
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRON_PILE = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRON_NUGGET = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))
    public static final Item NEUTRONIUM_INGOT = new Item(new FabricItemSettings());
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("neutronium_block"))
    public static Item NEUTRONIUM_BLOCK = null;
    @ItemReg(group = @TargetId("main"))//TODO
    public static final Item ENDEST_PEARL = new Item(new FabricItemSettings());
    @ItemReg(group = @TargetId("main"))//TODO
    public static final Item SINGULARITY = new SingularityItem(new FabricItemSettings());
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
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_CATALYST = new Item(new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_INGOT = new Item(new FabricItemSettings().fireproof());
    @Group(@TargetId("main"))
    @Link(type = TargetType.BLOCK, target = @TargetId("infinity_block"))
    public static Item INFINITY_BLOCK=null;
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_HELMET = new InfinityArmor(ArmorItem.Type.HELMET, new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_CHESTPLATE = new InfinityArmor(ArmorItem.Type.CHESTPLATE, new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_LEGS = new InfinityArmor(ArmorItem.Type.LEGGINGS, new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_BOOTS = new InfinityArmor(ArmorItem.Type.BOOTS, new FabricItemSettings().fireproof());
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_SWORD = new InfinityTools.Sword();
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_AXE = new InfinityTools.Axe();
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_PICKAXE = new InfinityTools.Pickaxe();
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_SHOVEL = new InfinityTools.Shovel();
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_HOE = new InfinityTools.Hoe();
    @ItemReg(group = @TargetId("main"))
    public static final Item INFINITY_BOW = new InfinityTools.Bow();
    @ItemReg(group = @TargetId("main"))//TODO
    public static final Item SKULLFIRE_SWORD = new InfinityTools.Sword();

}
