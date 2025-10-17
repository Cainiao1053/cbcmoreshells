package com.cainiao1053.cbcmoreshells.index;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;

import rbasamoyai.createbigcannons.connected_textures.CBCCTSpriteShifter;
import rbasamoyai.createbigcannons.index.CBCCTTypes;

public class CBCMSSpriteShifts {

	public static final CTSpriteShiftEntry
		NETHERSTEEL_SLIDING_BREECH_SIDE = slidingBreech("nethersteel_sliding_breech_side"),
		NETHERSTEEL_SLIDING_BREECH_SIDE_HOLE = slidingBreech("nethersteel_sliding_breech_side_hole"),
		STEEL_DUAL_CANNON_BARREL = dualCannon("dual_cannon/steel_dual_cannon_barrel"),
		STEEL_DUAL_CANNON_CHAMBER = dualCannon("dual_cannon/steel_dual_cannon_chamber"),
		BRASS_DUAL_CANNON_BARREL = dualCannon("dual_cannon/brass_dual_cannon_barrel"),
		BRASS_DUAL_CANNON_CHAMBER = dualCannon("dual_cannon/brass_dual_cannon_chamber"),
		TOUGH_STEEL_DUAL_CANNON_BARREL = dualCannon("dual_cannon/tough_steel_dual_cannon_barrel"),
		TOUGH_STEEL_DUAL_CANNON_CHAMBER = dualCannon("dual_cannon/tough_steel_dual_cannon_chamber"),
		NETHER_STEEL_DUAL_CANNON_BARREL = dualCannon("dual_cannon/nether_steel_dual_cannon_barrel"),
		NETHER_STEEL_DUAL_CANNON_CHAMBER = dualCannon("dual_cannon/nether_steel_dual_cannon_chamber"),
		ROSEQUARTZ_BRASS_DUAL_CANNON_BARREL = dualCannon("dual_cannon/rosequartz_brass_dual_cannon_barrel"),
		ROSEQUARTZ_BRASS_DUAL_CANNON_CHAMBER = dualCannon("dual_cannon/rosequartz_brass_dual_cannon_chamber");




	private static CTSpriteShiftEntry slidingBreech(String name) {
		return CBCCTSpriteShifter.getCT(AllCTTypes.VERTICAL, 1, Cbcmoreshells.resource("block/sliding_breech/" + name),
			Cbcmoreshells.resource("block/sliding_breech/" + name + "_connected"));
	}

	private static CTSpriteShiftEntry dualCannon(String name, int spriteScale) {
		return CBCCTSpriteShifter.getCT(CBCCTTypes.CANNON, spriteScale, Cbcmoreshells.resource("block/" + name + "_side"),
				Cbcmoreshells.resource("block/" + name + "_side_connected"));
	}

	private static CTSpriteShiftEntry dualCannon(String name) { return dualCannon(name, 1); }

}
