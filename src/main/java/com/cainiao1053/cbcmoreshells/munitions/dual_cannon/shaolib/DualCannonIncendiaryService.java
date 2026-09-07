package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

/**
 * Starts fires when an incendiary dual cannon shell detonates.
 *
 * <p>Split out from {@link DualCannonBehavior} so the fire-spreading logic — which has to reach into
 * sub-level/ship worlds through {@code CBCMSCompatTransformers} — stays out of the flight pipeline
 * and can be swapped or stubbed in tests.
 *
 * <p>{@code fireChance} and {@code fireRange} arrive already scaled by the shell's durability
 * modifier; implementations should use them as given.
 */
@FunctionalInterface
public interface DualCannonIncendiaryService {

	DualCannonIncendiaryService NOOP = (level, position, fireChance, fireRange) -> {};

	void ignite(ServerLevel level, Vec3 position, float fireChance, int fireRange);

	/**
	 * Process-wide holder for the active service.
	 *
	 * <p>Deliberately a single slot rather than a listener list: igniting twice would double the
	 * fire, so callers replacing the service should chain to the previous one themselves if they want
	 * both to run.
	 */
	final class Holder {

		private static volatile DualCannonIncendiaryService service = NOOP;

		private Holder() {}

		public static DualCannonIncendiaryService get() {
			return service;
		}

		public static void set(DualCannonIncendiaryService service) {
			Holder.service = Objects.requireNonNull(service, "service");
		}

		public static void reset() {
			service = NOOP;
		}

	}

}
