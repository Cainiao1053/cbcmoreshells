package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.ProjectileServerContext;
import com.verr1.shaolib.munitions.projectile.impact.MunitionImpactOutcome;

@FunctionalInterface
public interface DualCannonHitCallback {

	DualCannonHitCallback NOOP = (context, outcome) -> {};

	void onHit(ProjectileServerContext<? extends DualCannonState> context, MunitionImpactOutcome outcome);

}
