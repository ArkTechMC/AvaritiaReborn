package com.iafenvoy.avaritia.network;

import com.iafenvoy.annotationlib.annotation.TargetId;
import com.iafenvoy.annotationlib.annotation.network.NetworkHandler;
import com.iafenvoy.annotationlib.api.IAnnotatedNetworkEntry;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
@NetworkHandler(@TargetId(namespace = AvaritiaReborn.MOD_ID,value = "singularity"))
public class ClientSingularityReceiver implements IAnnotatedNetworkEntry, ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        Singularity.decodeAll(buf);
    }
}
