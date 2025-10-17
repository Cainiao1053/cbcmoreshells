package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.extended_ap_shot;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.InertDualCannonProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.config.DualCannonPropertiesHandler;
import net.minecraft.world.entity.EntityType;



public class ExtendedAPShotBlock extends InertDualCannonProjectileBlock<ExtendedAPShotProjectile> {

	public ExtendedAPShotBlock(Properties properties) {
		super(properties);
	}



	@Override
	public EntityType<? extends ExtendedAPShotProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.EXTENDED_AP_SHOT.get();
	}

}
