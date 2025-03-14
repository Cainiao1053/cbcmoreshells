package com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_he_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class AntiairHEShellBlock extends ShellessShellBlock<AntiairHEShellProjectile> {

	public AntiairHEShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends AntiairHEShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.ANTIAIR_HE_SHELL.get();
	}

}
