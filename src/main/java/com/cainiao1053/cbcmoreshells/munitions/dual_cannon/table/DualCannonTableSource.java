package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.CBCMSDualCannonMunitionRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.block.Block;

/**
 * Assembles firing tables on demand and caches them for the life of one screen.
 *
 * <p>Deliberately lazy in two steps. Listing shells resolves nothing — a screen showing nine icons
 * should not parse nine property sets. Picking a shell builds its skeleton once. Rows are built per
 * material as the page needs them, which is cheap because the skeleton is already there and only
 * the momentum column is per material.
 *
 * <p>Nothing survives the screen, so a {@code /reload} that changes the datapack shows up the next
 * time the table is opened.
 */
public final class DualCannonTableSource {

	/** Default width budget: material name, its stat columns, and the distances share this. */
	public static final int DEFAULT_TOTAL_COLUMNS = 13;
	/** A table with fewer distance columns than this is not worth showing. */
	public static final int MIN_BALLISTIC_COLUMNS = 4;

	private final int totalColumns;
	private final DualCannonMaterialFilter filter;
	private final boolean highArc;

	private final Map<Block, DualCannonTable> tables = new HashMap<>();
	private final Map<Block, Map<DualCannonMaterial, MaterialRow>> rows = new HashMap<>();
	private List<Block> shells;
	private Set<DualCannonMaterial> gapMaterials;

	public DualCannonTableSource() {
		this(DEFAULT_TOTAL_COLUMNS, DualCannonMaterialFilter.SINGLE_PLUS_GAPS, false);
	}

	public DualCannonTableSource(int totalColumns, DualCannonMaterialFilter filter, boolean highArc) {
		this.totalColumns = Math.max(1 + MIN_BALLISTIC_COLUMNS, totalColumns);
		this.filter = filter;
		this.highArc = highArc;
	}

	/** Projectile blocks that can be tabulated, in registration order. Resolves no properties. */
	public List<Block> shells() {
		if (this.shells == null) {
			List<Block> found = new ArrayList<>();
			for (Block block : CBCMSDualCannonMunitionRegistry.blocks()) {
				if (DualCannonShellContext.of(block) != null) found.add(block);
			}
			this.shells = List.copyOf(found);
		}
		return this.shells;
	}

	/**
	 * The table for one shell, built on first request.
	 *
	 * @return null when the block is not a tabulatable dual cannon round
	 */
	@Nullable
	public DualCannonTable table(Block shell) {
		DualCannonTable cached = this.tables.get(shell);
		if (cached != null) return cached;

		DualCannonShellContext context = DualCannonShellContext.of(shell);
		if (context == null) return null;

		StatSink<DualCannonShellContext> shellSink = new StatSink<>();
		context.item().collectShellStats(shellSink);

		StatSink<DualCannonLoadout> materialSink = new StatSink<>();
		context.item().collectMaterialStats(materialSink);

		// Distances get whatever the material columns leave behind; if that is too little, the
		// lowest priority material stats fold away instead of squeezing the ballistics to nothing.
		int materialBudget = this.totalColumns - 1 - MIN_BALLISTIC_COLUMNS;
		List<StatSpec<DualCannonLoadout>> materialStats = materialSink.limited(Math.max(0, materialBudget));
		int ballisticColumns = this.totalColumns - 1 - materialStats.size();

		// One of those columns is the muzzle, which compute() prepends for free, so ask for one
		// fewer downrange sample and the finished table still fits the budget.
		BallisticSkeleton skeleton =
			BallisticSkeleton.compute(context, Math.max(1, ballisticColumns - 1), this.highArc);
		DualCannonTable table = new DualCannonTable(context, shellSink.specs(), materialStats, skeleton,
			context.item().ballisticModes(), this.filter.select());
		this.tables.put(shell, table);
		return table;
	}

	/**
	 * One material's row, built on first request.
	 *
	 * @return null when the shell has no table or its flight cannot be solved
	 */
	@Nullable
	public MaterialRow row(Block shell, DualCannonMaterial material) {
		DualCannonTable table = this.table(shell);
		if (table == null || table.skeleton() == null) return null;

		Map<DualCannonMaterial, MaterialRow> byMaterial =
			this.rows.computeIfAbsent(shell, key -> new LinkedHashMap<>());
		MaterialRow cached = byMaterial.get(material);
		if (cached != null) return cached;

		DualCannonLoadout loadout = DualCannonLoadout.of(table.shell(), material);
		MaterialRow row = MaterialRow.compute(loadout, table.skeleton(), table.materialStats(),
			this.gapMaterials().contains(material));
		byMaterial.put(material, row);
		return row;
	}

	/** Rows for a page's worth of materials, in the order given. */
	public List<MaterialRow> rows(Block shell, List<DualCannonMaterial> page) {
		List<MaterialRow> built = new ArrayList<>(page.size());
		for (DualCannonMaterial material : page) {
			MaterialRow row = this.row(shell, material);
			if (row != null) built.add(row);
		}
		return built;
	}

	/** Materials that only appear because no single-barrel variant exists yet. */
	private Set<DualCannonMaterial> gapMaterials() {
		if (this.gapMaterials == null) {
			this.gapMaterials = new HashSet<>(DualCannonMaterialFilter.missingSingleVariants());
		}
		return this.gapMaterials;
	}

	/** Drops everything cached; call after a datapack reload. */
	public void invalidate() {
		this.tables.clear();
		this.rows.clear();
		this.shells = null;
		this.gapMaterials = null;
	}

}
