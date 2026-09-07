package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.verr1.shaolib.munitions.projectile.shell.FuzedShellState;
import java.util.Objects;
import net.minecraft.world.phys.Vec3;

/**
 * Server-side state for a dual cannon projectile.
 *
 * <p>{@link FuzedShellState} already carries the shared shell bookkeeping (travelled distance,
 * durability mass, penetration/ricochet counts, fuze). This adds what dual cannons need on top:
 *
 * <ul>
 *   <li>{@code durabilityModifier} — the barrel/command/equipment multiplier for this shot. Scales
 *       both the shell's durability mass and its explosive yield.
 *   <li>{@code launchY} — muzzle altitude, used by AA shells to scale their burst with climb.
 *   <li>{@code hitCallback} — transient hook back to the firing contraption.
 *   <li>{@code lifetimeTicks} plus the trail fields — cbcms additions with no Shaolib equivalent.
 * </ul>
 *
 * <p>Owned by cbcms rather than taken from Shaolib's {@code DualCannonShellState}, since that class
 * lives in a package scheduled for removal.
 */
public class DualCannonState extends FuzedShellState {

	/** Sentinel for "not launched yet" — {@link #hasLaunchY()} tests for a finite value. */
	private static final double NO_LAUNCH_Y = Double.NaN;

	private double durabilityModifier = 1.0;
	private double launchY = NO_LAUNCH_Y;
	private transient DualCannonHitCallback hitCallback = DualCannonHitCallback.NOOP;

	private int lifetimeTicks;
	private Vec3 trailAnchor = Vec3.ZERO;
	private int trailCooldown;
	private int trailStage = TRAIL_STAGE_PENDING;

	public static final int TRAIL_STAGE_PENDING = 0;
	public static final int TRAIL_STAGE_TRACING = 1;
	public static final int TRAIL_STAGE_DONE = 2;

	public double durabilityModifier() {
		return this.durabilityModifier;
	}

	public void setDurabilityModifier(double durabilityModifier) {
		this.durabilityModifier = Math.max(0.0, durabilityModifier);
	}

	public double launchY() {
		return this.launchY;
	}

	public void setLaunchY(double launchY) {
		this.launchY = launchY;
	}

	public boolean hasLaunchY() {
		return Double.isFinite(this.launchY);
	}

	public DualCannonHitCallback hitCallback() {
		return this.hitCallback;
	}

	public void setHitCallback(DualCannonHitCallback hitCallback) {
		this.hitCallback = Objects.requireNonNull(hitCallback, "hitCallback");
	}

	/** Per-shot tick budget; zero or less means "no cbcms lifetime cap, runtime limits only". */
	public int lifetimeTicks() {
		return this.lifetimeTicks;
	}

	public void setLifetimeTicks(int lifetimeTicks) {
		this.lifetimeTicks = Math.max(0, lifetimeTicks);
	}

	public Vec3 trailAnchor() {
		return this.trailAnchor;
	}

	public void setTrailAnchor(Vec3 trailAnchor) {
		this.trailAnchor = trailAnchor == null ? Vec3.ZERO : trailAnchor;
	}

	public int trailCooldown() {
		return this.trailCooldown;
	}

	public void setTrailCooldown(int trailCooldown) {
		this.trailCooldown = Math.max(0, trailCooldown);
	}

	public int trailStage() {
		return this.trailStage;
	}

	public void setTrailStage(int trailStage) {
		this.trailStage = trailStage;
	}

}
