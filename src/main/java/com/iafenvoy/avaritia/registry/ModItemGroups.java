package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.CallbackHandler;
import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.registration.RegisterAll;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@ModId(AvaritiaReborn.MOD_ID)
@RegisterAll
@SuppressWarnings("unused")
public class ModItemGroups implements IAnnotatedRegistryEntry {
    public static final ItemGroup MAIN = FabricItemGroup.builder().entries((displayContext, entries) -> {
    }).displayName(Text.translatable("itemGroup." + AvaritiaReborn.MOD_ID)).icon(() -> new ItemStack(ModItems.INFINITY_CATALYST)).build();

    @CallbackHandler
    public static void addSingularity() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            if (group == MAIN)
                for (Singularity singularity : Singularity.MATERIALS.values())
                    entries.add(SingularityHelper.buildStack(singularity));
        });
    }
}
