package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

/**
 * The launch-time facts about a munition that the datapack property set cannot express.
 *
 * <p>Deliberately tiny. Everything the Shaolib property model already covers —
 * charge cost, recoil, muzzle velocity, spread, reload coefficient, cooldown reduction — is read
 * from {@link DualCannonLaunchProperties} via {@link CBCMSDualCannonMunitionRegistry#properties},
 * so those numbers have exactly one home and cannot drift between two tables.
 *
 * <p>Only {@code baseLifetimeTicks} lives here, because Shaolib has no per-shot tick budget: its
 * projectile types carry a fixed {@code maxLifetimeTicks} ceiling and the property model expresses
 * reach as {@code runtime.max_distance}. cbcms needs a budget that varies per shot with barrel,
 * command and equipment modifiers, so it is tracked separately.
 *
 * @param baseLifetimeTicks the munition's own contribution to the tick budget, before barrel and
 *                          modifier scaling. Zero is meaningful and used by the AA shells: they
 *                          contribute nothing themselves and live purely on the barrel's bonus.
 */
public record CBCMSDualCannonLaunchProfile(int baseLifetimeTicks) {

	public CBCMSDualCannonLaunchProfile {
		baseLifetimeTicks = Math.max(0, baseLifetimeTicks);
	}

	/**
	 * Applies the firing contraption's modifiers to produce this shot's actual tick budget.
	 *
	 * <p>Mirrors the old
	 * {@code setLifetime((getLifetime() + barrelLifetime) * commandLifetimeModifier * equipmentLifetimeModifier)}.
	 * The result is floored at 1 so a hostile modifier cannot produce a shell that dies on the tick
	 * it spawns.
	 */
	public int resolveLifetimeTicks(int barrelBonusTicks, float commandModifier, float equipmentModifier) {
		double total = (this.baseLifetimeTicks + barrelBonusTicks) * (double) commandModifier * (double) equipmentModifier;
		return Math.max(1, (int) total);
	}

}
