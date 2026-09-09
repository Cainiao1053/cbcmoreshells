package com.cainiao1053.cbcmoreshells;

import com.cainiao1053.cbcmoreshells.client.gui.CBCMSFiringTableKeybind;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class CBCMSClientNeoForge {
	public static void prepareClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		modEventBus.addListener(CBCMSClientNeoForge::onClientSetup);
		modEventBus.addListener(CBCMSFiringTableKeybind::register);
		forgeEventBus.addListener(CBCMSFiringTableKeybind::onClientTick);
	}

	public static void onClientSetup(FMLClientSetupEvent event) {
		CbcmoreshellsClientCommon.onClientSetup();
	}

}
