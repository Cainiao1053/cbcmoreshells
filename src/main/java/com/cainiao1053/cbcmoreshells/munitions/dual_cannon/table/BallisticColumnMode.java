package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

/**
 * What the distance columns of a firing table show. The toggle button cycles through whatever list
 * the shell's item returns from {@code ballisticModes()}.
 *
 * <p>Only {@link #MOMENTUM} varies between barrel materials. The other two come straight off the
 * ballistic skeleton, so every material row would print the same numbers — a table showing one of
 * those can collapse its per-material columns and just read the skeleton.
 */
public enum BallisticColumnMode {

	/** Equivalent momentum on arrival. Varies by material through durability mass. */
	MOMENTUM("cbcmoreshells.firing_table.column.momentum", StatFormat.PLAIN1, true),
	/** Time from muzzle to target. Same for every material. */
	FLIGHT_TIME("cbcmoreshells.firing_table.column.flight_time", StatFormat.SECONDS, false),
	/** Speed on arrival. Same for every material. */
	IMPACT_SPEED("cbcmoreshells.firing_table.column.impact_speed", StatFormat.M_PER_SEC, false);

	private final String key;
	private final StatFormat format;
	private final boolean materialSensitive;

	BallisticColumnMode(String key, StatFormat format, boolean materialSensitive) {
		this.key = key;
		this.format = format;
		this.materialSensitive = materialSensitive;
	}

	public String key() {
		return this.key;
	}

	public StatFormat format() {
		return this.format;
	}

	/** False when every material row prints identical values. */
	public boolean materialSensitive() {
		return this.materialSensitive;
	}

}
