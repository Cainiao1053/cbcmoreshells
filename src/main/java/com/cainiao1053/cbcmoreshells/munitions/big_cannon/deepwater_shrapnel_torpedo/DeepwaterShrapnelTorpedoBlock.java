package com.cainiao1053.cbcmoreshells.munitions.big_cannon.deepwater_shrapnel_torpedo;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.GeneralCannonTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;



public class DeepwaterShrapnelTorpedoBlock extends GeneralCannonTorpedoBlock<DeepwaterShrapnelTorpedoProjectile> {

	public DeepwaterShrapnelTorpedoBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends DeepwaterShrapnelTorpedoProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.DEEPWATER_SHRAPNEL_TORPEDO.get();
	}

}
