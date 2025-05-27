package com.cainiao1053.cbcmoreshells.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import rbasamoyai.createbigcannons.network.RootPacket;

public class CBCMSNetworkImpl {

//	@OnlyIn(Dist.CLIENT)
//	public static void sendToServer(RootPacket pkt) {
//		CBCMSNetwork.INSTANCE.sendToServer(new ForgeServerPacket(pkt));
//	}

	public static void sendToClientPlayer(RootPacket pkt, ServerPlayer player) {
		CBCMSNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new CBCMSClientPacket(pkt));
	}

	public static void sendToClientTracking(RootPacket pkt, Entity tracked) {
		CBCMSNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> tracked), new CBCMSClientPacket(pkt));
	}

	public static void sendToClientAll(RootPacket pkt, MinecraftServer server) {
		CBCMSNetwork.INSTANCE.send(PacketDistributor.ALL.noArg(), new CBCMSClientPacket(pkt));
	}

}