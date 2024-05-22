package com.iafenvoy.avaritia.item.armor;

import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.util.ArmorMaterialUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Objects;

public class InfinityArmor extends ArmorItem {
    private static final ArmorMaterial MATERIAL = ArmorMaterialUtil.of("infinity", new int[]{999, 999, 999, 999}, 999, new int[]{999, 999, 999, 999}, 1000, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 999, 999, ModItems.INFINITY_INGOT);

    public InfinityArmor(Type slot, Settings settings) {
        super(MATERIAL, slot, settings);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }



    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (hasFullSuitOfArmorOn(player)) {
                    player.getAbilities().allowFlying = true;
                    //player.setHealth(20);
                } else {
                    if (!player.getAbilities().creativeMode) {
                        player.getAbilities().allowFlying = false;
                    }

                }
            }
        }

        if (!world.isClient()) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                if (hasFullSuitOfArmorOn(player)) {
                    //player.sendMessage(Text.literal("tas com o set de armadura"),true);


                    if (player.getStatusEffect(StatusEffects.NIGHT_VISION) == null || Objects.requireNonNull(player.getStatusEffect(StatusEffects.NIGHT_VISION)).getDuration() < 250) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 4));
                    }

                    if (player.getStatusEffect(StatusEffects.REGENERATION) == null || Objects.requireNonNull(player.getStatusEffect(StatusEffects.REGENERATION)).getDuration() < 250) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 4));
                    }


                } else {
                    //player.sendMessage(Text.literal("nao estas com o set"),true);

                    //player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
                    //player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                    //player.removeStatusEffect(StatusEffects.SPEED);
                    //player.removeStatusEffect(StatusEffects.JUMP_BOOST);
                    //player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);

                }

                if (player.getInventory().getArmorStack(3).getItem().equals(ModItems.INFINITY_HELMET)) {
                    //efeitos do capacete
                    if (player.getHungerManager().getFoodLevel() != 20) {
                        player.getHungerManager().setFoodLevel(20);
                    }
                }

                if (player.getInventory().getArmorStack(2).getItem().equals(ModItems.INFINITY_CHESTPLATE)) {
                    //efeitos da chestplate
                    if (player.getStatusEffect(StatusEffects.POISON) != null) {
                        player.removeStatusEffect(StatusEffects.POISON);
                    }
                    if (player.getStatusEffect(StatusEffects.WEAKNESS) != null) {
                        player.removeStatusEffect(StatusEffects.WEAKNESS);
                    }
                    if (player.getStatusEffect(StatusEffects.SLOWNESS) != null) {
                        player.removeStatusEffect(StatusEffects.SLOWNESS);
                    }
                    if (player.getStatusEffect(StatusEffects.BLINDNESS) != null) {
                        player.removeStatusEffect(StatusEffects.BLINDNESS);
                    }
                    if (player.getStatusEffect(StatusEffects.HUNGER) != null) {
                        player.removeStatusEffect(StatusEffects.HUNGER);
                    }
                    if (player.getStatusEffect(StatusEffects.NAUSEA) != null) {
                        player.removeStatusEffect(StatusEffects.NAUSEA);
                    }
                    if (player.getStatusEffect(StatusEffects.MINING_FATIGUE) != null) {
                        player.removeStatusEffect(StatusEffects.MINING_FATIGUE);
                    }
                    if (player.getStatusEffect(StatusEffects.WITHER) != null) {
                        player.removeStatusEffect(StatusEffects.WITHER);
                    }
                }

                if (player.getInventory().getArmorStack(1).getItem().equals(ModItems.INFINITY_LEGS)) {
                    //efeitos das leggings
                    if (player.getStatusEffect(StatusEffects.FIRE_RESISTANCE) == null || Objects.requireNonNull(player.getStatusEffect(StatusEffects.FIRE_RESISTANCE)).getDuration() < 250) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 300, 99));
                    }
                }

                if (player.getInventory().getArmorStack(0).getItem().equals(ModItems.INFINITY_BOOTS)) {
                    //efeitos das botas
                    if (player.getStatusEffect(StatusEffects.JUMP_BOOST) == null || Objects.requireNonNull(player.getStatusEffect(StatusEffects.JUMP_BOOST)).getDuration() < 250) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 4));
                        //corrigir fall damage
                    }
                    if (player.getStatusEffect(StatusEffects.SPEED) == null || Objects.requireNonNull(player.getStatusEffect(StatusEffects.SPEED)).getDuration() < 250) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 4));
                    }
                }

            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
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
