package com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end;

import net.minecraft.util.StringRepresentable;

public enum TorpedoTubeEnd implements StringRepresentable {
	CLOSED("closed"),
	OPEN("open"),
	PARTIAL("partial");
	
	private final String serializedName;
	
	private TorpedoTubeEnd(String name) {
		this.serializedName = name;
	}
	
	@Override public String getSerializedName() { return this.serializedName; }
	
	public static TorpedoTubeEnd getOpeningType(float openProgress) {
		return openProgress <= 0 ? CLOSED : openProgress >= 1 ? OPEN : PARTIAL;
	}
}