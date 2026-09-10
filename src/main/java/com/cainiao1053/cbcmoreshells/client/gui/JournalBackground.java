package com.cainiao1053.cbcmoreshells.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * The paper backdrop shared by the firing table screens.
 *
 * <p>The artwork is a fixed 450x300, but the screens size themselves from their content, so it is
 * stretched to whatever window it is given rather than the window being forced to match the image.
 */
public final class JournalBackground {

	public static final ResourceLocation TEXTURE =
		ResourceLocation.fromNamespaceAndPath("cbcmoreshells", "textures/gui/journal_background.png");

	/** Source image dimensions; blit needs these to map UVs correctly. */
	private static final int TEXTURE_WIDTH = 450;
	private static final int TEXTURE_HEIGHT = 300;

	/** Slack around the content, so text does not sit on the very edge of the paper. */
	public static final int MARGIN = 6;

	private JournalBackground() {}

	/** Stretches the whole image across the given rectangle. */
	public static void render(GuiGraphics graphics, int x, int y, int width, int height) {
		graphics.blit(TEXTURE, x - MARGIN, y - MARGIN, width + MARGIN * 2, height + MARGIN * 2,
			0.0F, 0.0F, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
	}

}
