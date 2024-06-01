package com.iafenvoy.avaritia.item.armor;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.util.ArmorMaterialUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
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

public class InfinityArmorItem extends ArmorItem {
    private static final ArmorMaterial MATERIAL = ArmorMaterialUtil.of("infinity", new int[]{999, 999, 999, 999}, 999, new int[]{999, 999, 999, 999}, 1000, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 999, 999, ModItems.INFINITY_INGOT);

    public InfinityArmorItem(Type slot) {
        super(MATERIAL, slot, new FabricItemSettings().fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.INFINITY_HELMET)) {
                player.setAir(player.getMaxAir());
                player.getHungerManager().setFoodLevel(20);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 20, 0, false, false));
            }
            if (player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.INFINITY_CHESTPLATE)) {
                List<StatusEffectInstance> effects = Lists.newArrayList(player.getStatusEffects());
                for (StatusEffectInstance effectInstance : Collections2.filter(effects, e -> e.getEffectType().getCategory() == StatusEffectCategory.HARMFUL))
                    player.removeStatusEffect(effectInstance.getEffectType());
                player.getAbilities().allowFlying = true;
            } else {
                if (!player.isCreative()) {
                    player.getAbilities().allowFlying = false;
                    player.getAbilities().flying = false;
                }
            }
            if (player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.INFINITY_LEGS)) {
                if (player.isOnFire())
                    player.extinguish();
            }
            if (player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.INFINITY_BOOTS)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 9, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 3, false, false));
            }
        }
    }

    public static boolean fullyEquipped(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.INFINITY_HELMET)
                && player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.INFINITY_CHESTPLATE)
                && player.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.INFINITY_LEGS)
                && player.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.INFINITY_BOOTS);
    }
}
