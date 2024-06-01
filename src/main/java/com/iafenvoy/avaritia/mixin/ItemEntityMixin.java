package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.AvaritiaReborn;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Unique
    private static final TagKey<Item> INFINITY_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(AvaritiaReborn.MOD_ID, "infinity"));

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract void setToDefaultPickupDelay();

    @Unique
    private boolean set = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void removePickupDelay(CallbackInfo ci) {
        ItemStack stack = this.getStack();
        if (!this.set && stack.isIn(INFINITY_TAG)) {
            this.set = true;
            this.setToDefaultPickupDelay();
        }
    }
}
