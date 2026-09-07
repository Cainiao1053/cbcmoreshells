package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesHandler;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesProvider;

/**
 * Technical reserve: the alternative, area-sampled penetration model.
 *
 * <p>This is the algorithm that sat behind {@code AbstractDualCannonProjectile.alternativePenetration()}
 * (which always returned {@code false}, so the branch in {@code NormalSAPShellProjectile} was dead
 * code) together with {@code getHitNearbyAverageToughness}. It is kept here — rather than being
 * dropped with the entity projectiles — because it is a genuinely different design from the
 * momentum model and may be worth switching to later.
 *
 * <h2>How it differs from the shipped model</h2>
 * The live model ({@link DualCannonPenetrationModel}, driven by {@link DualCannonImpactProperties})
 * is momentum-based: it compares {@code mass * velocity * bonus * incidence} against the toughness
 * of the single block that was hit, with a probabilistic band in between. It is sensitive to
 * impact angle and speed, and a thin plate stops a shell about as well as a thick one.
 *
 * <p>This alternative is armour-thickness-based: it averages toughness over a 3x3x3-deep volume
 * behind the impact face (27 samples) and compares it against a purely mass-derived penetration
 * figure. Velocity and incidence do not enter into it, so it rewards spaced/laminated armour and
 * makes shell calibre the dominant variable — closer to how real armour penetration tables behave.
 *
 * <h2>Wiring it in</h2>
 * Not currently called. {@link DualCannonBehavior#tickServer} passes
 * {@code DualCannonPenetrationModel::resolve} to {@code MunitionImpactSweep.sweep(...)} as its block
 * impact resolver; swapping in a resolver that consults {@link #penetrates} is the whole change.
 * Note that a resolver must still produce a full {@code MunitionImpactOutcome} — durability mass
 * accounting, bounce handling and the stop/spall effects all live in the resolver, so a replacement
 * has to cover those too rather than only answering the penetrate/stop question.
 */
public final class CBCMSLegacyPenetration {

	/** Depth, in blocks, sampled behind the impact face. */
	private static final int SAMPLE_DEPTH = 3;
	/** Half-width of the sampled square, so the footprint is (2*HALF_WIDTH+1)^2. */
	private static final int SAMPLE_HALF_WIDTH = 1;

	private CBCMSLegacyPenetration() {}

	/**
	 * Mean armour toughness of the 3x3 column running {@value #SAMPLE_DEPTH} blocks back from
	 * {@code pos} along {@code face}.
	 *
	 * <p>Note the divisor is a fixed 27 rather than the actual sample count, matching the original
	 * implementation; it is correct for the shipped constants and is left as-is so the tuning
	 * carries over unchanged.
	 */
	public static double averageToughnessBehind(Level level, BlockPos pos, Direction face) {
		double accumulatedArmor = 0;
		for (int depth = 0; depth < SAMPLE_DEPTH; depth++) {
			for (int a = -SAMPLE_HALF_WIDTH; a <= SAMPLE_HALF_WIDTH; a++) {
				for (int b = -SAMPLE_HALF_WIDTH; b <= SAMPLE_HALF_WIDTH; b++) {
					BlockPos targetPos = switch (face) {
						case UP -> pos.offset(a, -depth, b);
						case DOWN -> pos.offset(a, depth, b);
						case EAST -> pos.offset(-depth, a, b);
						case WEST -> pos.offset(depth, a, b);
						case SOUTH -> pos.offset(a, b, -depth);
						case NORTH -> pos.offset(a, b, depth);
					};
					BlockState state = level.getBlockState(targetPos);
					BlockArmorPropertiesProvider blockArmor = BlockArmorPropertiesHandler.getProperties(state);
					accumulatedArmor += blockArmor.toughness(level, state, targetPos, true);
				}
			}
		}
		return accumulatedArmor / 27;
	}

	/**
	 * Toughness this shell can defeat, as a function of its current durability mass alone.
	 * Quadratic, so calibre scales super-linearly.
	 */
	public static double penetrationToughness(double durabilityMass) {
		return durabilityMass * durabilityMass * 0.4 + durabilityMass * 1.2 - 1.5;
	}

	/** Deterministic penetration test: no velocity, no incidence, no randomness. */
	public static boolean penetrates(Level level, BlockPos pos, Direction face, double durabilityMass) {
		return penetrationToughness(durabilityMass) > averageToughnessBehind(level, pos, face);
	}

}
