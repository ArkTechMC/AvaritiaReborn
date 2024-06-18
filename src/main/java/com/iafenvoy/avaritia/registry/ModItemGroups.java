package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItemGroups {
    public static final List<Item> groupItems = new ArrayList<>();
    public static final ItemGroup MAIN = register("main", FabricItemGroup.builder().entries((displayContext, entries) -> groupItems.forEach(entries::add)).displayName(Text.translatable("itemGroup." + AvaritiaReborn.MOD_ID)).icon(() -> new ItemStack(ModItems.INFINITY_CATALYST)).build());

    private static <T extends ItemGroup> T register(String name, T group) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(AvaritiaReborn.MOD_ID, name), group);
    }

    public static void init() {
        addSingularity();
    }

    public static void addSingularity() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            if (group == MAIN)
                for (Singularity singularity : Singularity.MATERIALS.values())
                    entries.add(SingularityHelper.buildStack(singularity));
        });
    }
}
