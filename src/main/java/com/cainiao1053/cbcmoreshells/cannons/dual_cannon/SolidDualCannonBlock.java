package com.cainiao1053.cbcmoreshells.cannons.dual_cannon;


import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.dual_cannon_end.DualCannonEnd;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.dual_cannon_end.DualCannonEndBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.simibubi.create.foundation.block.IBE;



public abstract class SolidDualCannonBlock<TE extends DualCannonEndBlockEntity> extends DualCannonBaseBlock implements IBE<TE> {

	public SolidDualCannonBlock(Properties properties, DualCannonMaterial material) {
		super(properties, material);
	}

	@Override public DualCannonEnd getDefaultOpeningType() { return DualCannonEnd.CLOSED; }

}
