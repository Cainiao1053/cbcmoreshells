package com.cainiao1053.cbcmoreshells.network;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class CBCMSNetwork {

	public static final String VERSION = "2.0.0";

	public static final SimpleChannel INSTANCE = construct();

	public static SimpleChannel construct() {
		SimpleChannel channel = NetworkRegistry.ChannelBuilder
				.named(Cbcmoreshells.resource("network"))
				.clientAcceptedVersions(VERSION::equals)
				.serverAcceptedVersions(VERSION::equals)
				.networkProtocolVersion(() -> VERSION)
				.simpleChannel();

		int id = 0;

//		channel.messageBuilder(ForgeServerPacket.class, id++)
//				.encoder(ForgeServerPacket::encode)
//				.decoder(ForgeServerPacket::new)
//				.consumerMainThread(ForgeServerPacket::handle)
//				.add();

		channel.messageBuilder(CBCMSClientPacket.class, id++)
				.encoder(CBCMSClientPacket::encode)
				.decoder(CBCMSClientPacket::new)
				.consumerMainThread(CBCMSClientPacket::handle)
				.add();

		return channel;
	}

	public static void init() {}

}
