package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterialProperties;
import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.DualCannonModifiers;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyComponents;

import java.util.Objects;

/**
 * A shell loaded into a particular barrel material. Everything here is what
 * {@code MountedDualCannonContraption.spawnRound} would produce for that pairing, so a firing table
 * row and a real shot agree.
 *
 * @param commandModifier   combat command bonus, 1.0 for the plain table
 * @param equipmentModifier equipment bonus, 1.0 for the plain table
 */
public record DualCannonLoadout(DualCannonShellContext shell, DualCannonMaterial material,
								DualCannonMaterialProperties barrel,
								float commandModifier, float equipmentModifier) {

	public DualCannonLoadout {
		Objects.requireNonNull(shell, "shell");
		Objects.requireNonNull(material, "material");
		Objects.requireNonNull(barrel, "barrel");
	}

	/** The pairing with no command or equipment bonus, which is what the table shows. */
	public static DualCannonLoadout of(DualCannonShellContext shell, DualCannonMaterial material) {
		return new DualCannonLoadout(shell, material, material.properties(), 1.0F, 1.0F);
	}

	/** What {@code DualCannonState.durabilityModifier} ends up as for this pairing. */
	public double durabilityModifier() {
		return (double) this.barrel.durabilityMassModifier() * this.commandModifier * this.equipmentModifier;
	}

	/** Durability mass the shell actually flies with; drives momentum and how deep it digs. */
	public double effectiveMass() {
		return this.shell.baseMass() * this.durabilityModifier();
	}

	/**
	 * Final lifetime: the shell's own base plus the barrel's bonus. This is the binding constraint
	 * on range — every dual cannon round has a 5000 block travel budget it never gets near, but a
	 * short-lived barrel expires the shell well before its ballistic maximum.
	 */
	public int lifetimeTicks() {
		return this.shell.entry().launchProfile().resolveLifetimeTicks(this.shell.baseLifetimeTicks(),
			this.barrel.addedLifetime(), this.commandModifier, this.equipmentModifier);
	}

	/** Burst power after the barrel's modifier. Zero for solid shot. */
	public double explosionPower() {
		return this.shell.baseExplosionPower()
			* DualCannonModifiers.explosionPower(this.shell.kind(), this.durabilityModifier());
	}

	/** Chance to start a fire, or NaN when this shell carries no incendiary payload. */
	public double fireChance() {
		MunitionPropertyComponents.IncendiaryProperties incendiary = this.shell.incendiary();
		return incendiary == null
			? Double.NaN
			: incendiary.fireChance() * DualCannonModifiers.incendiaryFire(this.durabilityModifier());
	}

	/** Fire spread radius, or NaN when this shell carries no incendiary payload. */
	public double fireRange() {
		MunitionPropertyComponents.IncendiaryProperties incendiary = this.shell.incendiary();
		return incendiary == null
			? Double.NaN
			: incendiary.fireRange() * DualCannonModifiers.incendiaryRange(this.durabilityModifier());
	}

	/** Reload time multiplier for this pairing; lower is faster. */
	public double reloadCoefficient() {
		return this.shell.shell().dualCannon().reloadTimeCoefficient() * this.barrel.reloadTimeModifier() * 2.5;
	}

	public double recoil(){
		return this.shell.shell().dualCannon().baseRecoil() * this.barrel.recoilMultiplier();
	}

}
