package com.cainiao1053.cbcmoreshells.munitions.big_cannon.inferior_he_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.datagen.assets.CBCMSLangGen;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;
import rbasamoyai.createbigcannons.munitions.big_cannon.SimpleShellBlock;


public class InferiorHEShellBlock extends SimpleShellBlock<InferiorHEShellProjectile> {

	public InferiorHEShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}


	@Override
	public EntityType<? extends InferiorHEShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.Inferior_HE_SHELL.get();
	}




}
