package com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_shrapnel_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class AntiairShrapnelShellBlock extends ShellessShellBlock<AntiairShrapnelShellProjectile> {

	public AntiairShrapnelShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMSMunitionPropertiesHandlers.SHRAPNEL_SHELLESS_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends AntiairShrapnelShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.ANTIAIR_SHRAPNEL_SHELL.get();
	}

}
