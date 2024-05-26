package com.iafenvoy.avaritia.item.armor;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.util.ArmorMaterialUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

public class InfinityArmor extends ArmorItem {
    private static final ArmorMaterial MATERIAL = ArmorMaterialUtil.of("infinity", new int[]{999, 999, 999, 999}, 999, new int[]{999, 999, 999, 999}, 1000, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 999, 999, ModItems.INFINITY_INGOT);

    public InfinityArmor(Type slot) {
        super(MATERIAL, slot, new FabricItemSettings().fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            this.setFlying(player.getInventory().getArmorStack(2).isOf(ModItems.INFINITY_CHESTPLATE), player);
            if (player.getInventory().getArmorStack(3).isOf(ModItems.INFINITY_HELMET)) {
                player.setAir(player.getMaxAir());
                player.getHungerManager().setFoodLevel(20);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, false, false));
            } else if (player.getInventory().getArmorStack(2).isOf(ModItems.INFINITY_CHESTPLATE)) {
                player.getAbilities().allowFlying = true;
                List<StatusEffectInstance> effects = Lists.newArrayList(player.getStatusEffects());
                for (StatusEffectInstance effectInstance : Collections2.filter(effects, e -> e.getEffectType().getCategory() == StatusEffectCategory.HARMFUL))
                    player.removeStatusEffect(effectInstance.getEffectType());

            } else if (player.getInventory().getArmorStack(1).isOf(ModItems.INFINITY_LEGS) && player.isOnFire())
                player.extinguish();
        }
    }

    private void setFlying(boolean flying, PlayerEntity player) {
        if (player.isCreative()) return;
        player.getAbilities().allowFlying = flying;
        if (!flying)
            player.getAbilities().flying = false;
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return (boots.getItem().equals(ModItems.INFINITY_BOOTS) &&
                leggings.getItem().equals(ModItems.INFINITY_LEGS) &&
                chestplate.getItem().equals(ModItems.INFINITY_CHESTPLATE) &&
                helmet.getItem().equals(ModItems.INFINITY_HELMET));
    }
}
