package com.cainiao1053.cbcmoreshells.client.gui;

import com.cainiao1053.cbcmoreshells.utils.Paginator;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/**
 * Prev / page counter / next, bound to a {@link Paginator}. Not a widget itself — it hands the
 * screen two buttons to register and draws the counter between them, so any list screen can reuse
 * it without inheriting a layout.
 */
public final class PageControls {

	private static final int BUTTON_WIDTH = 20;
	private static final int BUTTON_HEIGHT = 16;
	private static final int GAP = 4;
	private static final int LABEL_WIDTH = 46;

	private final Paginator<?> paginator;
	private final Runnable onPageChanged;
	private final Button prev;
	private final Button next;

	private int x;
	private int y;

	public PageControls(Paginator<?> paginator, Runnable onPageChanged) {
		this.paginator = paginator;
		this.onPageChanged = onPageChanged;
		this.prev = Button.builder(Component.literal("<"), button -> this.step(true))
			.bounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT).build();
		this.next = Button.builder(Component.literal(">"), button -> this.step(false))
			.bounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT).build();
	}

	private void step(boolean backwards) {
		boolean moved = backwards ? this.paginator.prev() : this.paginator.next();
		if (moved) this.onPageChanged.run();
		this.refresh();
	}

	/** Total pixel width of the whole control. */
	public static int width() {
		return BUTTON_WIDTH * 2 + LABEL_WIDTH + GAP * 2;
	}

	public static int height() {
		return BUTTON_HEIGHT;
	}

	/** Places the buttons. Call from the screen's init, before registering them. */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.prev.setPosition(x, y);
		this.next.setPosition(x + BUTTON_WIDTH + LABEL_WIDTH + GAP * 2, y);
		this.refresh();
	}

	/** Greys out whichever end of the range the paginator is sitting at. */
	public void refresh() {
		this.prev.active = this.paginator.pageIndex() > 0;
		this.next.active = this.paginator.pageIndex() < this.paginator.pageCount() - 1;
	}

	/** The buttons for the screen to register; the screen owns their lifecycle. */
	public Button[] buttons() {
		return new Button[]{this.prev, this.next};
	}

	/** Draws the "3 / 12" between the buttons. The buttons draw themselves as registered widgets. */
	public void render(GuiGraphics graphics, Font font, int colour) {
		String label = (this.paginator.pageIndex() + 1) + " / " + this.paginator.pageCount();
		int centre = this.x + BUTTON_WIDTH + GAP + LABEL_WIDTH / 2;
		graphics.drawString(font, label, centre - font.width(label) / 2,
			this.y + (BUTTON_HEIGHT - font.lineHeight) / 2 + 1, colour, false);
	}

}
