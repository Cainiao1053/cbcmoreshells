package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.ProjectileServerContext;
import com.verr1.shaolib.munitions.projectile.impact.MunitionImpactOutcome;

/**
 * Notified every time a dual cannon projectile resolves a block impact.
 *
 * <p>Exists so the firing contraption can react to its own shells landing — cbcms uses it to shorten
 * the quick-firing breech cooldown on a confirmed hit. Held on {@link DualCannonState} as a transient
 * field, so it does not survive a save/load round trip; that matches the old entity projectiles,
 * which held a bare reference to the contraption.
 */
@FunctionalInterface
public interface DualCannonHitCallback {

	DualCannonHitCallback NOOP = (context, outcome) -> {};

	void onHit(ProjectileServerContext<? extends DualCannonState> context, MunitionImpactOutcome outcome);

}
