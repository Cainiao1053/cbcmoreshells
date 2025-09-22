package com.cainiao1053.cbcmoreshells.cannons.projectile_rack;

import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.material.ProjectileRackMaterial;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.projectile_rack_end.ProjectileRackEnd;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.projectile_rack_end.ProjectileRackEndBlockEntity;
//import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.material.TorpedoTubeMaterial;
//import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEnd;
//import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEndBlockEntity;
import com.simibubi.create.foundation.block.IBE;



public abstract class SolidProjectileRackBlock<TE extends ProjectileRackEndBlockEntity> extends ProjectileRackBaseBlock implements IBE<TE> {

	public SolidProjectileRackBlock(Properties properties, ProjectileRackMaterial material) {
		super(properties, material);
	}

	@Override public ProjectileRackEnd getDefaultOpeningType() { return ProjectileRackEnd.CLOSED; }

}
