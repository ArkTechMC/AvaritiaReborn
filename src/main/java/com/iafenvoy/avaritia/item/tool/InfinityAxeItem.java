package com.iafenvoy.avaritia.item.tool;

import net.minecraft.item.AxeItem;
import net.minecraft.util.Rarity;

public class InfinityAxeItem extends AxeItem {
    public InfinityAxeItem() {
        super(InfinityMaterial.MATERIAL, 1.0F, -2.2F, new Settings().rarity(Rarity.EPIC));
    }
}
