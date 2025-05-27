package com.cainiao1053.cbcmoreshells.munitions.big_cannon.apbc_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class APBCShotBlock extends ShellessInertProjectileBlock<APBCShotProjectile> {

	public APBCShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends APBCShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.APBC_SHOT.get();
	}

}
