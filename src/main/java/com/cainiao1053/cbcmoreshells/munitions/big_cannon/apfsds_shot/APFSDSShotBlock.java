package com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class APFSDSShotBlock extends ShellessInertProjectileBlock<APFSDSShotProjectile> {

	public APFSDSShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends APFSDSShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.APFSDS_SHOT.get();
	}

}
