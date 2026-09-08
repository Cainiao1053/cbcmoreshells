package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.google.gson.JsonObject;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyTypes;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record DualCannonIncendiaryProjectileProperties(
	MunitionPropertyComponents.BallisticsProperties ballistics,
	MunitionPropertyComponents.RuntimeProperties runtime,
	MunitionPropertyComponents.ImpactProperties impact,
	MunitionPropertyComponents.EffectProperties effects,
	MunitionPropertyComponents.EntityDamageProperties damage,
	DualCannonLaunchProperties dualCannon,
	DualCannonImpactProperties dualImpact,
	MunitionPropertyComponents.IncendiaryProperties incendiary
) implements DualCannonMunitionProperties {

	public DualCannonIncendiaryProjectileProperties {
		Objects.requireNonNull(ballistics, "ballistics");
		Objects.requireNonNull(runtime, "runtime");
		Objects.requireNonNull(impact, "impact");
		Objects.requireNonNull(effects, "effects");
		Objects.requireNonNull(damage, "damage");
		Objects.requireNonNull(dualCannon, "dualCannon");
		Objects.requireNonNull(dualImpact, "dualImpact");
		Objects.requireNonNull(incendiary, "incendiary");
	}

	public static MunitionPropertyType<DualCannonIncendiaryProjectileProperties> createType(
		ResourceLocation id, Supplier<DualCannonIncendiaryProjectileProperties> fallback) {
		return MunitionPropertyTypes.register(new MunitionPropertyType<>(id,
			DualCannonIncendiaryProjectileProperties.class, fallback,
			DualCannonIncendiaryProjectileProperties::fromJson, DualCannonIncendiaryProjectileProperties::write,
			DualCannonIncendiaryProjectileProperties::read));
	}

	public static DualCannonIncendiaryProjectileProperties normalIncendiaryHeShellFallback() {
		return new DualCannonIncendiaryProjectileProperties(
			new MunitionPropertyComponents.BallisticsProperties(-0.05, 0.01, false, 2.3, 2.0, 0.5, 0.7),
			new MunitionPropertyComponents.RuntimeProperties(180.0, 80, 12),
			new MunitionPropertyComponents.ImpactProperties(0.25, 32, 1, true, true, 0.05, 2.0, 0.15, 0.62, 0.92, 0.25,
				0.08, 0.42),
			new MunitionPropertyComponents.EffectProperties(true, true, true,
				Cbcmoreshells.resource("normal_incendiary_he_shell"),
				new MunitionPropertyComponents.ExplosionProperties(MunitionPropertyComponents.ExplosionKind.CBC_SHELL,
					4.8F, false, false)),
			new MunitionPropertyComponents.EntityDamageProperties(30.0F, true, false, false, 2.0F),
			DualCannonLaunchProperties.DEFAULT,
			DualCannonImpactProperties.DEFAULT,
			new MunitionPropertyComponents.IncendiaryProperties(0.35F, 4));
	}

	private static DualCannonIncendiaryProjectileProperties fromJson(JsonObject json,
																	 DualCannonIncendiaryProjectileProperties fallback) {
		return new DualCannonIncendiaryProjectileProperties(
			MunitionPropertyComponents.BallisticsProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "ballistics"), fallback.ballistics()),
			MunitionPropertyComponents.RuntimeProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "runtime"), fallback.runtime()),
			MunitionPropertyComponents.ImpactProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "impact"), fallback.impact()),
			MunitionPropertyComponents.EffectProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "effects"), fallback.effects()),
			MunitionPropertyComponents.EntityDamageProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "damage"), fallback.damage()),
			DualCannonLaunchProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "dual_cannon"), fallback.dualCannon()),
			DualCannonImpactProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "dual_impact"), fallback.dualImpact()),
			MunitionPropertyComponents.IncendiaryProperties.fromJson(
				MunitionPropertyComponents.optionalObject(json, "incendiary"), fallback.incendiary()));
	}

	private static void write(RegistryFriendlyByteBuf buffer, DualCannonIncendiaryProjectileProperties properties) {
		MunitionPropertyComponents.writeBallistics(buffer, properties.ballistics());
		MunitionPropertyComponents.writeRuntime(buffer, properties.runtime());
		MunitionPropertyComponents.writeImpact(buffer, properties.impact());
		MunitionPropertyComponents.writeEffects(buffer, properties.effects());
		MunitionPropertyComponents.writeEntityDamage(buffer, properties.damage());
		DualCannonLaunchProperties.write(buffer, properties.dualCannon());
		DualCannonImpactProperties.write(buffer, properties.dualImpact());
		MunitionPropertyComponents.writeIncendiary(buffer, properties.incendiary());
	}

	private static DualCannonIncendiaryProjectileProperties read(RegistryFriendlyByteBuf buffer) {
		return new DualCannonIncendiaryProjectileProperties(
			MunitionPropertyComponents.readBallistics(buffer),
			MunitionPropertyComponents.readRuntime(buffer),
			MunitionPropertyComponents.readImpact(buffer),
			MunitionPropertyComponents.readEffects(buffer),
			MunitionPropertyComponents.readEntityDamage(buffer),
			DualCannonLaunchProperties.read(buffer),
			DualCannonImpactProperties.read(buffer),
			MunitionPropertyComponents.readIncendiary(buffer));
	}

}
