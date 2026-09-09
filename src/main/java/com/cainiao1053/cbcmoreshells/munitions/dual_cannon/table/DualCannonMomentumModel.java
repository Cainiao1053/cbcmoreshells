package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonPenetrationModel;

/**
 * Equivalent momentum a shell arrives with, which is what the penetration check compares against a
 * block's toughness.
 *
 * <p>One shell class, one model. New shell classes with their own penetration maths override
 * {@code FuzedDualCannonProjectileBlockItem.momentumModel()} rather than adding a branch here.
 */
@FunctionalInterface
public interface DualCannonMomentumModel {

	/**
	 * The stock model: mass times speed, bonused above a speed threshold, then capped.
	 *
	 * <p>Routed through {@link DualCannonPenetrationModel#getCappedMomentum} on purpose. Once the
	 * impact path calls that same method the table can no longer disagree with what a shot actually
	 * does.
	 */
	DualCannonMomentumModel CAPPED = (loadout, speed) ->
		DualCannonPenetrationModel.getCappedMomentum(loadout.shell().shell(), speed, loadout.effectiveMass());

	/**
	 * @param speed impact speed in blocks per tick
	 * @return equivalent momentum, comparable to block toughness
	 */
	double momentum(DualCannonLoadout loadout, double speed);

}
