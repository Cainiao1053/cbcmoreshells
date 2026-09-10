package com.cainiao1053.cbcmoreshells.client.gui;

import net.createmod.catnip.gui.ScreenOpener;

/**
 * Client-only entry points for this mod's screens.
 *
 * <p>Kept separate from the screens themselves so common code (an item's {@code use}) can reach
 * them through {@code CatnipServices.PLATFORM.executeOnClientOnly} without ever naming a screen
 * class — loading one on a dedicated server would fail.
 */
public final class CBCMSScreens {

	private CBCMSScreens() {}

	/** Opens the dual cannon firing tables, unless a screen is already up. */
	public static void openBallisticJournal() {
		ScreenOpener.open(new DualCannonCodexScreen());
	}

}
