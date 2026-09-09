package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Which barrel materials a firing table lists.
 *
 * <p>Most alloys ship as a single-barrel and a dual-barrel material, and the two are the same
 * barrel for everything a firing table shows. Listing both doubles the rows for no information, so
 * the default keeps only the single-barrel ones.
 */
public enum DualCannonMaterialFilter {

	/** Single-barrel materials only. Alloys with no single-barrel variant yet are dropped. */
	SINGLE_ONLY,

	/**
	 * Single-barrel materials, plus every dual material whose single-barrel twin does not exist yet
	 * — so an alloy never vanishes from the table just because nobody has added its single barrel.
	 * Those extras are exactly {@link #missingSingleVariants()} and the UI should mark them. The
	 * list shrinks to nothing on its own as the single barrels get added.
	 */
	SINGLE_PLUS_GAPS,

	/** Everything, both barrel counts. Mostly for debugging. */
	ALL;

	private static final String SINGLE = "single_";
	/** Bore prefixes that sit in front of {@code single_}: {@code wide_steel -> wide_single_steel}. */
	private static final String[] BORE_PREFIXES = {"wide_", "large_"};

	/** Materials to show, lightest durability mass modifier first. */
	public List<DualCannonMaterial> select() {
		List<DualCannonMaterial> selected = new ArrayList<>();
		for (DualCannonMaterial material : DualCannonMaterial.all()) {
			if (this == ALL || material.properties().isSingleBarrel()) selected.add(material);
		}
		if (this == SINGLE_PLUS_GAPS) selected.addAll(missingSingleVariants());
		selected.sort(Comparator.comparingDouble(m -> m.properties().durabilityMassModifier()));
		return selected;
	}

	/**
	 * Dual materials whose single-barrel twin is not registered. Doubles as a to-do list: every
	 * entry is an alloy still waiting for its single barrel.
	 *
	 * <p>Pairing goes by name rather than by stats. Single and dual variants of one alloy differ
	 * slightly in reload time and minimum spread, so any numeric signature loose enough to pair
	 * them also merges genuinely different alloys — {@code podia_zinc} and {@code brass} carry the
	 * same mass modifier and lifetime but reload at 0.80 against 0.62.
	 */
	public static List<DualCannonMaterial> missingSingleVariants() {
		Set<String> singlePaths = new HashSet<>();
		for (DualCannonMaterial material : DualCannonMaterial.all()) {
			if (material.properties().isSingleBarrel()) singlePaths.add(material.name().getPath());
		}

		List<DualCannonMaterial> missing = new ArrayList<>();
		for (DualCannonMaterial material : DualCannonMaterial.all()) {
			if (material.properties().isSingleBarrel()) continue;
			if (!singlePaths.contains(singleVariantPath(material.name().getPath()))) missing.add(material);
		}
		return missing;
	}

	/** {@code steel -> single_steel}, {@code wide_steel -> wide_single_steel}. */
	private static String singleVariantPath(String dualPath) {
		for (String prefix : BORE_PREFIXES) {
			if (dualPath.startsWith(prefix)) return prefix + SINGLE + dualPath.substring(prefix.length());
		}
		return SINGLE + dualPath;
	}

	/** Translation key for a material's display name, matching the existing cannon block keys. */
	public static String translationKey(DualCannonMaterial material) {
		return "block." + material.name().getNamespace() + ".material." + material.name().getPath();
	}

}
