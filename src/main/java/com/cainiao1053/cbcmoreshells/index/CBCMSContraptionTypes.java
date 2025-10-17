package com.cainiao1053.cbcmoreshells.index;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedDualCannonContraption;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedProjectileRackContraption;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;

import com.simibubi.create.content.contraptions.ContraptionType;



public class CBCMSContraptionTypes {

	public static final ContraptionType
		TORPEDO_TUBE = ContraptionType.register(Cbcmoreshells.resource("mounted_torpedo_tube").toString(), MountedTorpedoTubeContraption::new),
	    PROJECTILE_RACK = ContraptionType.register(Cbcmoreshells.resource("mounted_projectile_rack").toString(), MountedProjectileRackContraption::new),
		DUAL_CANNON = ContraptionType.register(Cbcmoreshells.resource("mounted_dual_cannon").toString(), MountedDualCannonContraption::new);

	public static void prepare() {
	}

}
