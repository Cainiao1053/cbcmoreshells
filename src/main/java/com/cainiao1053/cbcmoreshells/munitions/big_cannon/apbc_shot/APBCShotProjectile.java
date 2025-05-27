package com.cainiao1053.cbcmoreshells.munitions.big_cannon.apbc_shot;

import javax.annotation.Nonnull;

import com.cainiao1053.cbcmoreshells.CBCMSBlocks;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessInertBigCannonProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonShellessShellProperties;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.ShellessInertBigCannonProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;

public class APBCShotProjectile extends ShellessInertBigCannonProjectile {

	protected int lifetime = this.getAllProperties().lifetime();

	public APBCShotProjectile(EntityType<? extends APBCShotProjectile> type, Level level) {
		super(type, level);
	}




	@Override
	public BlockState getRenderedBlockState() {
		return CBCMSBlocks.APBC_SHOT.getDefaultState().setValue(BlockStateProperties.FACING, Direction.NORTH);
	}


	@Override
	public void setChargePower(float power) {
		float maxCharges = this.getAllProperties().maxCharges();
		this.tooManyCharges = maxCharges >= 0 && power > maxCharges;
		setLifetime(lifetime);
	}

	@Nonnull
	@Override
	protected BigCannonProjectilePropertiesComponent getBigCannonProjectileProperties() {
		return this.getAllProperties().bigCannonProperties();
	}

	@Nonnull
	@Override
	public EntityDamagePropertiesComponent getDamageProperties() {
		return this.getAllProperties().damage();
	}

	@Nonnull
	@Override
	protected BallisticPropertiesComponent getBallisticProperties() {
		return this.getAllProperties().ballistics();
	}

	protected ShellessInertBigCannonProperties getAllProperties() {
		return CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE.getPropertiesOf(this);
	}

}
