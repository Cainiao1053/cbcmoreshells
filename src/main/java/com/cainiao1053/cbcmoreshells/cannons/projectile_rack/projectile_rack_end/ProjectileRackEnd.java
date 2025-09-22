package com.cainiao1053.cbcmoreshells.cannons.projectile_rack.projectile_rack_end;

import net.minecraft.util.StringRepresentable;

public enum ProjectileRackEnd implements StringRepresentable {
	CLOSED("closed"),
	OPEN("open"),
	PARTIAL("partial");
	
	private final String serializedName;
	
	private ProjectileRackEnd(String name) {
		this.serializedName = name;
	}
	
	@Override public String getSerializedName() { return this.serializedName; }
	
	public static ProjectileRackEnd getOpeningType(float openProgress) {
		return openProgress <= 0 ? CLOSED : openProgress >= 1 ? OPEN : PARTIAL;
	}
}