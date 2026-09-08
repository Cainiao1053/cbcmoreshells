package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

public record CBCMSDualCannonLaunchProfile(int baseLifetimeTicks) {
	public CBCMSDualCannonLaunchProfile {
		baseLifetimeTicks = Math.max(0, baseLifetimeTicks);
	}
	public int resolveLifetimeTicks(int barrelBonusTicks, float commandModifier, float equipmentModifier) {
		double total = (this.baseLifetimeTicks + barrelBonusTicks) * (double) commandModifier * (double) equipmentModifier;
		return Math.max(1, (int) total);
	}

}
