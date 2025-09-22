package com.cainiao1053.cbcmoreshells.munitions.big_cannon.long_range_shrapnel_torpedo;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.GeneralCannonTorpedoBlock;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import net.minecraft.world.entity.EntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;



public class LongRangeShrapnelTorpedoBlock extends GeneralCannonTorpedoBlock<LongRangeShrapnelTorpedoProjectile> {

	public LongRangeShrapnelTorpedoBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

	@Override
	public EntityType<? extends LongRangeShrapnelTorpedoProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.LONG_RANGE_SHRAPNEL_TORPEDO.get();
	}

}
