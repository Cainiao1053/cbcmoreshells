package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

@FunctionalInterface
public interface DualCannonIncendiaryService {

	DualCannonIncendiaryService NOOP = (level, position, fireChance, fireRange) -> {};

	void ignite(ServerLevel level, Vec3 position, float fireChance, int fireRange);

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
