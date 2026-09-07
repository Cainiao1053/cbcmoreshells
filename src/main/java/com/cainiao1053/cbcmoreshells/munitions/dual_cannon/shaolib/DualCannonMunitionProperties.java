package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.munitions.config.properties.CbcLikeMunitionProperties;

/**
 * What {@link DualCannonBehavior} and {@link DualCannonPenetrationModel} need from a munition on top
 * of the generic CBC-like property set: the launch profile and the dual-cannon impact tuning.
 *
 * <p>Implemented by {@link DualCannonProjectileProperties} and
 * {@link DualCannonIncendiaryProjectileProperties}.
 */
public interface DualCannonMunitionProperties extends CbcLikeMunitionProperties {

	DualCannonLaunchProperties dualCannon();

	DualCannonImpactProperties dualImpact();

}
