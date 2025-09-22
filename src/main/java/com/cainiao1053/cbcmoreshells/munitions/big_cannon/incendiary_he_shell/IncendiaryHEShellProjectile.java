package com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell;

import javax.annotation.Nonnull;

import com.cainiao1053.cbcmoreshells.CBCMSBlocks;
import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.AbstractTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessFuzedBigCannonProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonIncendiaryShellProperties;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonShellessShellProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCBlocks;
import rbasamoyai.createbigcannons.index.CBCEntityTypes;
import rbasamoyai.createbigcannons.multiloader.IndexPlatform;
import rbasamoyai.createbigcannons.munitions.ProjectileContext;
import rbasamoyai.createbigcannons.munitions.ShellExplosion;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.EndFluidStack;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidBlobBurst;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidExplosion;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.CBCProjectileBurst;

public class IncendiaryHEShellProjectile extends ShellessFuzedBigCannonProjectile {

	protected int lifetime = this.getAllProperties().lifetime();

	public IncendiaryHEShellProjectile(EntityType<? extends IncendiaryHEShellProjectile> type, Level level) {
		super(type, level);
	}

	private boolean canFire = true;

	@Override
	protected void detonate(Position position) {
//		double fireRate = this.getAllProperties().fireChance();
//		boolean doFire = Math.random() < fireRate;
        ShellExplosion explosion = new ShellExplosion(this.level(), this, this.indirectArtilleryFire(false), position.x(),
			position.y(), position.z(), this.getAllProperties().explosion().explosivePower(), false,
			CBCConfigs.SERVER.munitions.damageRestriction.get().explosiveInteraction());
		CreateBigCannons.handleCustomExplosion(this.level(), explosion);
		//BlockPos pos = BlockPos.containing(position);

//		if(doFire){
//			if(!level().isClientSide){
//				spawnFire(pos,level(),this.getAllProperties().fireRange());
//			}
//		}
	}

//	@Override
//	protected void onHitBlock(BlockHitResult result) {
//		double fireRate = this.getAllProperties().fireChance();
//		boolean doFire = Math.random() < fireRate;
//		if(canFire){
//			if(!level().isClientSide){
//				BlockPos pos = result.getBlockPos();
//				spawnFire(pos,level(),this.getAllProperties().fireRange());
//			}
//		}
//		this.canFire = false;
//		super.onHitBlock(result);
//	}

	@Override
	protected ImpactResult calculateBlockPenetration(ProjectileContext projectileContext, BlockState state, BlockHitResult blockHitResult) {
		ImpactResult result = super.calculateBlockPenetration(projectileContext, state, blockHitResult);
		double fireRate = this.getAllProperties().fireChance();
		boolean doFire = Math.random() < fireRate;
		if(doFire && canFire){
			if(!level().isClientSide){
				BlockPos pos = blockHitResult.getBlockPos();
				spawnFire(pos,level(),this.getAllProperties().fireRange());
			}
		}
		this.canFire = false;
		return result;
	}

	@Override
	public BlockState getRenderedBlockState() {
		return CBCMSBlocks.INCENDIARY_HE_SHELL.getDefaultState().setValue(BlockStateProperties.FACING, Direction.NORTH);
	}

	@Nonnull
	@Override
	protected BigCannonFuzePropertiesComponent getFuzeProperties() {
		return this.getAllProperties().fuze();
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

	public static void spawnFire(BlockPos root, Level level, int radius) {
		float chance = FluidBlobBurst.getBlockAffectChance();
		if (chance == 0)
			return;
		AABB bounds = new AABB(root).inflate(radius);
		BlockPos pos1 = BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ);
		BlockPos pos2 = BlockPos.containing(bounds.maxX, bounds.maxY, bounds.maxZ);
		for (BlockPos pos : BlockPos.betweenClosed(pos1, pos2)) {
			if (level.getRandom().nextFloat() > chance)
				continue;
			BlockState state = level.getBlockState(pos);
			if (level.isEmptyBlock(pos)) {
				level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
			} else if (CandleBlock.canLight(state) || CampfireBlock.canLight(state) || CandleCakeBlock.canLight(state)) {
				level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
				level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 11);
				level.gameEvent(null, GameEvent.BLOCK_PLACE, pos);
			}
		}
	}

	protected BigCannonIncendiaryShellProperties getAllProperties() {
		return CBCMSMunitionPropertiesHandlers.INCENDIARY_SHELL_PROJECTILE.getPropertiesOf(this);
	}

}
