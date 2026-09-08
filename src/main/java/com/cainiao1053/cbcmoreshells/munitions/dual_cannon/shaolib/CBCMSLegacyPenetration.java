package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesHandler;
import rbasamoyai.createbigcannons.block_armor_properties.BlockArmorPropertiesProvider;

public final class CBCMSLegacyPenetration {
	private static final int SAMPLE_DEPTH = 3;
	private static final int SAMPLE_HALF_WIDTH = 1;

	private CBCMSLegacyPenetration() {}

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

	public static double penetrationToughness(double durabilityMass) {
		return durabilityMass * durabilityMass * 0.4 + durabilityMass * 1.2 - 1.5;
	}

	public static boolean penetrates(Level level, BlockPos pos, Direction face, double durabilityMass) {
		return penetrationToughness(durabilityMass) > averageToughnessBehind(level, pos, face);
	}

}
