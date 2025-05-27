package com.cainiao1053.cbcmoreshells.network;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import rbasamoyai.createbigcannons.multiloader.EnvExecute;
import rbasamoyai.createbigcannons.network.RootPacket;

public record ClientboundCBCMSSplashPacket(double x, double y, double z, double xOld, double yOld, double zOld) implements RootPacket {

	public ClientboundCBCMSSplashPacket(FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble(),buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

//	private static List<BlockPos> readToBlow(FriendlyByteBuf buf) {
//		int sz = buf.readVarInt();
//		List<BlockPos> toBlow = new LinkedList<>();
//		for (int i = 0; i < sz; ++i)
//			toBlow.add(buf.readBlockPos());
//		return toBlow;
//	}

	@Override
	public void rootEncode(FriendlyByteBuf buf) {
		buf.writeDouble(this.x)
			.writeDouble(this.y)
			.writeDouble(this.z)
			.writeDouble(this.xOld)
			.writeDouble(this.yOld)
			.writeDouble(this.zOld);
	}

	@Override
	public void handle(Executor exec, PacketListener listener, @Nullable ServerPlayer sender) {
		EnvExecute.executeOnClient(() -> () -> CBCMSClientHandlers.addSplashFromServer(this));
	}


}
