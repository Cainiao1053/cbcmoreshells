package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.CBCMSBlocks;
import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.api.projectile.ProjectileType;
import com.verr1.shaolib.munitions.config.MunitionConfigManager;
import com.verr1.shaolib.munitions.config.properties.MunitionPropertyType;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Maps a loaded munition block to everything needed to fire it.
 *
 * <p>This is the piece the old code was missing. Because there was no lookup table, callers that
 * only wanted a number — {@code DualCannonMountPoint} reading a reload coefficient, for instance —
 * had to call {@code projectileBlock.getProjectile(level, stack)} and construct a throwaway entity
 * just to ask it. Every such site can now go through {@link #of} instead.
 *
 * <p>Built lazily. Registrate hands out {@code BlockEntry} holders during mod construction but does
 * not populate them until the registry events fire, so resolving blocks eagerly in
 * {@code Cbcmoreshells.init()} would throw. The table is instead assembled on first use, by which
 * point registration has certainly completed.
 */
public final class CBCMSDualCannonMunitionRegistry {

	private static volatile Map<Block, Entry> byBlock;

	private CBCMSDualCannonMunitionRegistry() {}

	/**
	 * Everything about one munition that is fixed at registration time.
	 *
	 * @param projectileType  what to spawn
	 * @param propertyType    which datapack property set governs it
	 * @param kind            impact/detonation class, see {@link DualCannonBehavior.Kind}
	 * @param launchProfile   the launch facts the property set cannot express
	 * @param fuzed           whether the block accepts a fuze at all; inert shot does not
	 * @param baseFuze        whether the shell is base-fuzed. Informational for now: the fuze socket
	 *                        face still comes from {@code FuzedDualCannonProjectileBlock.isBaseFuze()}
	 *                        on the block, and the "only fuze after penetrating" behaviour comes from
	 *                        {@link DualCannonBehavior.Kind#isApheLike()}. Recorded here so a future
	 *                        munition that needs base-fuze semantics outside APHE/APBC has somewhere
	 *                        to declare it. Every shipped dual cannon munition is {@code false}
	 * @param renderedBlock   block whose model represents this shell in flight; the extended
	 *                        variants borrow their normal sibling's model
	 */
	public record Entry(
		ProjectileType<DualCannonState> projectileType,
		MunitionPropertyType<? extends DualCannonMunitionProperties> propertyType,
		DualCannonBehavior.Kind kind,
		CBCMSDualCannonLaunchProfile launchProfile,
		boolean fuzed,
		boolean baseFuze,
		ResourceLocation renderedBlock
	) {}

	@Nullable
	public static Entry of(Block block) {
		return block == null ? null : map().get(block);
	}

	@Nullable
	public static Entry of(BlockState state) {
		return state == null ? null : of(state.getBlock());
	}

	@Nullable
	public static Entry of(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return null;
		return stack.getItem() instanceof BlockItem blockItem ? of(blockItem.getBlock()) : null;
	}

	public static Collection<Entry> all() {
		return Collections.unmodifiableCollection(map().values());
	}

	/** Current datapack-resolved properties for this munition, or its baked-in fallback. */
	public static DualCannonMunitionProperties properties(Entry entry) {
		return resolve(entry.propertyType());
	}

	/** Convenience for the many call sites that only want the launch block. */
	public static DualCannonLaunchProperties launchProperties(Entry entry) {
		return properties(entry).dualCannon();
	}

	/**
	 * Reload cost multiplier for a munition item, defaulting to 1 for anything that is not a dual
	 * cannon munition. Replaces the old
	 * {@code ((DualCannonProjectileBlock<?>) munition).getProjectile(level, stack).getReloadTimeCoef()}.
	 */
	public static float reloadTimeCoefficient(ItemStack stack) {
		Entry entry = of(stack);
		return entry == null ? 1.0F : (float) launchProperties(entry).reloadTimeCoefficient();
	}

	/** Capture helper: turns the wildcard property type back into something resolvable. */
	private static <P extends DualCannonMunitionProperties> P resolve(MunitionPropertyType<P> type) {
		return MunitionConfigManager.properties(type);
	}

	private static Map<Block, Entry> map() {
		Map<Block, Entry> local = byBlock;
		if (local == null) {
			synchronized (CBCMSDualCannonMunitionRegistry.class) {
				local = byBlock;
				if (local == null) {
					local = build();
					byBlock = local;
				}
			}
		}
		return local;
	}

	private static Map<Block, Entry> build() {
		Map<Block, Entry> entries = new LinkedHashMap<>();

		// Inert shot: no burst charge, so no fuze socket either.
		put(entries, CBCMSBlocks.NORMAL_AP_SHOT.get(), CBCMSDualCannonProjectiles.NORMAL_AP_SHOT,
			CBCMSDualCannonPropertyTypes.NORMAL_AP_SHOT, DualCannonBehavior.Kind.AP_SHOT, 40, false, false,
			"normal_ap_shot");
		put(entries, CBCMSBlocks.EXTENDED_AP_SHOT.get(), CBCMSDualCannonProjectiles.EXTENDED_AP_SHOT,
			CBCMSDualCannonPropertyTypes.EXTENDED_AP_SHOT, DualCannonBehavior.Kind.AP_SHOT, 20, false, false,
			"normal_ap_shot");

		// Fuzed shells. None of the dual cannon munitions are base-fuzed in the shipped data; APHE
		// and APBC still wait for penetration before consulting their fuze, but that comes from
		// Kind.isApheLike() rather than this flag.
		put(entries, CBCMSBlocks.NORMAL_HE_SHELL.get(), CBCMSDualCannonProjectiles.NORMAL_HE_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_HE_SHELL, DualCannonBehavior.Kind.HE, 20, true, false,
			"normal_he_shell");
		put(entries, CBCMSBlocks.NORMAL_ANTIAIR_HE_SHELL.get(), CBCMSDualCannonProjectiles.NORMAL_ANTIAIR_HE_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_ANTIAIR_HE_SHELL, DualCannonBehavior.Kind.ANTIAIR_HE, 0, true, false,
			"normal_antiair_he_shell");
		put(entries, CBCMSBlocks.EXTENDED_ANTIAIR_HE_SHELL.get(), CBCMSDualCannonProjectiles.EXTENDED_ANTIAIR_HE_SHELL,
			CBCMSDualCannonPropertyTypes.EXTENDED_ANTIAIR_HE_SHELL, DualCannonBehavior.Kind.ANTIAIR_HE, 0, true, false,
			"normal_antiair_he_shell");
		put(entries, CBCMSBlocks.NORMAL_AP_SHELL.get(), CBCMSDualCannonProjectiles.NORMAL_AP_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_AP_SHELL, DualCannonBehavior.Kind.APHE, 30, true, false,
			"normal_ap_shell");
		put(entries, CBCMSBlocks.NORMAL_APBC_SHELL.get(), CBCMSDualCannonProjectiles.NORMAL_APBC_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_APBC_SHELL, DualCannonBehavior.Kind.APBC, 30, true, false,
			"normal_apbc_shell");
		put(entries, CBCMSBlocks.NORMAL_SAP_SHELL.get(), CBCMSDualCannonProjectiles.NORMAL_SAP_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_SAP_SHELL, DualCannonBehavior.Kind.SAP, 30, true, false,
			"normal_sap_shell");
		put(entries, CBCMSBlocks.NORMAL_INCENDIARY_HE_SHELL.get(),
			CBCMSDualCannonProjectiles.NORMAL_INCENDIARY_HE_SHELL,
			CBCMSDualCannonPropertyTypes.NORMAL_INCENDIARY_HE_SHELL, DualCannonBehavior.Kind.INCENDIARY, 20, true,
			false, "normal_incendiary_he_shell");

		return Map.copyOf(entries);
	}

	private static void put(Map<Block, Entry> entries, Block block, ProjectileType<DualCannonState> projectileType,
							MunitionPropertyType<? extends DualCannonMunitionProperties> propertyType,
							DualCannonBehavior.Kind kind, int baseLifetimeTicks, boolean fuzed, boolean baseFuze,
							String renderedBlockPath) {
		entries.put(block, new Entry(projectileType, propertyType, kind,
			new CBCMSDualCannonLaunchProfile(baseLifetimeTicks), fuzed, baseFuze,
			Cbcmoreshells.resource(renderedBlockPath)));
	}

}
