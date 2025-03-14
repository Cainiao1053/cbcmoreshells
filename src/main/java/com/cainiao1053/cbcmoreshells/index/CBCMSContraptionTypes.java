package com.cainiao1053.cbcmoreshells.index;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.cannon_control.contraption.MountedTorpedoTubeContraption;

import com.simibubi.create.content.contraptions.ContraptionType;



public class CBCMSContraptionTypes {

	public static final ContraptionType
		TORPEDO_TUBE = ContraptionType.register(Cbcmoreshells.resource("mounted_cannon").toString(), MountedTorpedoTubeContraption::new);

	public static void prepare() {
	}

}
