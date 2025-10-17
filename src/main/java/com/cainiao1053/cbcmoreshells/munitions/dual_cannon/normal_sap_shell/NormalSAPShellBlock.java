package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.normal_sap_shell;

import com.cainiao1053.cbcmoreshells.CBCMSEntityTypes;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessShellBlock;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.GeneralDualCannonProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.FuzedRackedProjectileBlock;
import com.cainiao1053.cbcmoreshells.munitions.racked_projectile.GeneralRackedProjectileBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;


public class NormalSAPShellBlock extends GeneralDualCannonProjectileBlock<NormalSAPShellProjectile> {

	public NormalSAPShellBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isBaseFuze() {
		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).fuze().baseFuze();
	}

//	@Override
//	public int getLifetimeFromBlock() {
//		return CBCMSMunitionPropertiesHandlers.RACKED_PROJECTILE.getPropertiesOf(this.getAssociatedEntityType()).lifetime();
//	}

	@Override
	public EntityType<? extends NormalSAPShellProjectile> getAssociatedEntityType() {
		return CBCMSEntityTypes.NORMAL_SAP_SHELL.get();
	}

}
