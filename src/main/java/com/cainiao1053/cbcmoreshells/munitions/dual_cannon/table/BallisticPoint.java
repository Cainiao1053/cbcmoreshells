package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

/**
 * One distance column of a firing table. Every field here is fixed by the shell alone — muzzle
 * velocity, drag and gravity carry no barrel modifier — so a whole column is shared by every
 * material row.
 *
 * @param range       horizontal distance from the muzzle, in blocks
 * @param elevation   barrel elevation that lands the shell there, in radians
 * @param flightTicks time from muzzle to target
 * @param impactSpeed speed on arrival, in blocks per tick
 */
public record BallisticPoint(double range, double elevation, double flightTicks, double impactSpeed) {

	/** Seconds of flight, for display. */
	public double flightSeconds() {
		return this.flightTicks / 20.0;
	}

	/** Whether a shell that lives {@code lifetimeTicks} is still alive when it gets here. */
	public boolean reachableWithin(int lifetimeTicks) {
		return this.flightTicks <= lifetimeTicks;
	}

}
