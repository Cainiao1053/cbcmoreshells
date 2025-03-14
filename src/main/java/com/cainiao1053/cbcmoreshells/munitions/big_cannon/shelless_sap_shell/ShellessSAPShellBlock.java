package com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_sap_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class ShellessSAPShellBlock extends ShellessShellBlock<ShellessSAPShellProjectile> {

	public ShellessSAPShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends ShellessSAPShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.SHELLESS_SAP_SHELL.get();
	}

}
