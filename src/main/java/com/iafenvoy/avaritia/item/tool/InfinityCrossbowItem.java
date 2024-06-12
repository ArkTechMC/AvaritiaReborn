package com.iafenvoy.avaritia.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class InfinityCrossbowItem extends CrossbowItem {
    private static final String CHARGED_NBT="Charged";
    public InfinityCrossbowItem() {
        super(new FabricItemSettings().maxDamage(InfinityMaterial.MATERIAL.getDurability()).rarity(Rarity.EPIC));
    }

    public static boolean isCharged(ItemStack stack) {
        NbtCompound compoundTag = stack.getNbt();
        return compoundTag != null && compoundTag.getBoolean(CHARGED_NBT);
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        NbtCompound compoundTag = stack.getOrCreateNbt();
        compoundTag.putBoolean(CHARGED_NBT, charged);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isCharged(itemStack)) {
            shootAll(world, user, hand, itemStack, hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F, 1.0F);
            setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        } else if (!user.getProjectileType(itemStack).isEmpty()) {
            if (!isCharged(itemStack)) {
                this.charged = false;
                this.loaded = false;
                user.setCurrentHand(hand);
            }

            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }
}
