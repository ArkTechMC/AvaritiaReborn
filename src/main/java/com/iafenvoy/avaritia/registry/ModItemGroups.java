package com.iafenvoy.avaritia.registry;

import com.iafenvoy.annotationlib.annotation.ModId;
import com.iafenvoy.annotationlib.annotation.registration.RegisterAll;
import com.iafenvoy.annotationlib.api.IAnnotatedRegistryEntry;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Map;

@ModId(AvaritiaReborn.MOD_ID)
@RegisterAll
@SuppressWarnings("unused")
public class ModItemGroups implements IAnnotatedRegistryEntry {
    public static final ItemGroup MAIN = FabricItemGroup.builder().entries((displayContext, entries) -> {
        for (Map.Entry<String, Singularity> singularity : Singularity.MATERIALS.entrySet())
            entries.add(SingularityHelper.buildStack(singularity.getValue()));
    }).displayName(Text.translatable("itemGroup." + AvaritiaReborn.MOD_ID)).icon(() -> new ItemStack(ModItems.INFINITY_CATALYST)).build();
}
