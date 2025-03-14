package com.cainiao1053.cbcmoreshells.munitions.big_cannon.aphe_cannon_rocket;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class APHECannonRocketBlock extends ShellessShellBlock<APHECannonRocketProjectile> {

	public APHECannonRocketBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends APHECannonRocketProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.APHE_CANNON_ROCKET.get();
	}

}
