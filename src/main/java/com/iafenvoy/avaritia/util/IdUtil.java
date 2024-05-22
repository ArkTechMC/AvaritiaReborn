package com.iafenvoy.avaritia.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class IdUtil {
    public static Identifier getId(Block block) {
        return Registries.BLOCK.getId(block);
    }

    public static Identifier getId(Item item) {
        return Registries.ITEM.getId(item);
    }
}
