package com.cainiao1053.cbcmoreshells.munitions.big_cannon.baked_apfsds_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class BakedAPFSDSShotBlock extends ShellessInertProjectileBlock<BakedAPFSDSShotProjectile> {

	public BakedAPFSDSShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends BakedAPFSDSShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.BAKED_APFSDS_SHOT.get();
	}

}
