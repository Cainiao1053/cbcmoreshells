package com.cainiao1053.cbcmoreshells.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import rbasamoyai.createbigcannons.network.RootPacket;

public class CBCMSClientPacket {

	private final RootPacket pkt;

	public CBCMSClientPacket(RootPacket pkt) { this.pkt = pkt; }

	public CBCMSClientPacket(FriendlyByteBuf buf) {
		this.pkt = CBCMSRootNetwork.constructPacket(buf, buf.readVarInt());
	}

	public void encode(FriendlyByteBuf buf) {
		CBCMSRootNetwork.writeToBuf(this.pkt, buf);
	}

	public void handle(Supplier<NetworkEvent.Context> sup) {
		NetworkEvent.Context ctx = sup.get();
		ctx.enqueueWork(() -> {
			this.pkt.handle(ctx::enqueueWork, ctx.getNetworkManager().getPacketListener(), null);
		});
		ctx.setPacketHandled(true);
	}

}
