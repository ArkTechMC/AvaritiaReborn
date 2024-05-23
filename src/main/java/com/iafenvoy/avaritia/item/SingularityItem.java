package com.iafenvoy.avaritia.item;

import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SingularityItem extends Item {
    public SingularityItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Singularity singularity = SingularityHelper.getFromStack(stack);
        if (singularity != Singularity.EMPTY)
            tooltip.add(Text.translatable("item.avaritia.singularity." + singularity.getId()));
        else
            tooltip.add(Text.literal("???"));
    }
}
