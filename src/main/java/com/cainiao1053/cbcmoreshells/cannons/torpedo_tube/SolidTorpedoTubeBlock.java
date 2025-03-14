package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube;

import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEnd;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEndBlockEntity;
import com.simibubi.create.foundation.block.IBE;



public abstract class SolidTorpedoTubeBlock<TE extends TorpedoTubeEndBlockEntity> extends TorpedoTubeBaseBlock implements IBE<TE> {

	public SolidTorpedoTubeBlock(Properties properties, TorpedoTubeMaterial material) {
		super(properties, material);
	}

	@Override public TorpedoTubeEnd getDefaultOpeningType() { return TorpedoTubeEnd.CLOSED; }

}
