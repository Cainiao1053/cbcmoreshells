package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.CBCMSCompatTransformers;
import com.verr1.shaolib.munitions.projectile.dual_cannon.IncendiaryEffectService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidBlobBurst;

/**
 * cbcms' implementation of Shaolib's incendiary SPI.
 *
 * <p>{@code DualCannonShellBehavior} calls {@code IncendiaryEffectService.Holder.get().ignite(...)}
 * when an {@code INCENDIARY} shell detonates, having already applied the durability-derived
 * chance/range multipliers. The library default is a no-op, so without this installed incendiary
 * shells would explode but never start fires.
 */
public final class CBCMSIncendiaryEffectService implements IncendiaryEffectService {

	/** Radius around the detonation searched for sub-level (ship) bodies to also set alight. */
	private static final double SUBLEVEL_SEARCH_INFLATION = 3.0D;

	private final IncendiaryEffectService delegate;

	private CBCMSIncendiaryEffectService(IncendiaryEffectService delegate) {
		this.delegate = delegate;
	}

	/**
	 * Installs this service, chaining to whatever was already registered.
	 *
	 * <p>{@code Holder} is a process-wide singleton, so a plain {@code set} would silently disable
	 * any other mod's incendiary handling in the same pack. Chaining keeps both alive.
	 */
	public static void install() {
		IncendiaryEffectService previous = IncendiaryEffectService.Holder.get();
		IncendiaryEffectService.Holder.set(new CBCMSIncendiaryEffectService(previous));
	}

	@Override
	public void ignite(ServerLevel level, Vec3 position, float fireChance, int fireRange) {
		if (this.delegate != null && this.delegate != IncendiaryEffectService.NOOP) {
			this.delegate.ignite(level, position, fireChance, fireRange);
		}
		if (Math.random() > fireChance) return;

		spawnFire(BlockPos.containing(position), level, fireRange);
		AABB searchRegion = new AABB(position, position).inflate(SUBLEVEL_SEARCH_INFLATION);
		CBCMSCompatTransformers.spawnFireOnSublevel(level, searchRegion, fireRange, position);
	}

	/**
	 * Scatters fire through a cube around {@code root}. Preserved verbatim in behaviour from the old
	 * {@code NormalIncendiaryHEShellProjectile.spawnFire}.
	 */
	public static void spawnFire(BlockPos root, Level level, int radius) {
		float chance = FluidBlobBurst.getBlockAffectChance();
		if (chance == 0) return;
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
				level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F,
					level.getRandom().nextFloat() * 0.4F + 0.8F);
				level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 11);
				level.gameEvent(null, GameEvent.BLOCK_PLACE, pos);
			}
		}
	}

}
