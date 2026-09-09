package com.cainiao1053.cbcmoreshells.client.gui;

import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonMaterialFilter;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table.DualCannonTableSource;

import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * Shell picker. Lists the dual cannon rounds and opens a comparison table for whichever one is
 * chosen; the table's shape (material filter, column budget) is chosen here so it applies to every
 * shell.
 *
 * <p>Listing shells resolves no properties — that only happens once a shell is picked.
 */
public class DualCannonCodexScreen extends AbstractSimiScreen {

	private static final int PADDING = 8;
	private static final int ROW_HEIGHT = 20;
	private static final int WINDOW_WIDTH = 260;
	private static final int ICON_SIZE = 16;

	private static final int COLOUR_TITLE = 0xFFFFFFFF;
	private static final int COLOUR_LABEL = 0xFFA0A0A0;
	private static final int COLOUR_VALUE = 0xFFE0E0E0;
	private static final int COLOUR_HOVER = 0x40FFFFFF;
	private static final int COLOUR_BACKGROUND = 0xF0101018;

	private static final int[] COLUMN_CHOICES = {11, 13, 15};

	private DualCannonTableSource source;
	private List<Block> shells = List.of();
	private DualCannonMaterialFilter filter;
	private int columnChoice;
	private int listTop;

	@Nullable
	private final Block lastSelection;

	public DualCannonCodexScreen() {
		this(null, null);
	}

	/** Reopened from a comparison table: keeps the settings and remembers what was being viewed. */
	public DualCannonCodexScreen(@Nullable DualCannonTableSource source, @Nullable Block lastSelection) {
		this.source = source;
		this.lastSelection = lastSelection;
		this.filter = DualCannonMaterialFilter.SINGLE_PLUS_GAPS;
		this.columnChoice = 1;
	}

	@Override
	protected void init() {
		if (this.source == null) this.source = this.buildSource();
		this.shells = this.source.shells();

		this.setWindowSize(WINDOW_WIDTH, PADDING * 2 + 20 + this.shells.size() * ROW_HEIGHT + 24);
		super.init();

		int left = this.guiLeft + PADDING;
		int top = this.guiTop + PADDING;

		this.addRenderableWidget(Button.builder(this.filterLabel(), button -> this.cycleFilter())
			.bounds(left, top + 12, 150, 16).build());
		this.addRenderableWidget(Button.builder(this.columnLabel(), button -> this.cycleColumns())
			.bounds(left + 154, top + 12, 90, 16).build());

		this.listTop = top + 34;
	}

	private DualCannonTableSource buildSource() {
		return new DualCannonTableSource(COLUMN_CHOICES[this.columnChoice], this.filter, false);
	}

	private Component filterLabel() {
		return Component.translatable("cbcmoreshells.firing_table.filter",
			I18n.get("cbcmoreshells.firing_table.filter." + this.filter.name().toLowerCase()));
	}

	private Component columnLabel() {
		return Component.translatable("cbcmoreshells.firing_table.columns", COLUMN_CHOICES[this.columnChoice]);
	}

	/** Changing either setting throws the cached tables away, since both change their shape. */
	private void cycleFilter() {
		DualCannonMaterialFilter[] values = DualCannonMaterialFilter.values();
		this.filter = values[(this.filter.ordinal() + 1) % values.length];
		this.source = this.buildSource();
		this.rebuildWidgets();
	}

	private void cycleColumns() {
		this.columnChoice = (this.columnChoice + 1) % COLUMN_CHOICES.length;
		this.source = this.buildSource();
		this.rebuildWidgets();
	}

	@Override
	protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		graphics.fill(this.guiLeft, this.guiTop, this.guiLeft + this.windowWidth,
			this.guiTop + this.windowHeight, COLOUR_BACKGROUND);

		int left = this.guiLeft + PADDING;
		graphics.drawString(this.font, I18n.get("cbcmoreshells.firing_table.title"), left,
			this.guiTop + PADDING, COLOUR_TITLE, false);

		if (this.shells.isEmpty()) {
			graphics.drawString(this.font, I18n.get("cbcmoreshells.firing_table.no_shells"), left, this.listTop,
				COLOUR_LABEL, false);
			return;
		}

		int hovered = this.rowAt(mouseX, mouseY);
		for (int i = 0; i < this.shells.size(); i++) {
			Block shell = this.shells.get(i);
			int y = this.listTop + i * ROW_HEIGHT;
			if (i == hovered) {
				graphics.fill(left, y - 2, this.guiLeft + this.windowWidth - PADDING, y + ROW_HEIGHT - 4,
					COLOUR_HOVER);
			}
			graphics.renderItem(new ItemStack(shell), left, y - 1);
			graphics.drawString(this.font, shell.getName(), left + ICON_SIZE + 6,
				y + (ICON_SIZE - this.font.lineHeight) / 2, COLOUR_VALUE, false);
		}
	}

	/** Index of the shell row under the cursor, or -1. */
	private int rowAt(double mouseX, double mouseY) {
		int left = this.guiLeft + PADDING;
		int right = this.guiLeft + this.windowWidth - PADDING;
		if (mouseX < left || mouseX > right || mouseY < this.listTop) return -1;
		int row = (int) ((mouseY - this.listTop + 2) / ROW_HEIGHT);
		return row >= 0 && row < this.shells.size() ? row : -1;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (super.mouseClicked(mouseX, mouseY, button)) return true;
		if (button != 0) return false;

		int row = this.rowAt(mouseX, mouseY);
		if (row < 0) return false;
		ScreenOpener.open(new DualCannonCompareScreen(this.source, this.shells.get(row)));
		return true;
	}

	/** So a table reopened from the picker starts on the shell that was being viewed. */
	@Nullable
	public Block lastSelection() {
		return this.lastSelection;
	}

}
