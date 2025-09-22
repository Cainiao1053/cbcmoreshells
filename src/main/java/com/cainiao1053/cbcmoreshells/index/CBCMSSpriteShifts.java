package com.cainiao1053.cbcmoreshells.index;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;

import rbasamoyai.createbigcannons.connected_textures.CBCCTSpriteShifter;

public class CBCMSSpriteShifts {

	public static final CTSpriteShiftEntry
		NETHERSTEEL_SLIDING_BREECH_SIDE = slidingBreech("nethersteel_sliding_breech_side"),
		NETHERSTEEL_SLIDING_BREECH_SIDE_HOLE = slidingBreech("nethersteel_sliding_breech_side_hole");


	private static CTSpriteShiftEntry slidingBreech(String name) {
		return CBCCTSpriteShifter.getCT(AllCTTypes.VERTICAL, 1, Cbcmoreshells.resource("block/sliding_breech/" + name),
			Cbcmoreshells.resource("block/sliding_breech/" + name + "_connected"));
	}

}
