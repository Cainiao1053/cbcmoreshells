package com.cainiao1053.cbcmoreshells.munitions.big_cannon.hesh_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;
import rbasamoyai.createbigcannons.munitions.big_cannon.SimpleShellBlock;


public class HESHShellBlock extends SimpleShellBlock<HESHShellProjectile> {

	public HESHShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends HESHShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.HESH_SHELL.get();
	}




}
