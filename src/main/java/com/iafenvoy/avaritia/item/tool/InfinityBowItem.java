package com.iafenvoy.avaritia.item.tool;

import com.iafenvoy.avaritia.entity.InfinityArrowEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class InfinityBowItem extends BowItem {
    public InfinityBowItem() {
        super(new FabricItemSettings().maxDamage(InfinityMaterial.MATERIAL.getDurability()).rarity(Rarity.EPIC));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            InfinityArrowEntity arrow = new InfinityArrowEntity(world);
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(i);
            arrow.setPosition(user.getPos());
            arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
            if (f == 1.0F)
                arrow.setCritical(true);
            stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
            world.spawnEntity(arrow);
        }
    }
}
