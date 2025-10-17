package com.cainiao1053.cbcmoreshells.cannons.dual_cannon.dual_cannon_end;

import net.minecraft.util.StringRepresentable;

public enum DualCannonEnd implements StringRepresentable {
	CLOSED("closed"),
	OPEN("open"),
	PARTIAL("partial");
	
	private final String serializedName;
	
	private DualCannonEnd(String name) {
		this.serializedName = name;
	}
	
	@Override public String getSerializedName() { return this.serializedName; }
	
	public static DualCannonEnd getOpeningType(float openProgress) {
		return openProgress <= 0 ? CLOSED : openProgress >= 1 ? OPEN : PARTIAL;
	}
}