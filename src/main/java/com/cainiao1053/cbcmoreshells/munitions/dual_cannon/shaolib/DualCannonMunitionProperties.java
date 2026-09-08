package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.munitions.config.properties.CbcLikeMunitionProperties;

public interface DualCannonMunitionProperties extends CbcLikeMunitionProperties {

	DualCannonLaunchProperties dualCannon();

	DualCannonImpactProperties dualImpact();

}
