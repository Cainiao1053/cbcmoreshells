package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.api.projectile.ProjectileInstance;
import com.verr1.shaolib.api.projectile.data.ProjectileSerializationContext;
import com.verr1.shaolib.munitions.projectile.shell.FuzedShellSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

/**
 * Persists the dual-cannon-specific parts of {@link DualCannonState}; the shared shell fields are
 * handled by {@link FuzedShellSerializer}.
 *
 * <p>{@code hitCallback} is intentionally not saved — it is a live reference to the firing
 * contraption and cannot be meaningfully rebuilt on load.
 */
public class DualCannonSerializer<S extends DualCannonState> extends FuzedShellSerializer<S> {

	private static final String DURABILITY_MODIFIER_KEY = "DurabilityModifier";
	private static final String LAUNCH_Y_KEY = "LaunchY";
	private static final String LIFETIME_TICKS_KEY = "LifetimeTicks";
	private static final String TRAIL_ANCHOR_KEY = "TrailAnchor";
	private static final String TRAIL_COOLDOWN_KEY = "TrailCooldown";
	private static final String TRAIL_STAGE_KEY = "TrailStage";

	@Override
	protected void saveAdditional(ProjectileSerializationContext context, ProjectileInstance projectile, S state,
								  CompoundTag tag) {
		super.saveAdditional(context, projectile, state, tag);
		tag.putDouble(DURABILITY_MODIFIER_KEY, state.durabilityModifier());
		if (state.hasLaunchY()) {
			tag.putDouble(LAUNCH_Y_KEY, state.launchY());
		}
		tag.putInt(LIFETIME_TICKS_KEY, state.lifetimeTicks());
		tag.putInt(TRAIL_COOLDOWN_KEY, state.trailCooldown());
		tag.putInt(TRAIL_STAGE_KEY, state.trailStage());
		writeVec3(tag, TRAIL_ANCHOR_KEY, state.trailAnchor());
	}

	@Override
	protected void loadAdditional(ProjectileSerializationContext context, ProjectileInstance projectile, S state,
								  CompoundTag tag) {
		super.loadAdditional(context, projectile, state, tag);
		state.setDurabilityModifier(tag.contains(DURABILITY_MODIFIER_KEY) ? tag.getDouble(DURABILITY_MODIFIER_KEY) : 1.0);
		if (tag.contains(LAUNCH_Y_KEY)) {
			state.setLaunchY(tag.getDouble(LAUNCH_Y_KEY));
		}
		state.setLifetimeTicks(tag.getInt(LIFETIME_TICKS_KEY));
		state.setTrailCooldown(tag.getInt(TRAIL_COOLDOWN_KEY));
		state.setTrailStage(tag.getInt(TRAIL_STAGE_KEY));
		if (tag.contains(TRAIL_ANCHOR_KEY)) {
			state.setTrailAnchor(readVec3(tag, TRAIL_ANCHOR_KEY));
		}
	}

	private static void writeVec3(CompoundTag tag, String key, Vec3 value) {
		CompoundTag vector = new CompoundTag();
		vector.putDouble("X", value.x);
		vector.putDouble("Y", value.y);
		vector.putDouble("Z", value.z);
		tag.put(key, vector);
	}

	private static Vec3 readVec3(CompoundTag tag, String key) {
		CompoundTag vector = tag.getCompound(key);
		return new Vec3(vector.getDouble("X"), vector.getDouble("Y"), vector.getDouble("Z"));
	}

}
