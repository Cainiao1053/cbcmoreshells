package com.cainiao1053.cbcmoreshells.munitions.big_cannon.baguette_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import net.minecraft.world.entity.EntityType;



public class BaguetteShotBlock extends ShellessInertProjectileBlock<BaguetteShotProjectile> {

	public BaguetteShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends BaguetteShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.BAGUETTE_SHOT.get();
	}

}
