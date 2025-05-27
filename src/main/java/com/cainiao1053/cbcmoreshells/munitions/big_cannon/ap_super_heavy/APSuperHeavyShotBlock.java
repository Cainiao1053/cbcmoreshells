package com.cainiao1053.cbcmoreshells.munitions.big_cannon.ap_super_heavy;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class APSuperHeavyShotBlock extends ShellessInertProjectileBlock<APSuperHeavyShotProjectile> {

	public APSuperHeavyShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends APSuperHeavyShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.AP_SUPER_HEAVY_SHOT.get();
	}

}
