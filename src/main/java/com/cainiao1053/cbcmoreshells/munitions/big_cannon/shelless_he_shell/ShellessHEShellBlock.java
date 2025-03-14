package com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_he_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class ShellessHEShellBlock extends ShellessShellBlock<ShellessHEShellProjectile> {

	public ShellessHEShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends ShellessHEShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.SHELLESS_HE_SHELL.get();
	}

}
