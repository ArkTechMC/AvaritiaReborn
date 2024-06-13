package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.data.singularity.Singularity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SynchronizeRecipesS2CPacket.class)
public class SynchronizeRecipesS2CPacketMixin {
    @Inject(method = "write", at = @At("RETURN"))
    private void writeSingularity(PacketByteBuf buf, CallbackInfo ci) {
        Singularity.encodeAll(buf);
    }

    @Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"))
    private void readSingularity(PacketByteBuf buf, CallbackInfo ci) {
        Singularity.decodeAll(buf);
    }
}
