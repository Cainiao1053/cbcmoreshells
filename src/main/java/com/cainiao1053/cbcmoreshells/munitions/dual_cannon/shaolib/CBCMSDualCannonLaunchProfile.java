package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

public record CBCMSDualCannonLaunchProfile(int baseLifetimeTicks) {
	public CBCMSDualCannonLaunchProfile {
		baseLifetimeTicks = Math.max(0, baseLifetimeTicks);
	}

	/**
	 * Not the authority on lifetime: {@code MountedDualCannonContraption.spawnRound} passes
	 * {@code DualCannonLaunchProperties.baseLifetime} into {@link #resolveLifetimeTicks} instead, so
	 * this field never reaches a live shell. Read the properties, not this.
	 */
	@Override
	public int baseLifetimeTicks() {
		return this.baseLifetimeTicks;
	}

	/** Final lifetime of a round: the shell's own base plus whatever the barrel material adds. */
	public int resolveLifetimeTicks(int baseLifetime, int barrelBonusTicks, float commandModifier, float equipmentModifier) {
		double total = (baseLifetime + barrelBonusTicks) * (double) commandModifier * (double) equipmentModifier;
		return Math.max(1, (int) total);
	}

}
