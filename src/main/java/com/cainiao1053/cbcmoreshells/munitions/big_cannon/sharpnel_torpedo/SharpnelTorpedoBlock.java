package com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.GeneralCannonTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;



public class SharpnelTorpedoBlock extends GeneralCannonTorpedoBlock<SharpnelTorpedoProjectile> {

	public SharpnelTorpedoBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends SharpnelTorpedoProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.SHARPNEL_TORPEDO.get();
	}

}
