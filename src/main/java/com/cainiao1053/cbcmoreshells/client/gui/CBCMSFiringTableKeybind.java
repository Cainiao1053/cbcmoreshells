package com.cainiao1053.cbcmoreshells.client.gui;

import com.mojang.blaze3d.platform.InputConstants;

import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

/**
 * Opens the dual cannon firing tables.
 *
 * <p>Unbound by default: the tables are a reference screen rather than something needed mid-fight,
 * and claiming a key by default would collide with whatever the player already has bound. A key
 * rather than an item keeps this asset-free — no model, texture or recipe to ship.
 */
public final class CBCMSFiringTableKeybind {

	private static final String CATEGORY = "key.categories.cbcmoreshells";

	public static final KeyMapping OPEN_FIRING_TABLES = new KeyMapping(
		"key.cbcmoreshells.firing_tables", InputConstants.Type.KEYSYM,
		InputConstants.UNKNOWN.getValue(), CATEGORY);

	private CBCMSFiringTableKeybind() {}

	public static void register(RegisterKeyMappingsEvent event) {
		event.register(OPEN_FIRING_TABLES);
	}

	public static void onClientTick(ClientTickEvent.Post event) {
		// Drain the queue even when the screen cannot open, so a buffered press does not fire later.
		boolean pressed = false;
		while (OPEN_FIRING_TABLES.consumeClick()) pressed = true;
		if (!pressed) return;

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null || minecraft.screen != null) return;
		ScreenOpener.open(new DualCannonCodexScreen());
	}

}
