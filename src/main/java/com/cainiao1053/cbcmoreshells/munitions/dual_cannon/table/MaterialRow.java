package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import java.util.List;

/**
 * One barrel material's row: its stat columns, its equivalent momentum at each distance, and how
 * far down the row it can still reach before the shell expires.
 *
 * <p>The arrays are view state, not identity — do not use a row as a map key.
 *
 * @param missingSingleVariant this alloy has no single-barrel material yet and only appears because
 *                             the filter is filling gaps; the UI should mark it
 * @param statValues           parallel to {@code DualCannonTable.materialStats()}
 * @param momentum             parallel to the skeleton's points
 * @param reachable            parallel to the skeleton's points; false once the shell has expired
 */
public record MaterialRow(DualCannonLoadout loadout, boolean missingSingleVariant,
						  double[] statValues, double[] momentum, boolean[] reachable) {

	/** Computes a row against an already-built skeleton; only the momentum column is new work. */
	public static MaterialRow compute(DualCannonLoadout loadout, BallisticSkeleton skeleton,
									  List<StatSpec<DualCannonLoadout>> statSpecs, boolean missingSingleVariant) {
		double[] statValues = new double[statSpecs.size()];
		for (int i = 0; i < statSpecs.size(); i++) statValues[i] = statSpecs.get(i).valueOf(loadout);

		DualCannonMomentumModel model = loadout.shell().item().momentumModel();
		int lifetime = loadout.lifetimeTicks();
		int columns = skeleton.columns();
		double[] momentum = new double[columns];
		boolean[] reachable = new boolean[columns];
		for (int i = 0; i < columns; i++) {
			BallisticPoint point = skeleton.point(i);
			momentum[i] = model.momentum(loadout, point.impactSpeed());
			reachable[i] = point.reachableWithin(lifetime);
		}
		return new MaterialRow(loadout, missingSingleVariant, statValues, momentum, reachable);
	}

	/** Value for a distance column, whichever mode the table is showing. */
	public double ballisticValue(BallisticColumnMode mode, BallisticSkeleton skeleton, int column) {
		return mode == BallisticColumnMode.MOMENTUM ? this.momentum[column] : skeleton.value(mode, column);
	}

	/** Formatted for display, or a dash once the shell can no longer get this far. */
	public String formatBallistic(BallisticColumnMode mode, BallisticSkeleton skeleton, int column) {
		if (!this.reachable[column]) return StatFormat.NOT_APPLICABLE;
		return mode.format().format(this.ballisticValue(mode, skeleton, column));
	}

	public String formatStat(List<StatSpec<DualCannonLoadout>> statSpecs, int index) {
		return statSpecs.get(index).format().format(this.statValues[index]);
	}

	/** Furthest column this material can still reach, or -1 if it cannot reach the first one. */
	public int lastReachableColumn() {
		for (int i = this.reachable.length - 1; i >= 0; i--) {
			if (this.reachable[i]) return i;
		}
		return -1;
	}

}
