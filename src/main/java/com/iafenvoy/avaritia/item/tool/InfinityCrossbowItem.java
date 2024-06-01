package com.iafenvoy.avaritia.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Rarity;

public class InfinityCrossbowItem extends CrossbowItem {
    public InfinityCrossbowItem() {
        super(new FabricItemSettings().maxDamage(InfinityMaterial.MATERIAL.getDurability()).rarity(Rarity.EPIC));
    }
}
