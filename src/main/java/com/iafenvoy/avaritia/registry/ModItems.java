package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.item.InfinityBucketItem;
import com.iafenvoy.avaritia.item.MatterClusterItem;
import com.iafenvoy.avaritia.item.SingularityItem;
import com.iafenvoy.avaritia.item.armor.InfinityArmorItem;
import com.iafenvoy.avaritia.item.tool.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

@SuppressWarnings("unused")
public class ModItems {
    public static final Item DIAMOND_LATTICE = register("diamond_lattice", new Item(new FabricItemSettings()));
    public static final Item CRYSTAL_MATRIX_INGOT = register("crystal_matrix_ingot", new Item(new FabricItemSettings()));
    public static final Item NEUTRON_PILE = register("neutron_pile", new Item(new FabricItemSettings()));
    public static final Item NEUTRON_NUGGET = register("neutron_nugget", new Item(new FabricItemSettings()));
    public static final Item NEUTRONIUM_INGOT = register("neutronium_ingot", new Item(new FabricItemSettings()));
    public static final Item ENDEST_PEARL = register("endest_pearl", new Item(new FabricItemSettings()));
    public static final Item SKULLFIRE_SWORD = register("skullfire_sword", new InfinitySwordItem());
    public static final Item SINGULARITY = register("singularity", new SingularityItem());
    public static final Item RECORD_FRAGMENT = register("record_fragment", new Item(new FabricItemSettings().fireproof()));
    public static final Item COSMIC_MEATBALLS = register("cosmic_meatballs", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(100).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 5 * 60 * 20, 1), 1).build())));
    public static final Item ULTIMATE_STEW = register("ultimate_stew", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(100).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 60 * 20, 1), 1).build())));
    public static final Item INFINITY_CATALYST = register("infinity_catalyst", new Item(new FabricItemSettings().fireproof().rarity(Rarity.RARE)));
    public static final Item INFINITY_INGOT = register("infinity_ingot", new Item(new FabricItemSettings().fireproof().rarity(Rarity.RARE)));
    public static final Item INFINITY_BUCKET = register("infinity_bucket", new InfinityBucketItem());
    public static final Item INFINITY_HELMET = register("infinity_helmet", new InfinityArmorItem(ArmorItem.Type.HELMET));
    public static final Item INFINITY_CHESTPLATE = register("infinity_chestplate", new InfinityArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final Item INFINITY_LEGS = register("infinity_legs", new InfinityArmorItem(ArmorItem.Type.LEGGINGS));
    public static final Item INFINITY_BOOTS = register("infinity_boots", new InfinityArmorItem(ArmorItem.Type.BOOTS));
    public static final Item INFINITY_SWORD = register("infinity_sword", new InfinitySwordItem());
    public static final Item INFINITY_AXE = register("infinity_axe", new InfinityAxeItem());
    public static final Item INFINITY_PICKAXE = register("infinity_pickaxe", new InfinityPickaxeItem());
    public static final Item INFINITY_SHOVEL = register("infinity_shovel", new InfinityShovelItem());
    public static final Item INFINITY_HOE = register("infinity_hoe", new InfinityHoeItem());
    public static final Item INFINITY_BOW = register("infinity_bow", new InfinityBowItem());
    public static final Item INFINITY_CROSSBOW = register("infinity_crossbow", new InfinityCrossbowItem());
    public static final Item INFINITY_TOTEM = register("infinity_totem", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item MATTER_CLUSTER = register("matter_cluster", new MatterClusterItem());

    public static <T extends Item> T register(String name, T item) {
        ModItemGroups.groupItems.add(item);
        return Registry.register(Registries.ITEM, new Identifier(AvaritiaReborn.MOD_ID, name), item);
    }

    public static void init() {
    }
}
