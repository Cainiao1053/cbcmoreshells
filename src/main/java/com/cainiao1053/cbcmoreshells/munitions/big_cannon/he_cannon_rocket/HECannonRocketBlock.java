package com.cainiao1053.cbcmoreshells.munitions.big_cannon.he_cannon_rocket;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class HECannonRocketBlock extends ShellessShellBlock<HECannonRocketProjectile> {

	public HECannonRocketBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends HECannonRocketProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.HE_CANNON_ROCKET.get();
	}

}
