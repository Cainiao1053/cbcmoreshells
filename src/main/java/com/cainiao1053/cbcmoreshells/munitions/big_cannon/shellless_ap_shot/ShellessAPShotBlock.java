package com.cainiao1053.cbcmoreshells.munitions.big_cannon.shellless_ap_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class ShellessAPShotBlock extends ShellessInertProjectileBlock<ShellessAPShotProjectile> {

	public ShellessAPShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends ShellessAPShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.SHELLESS_AP_SHOT.get();
	}

}
