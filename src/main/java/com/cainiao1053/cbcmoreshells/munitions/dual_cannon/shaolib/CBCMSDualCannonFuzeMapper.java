package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.verr1.shaolib.munitions.fuze.BodyOnlyProximityFuzeItem;
import com.verr1.shaolib.munitions.fuze.FuzedMunitionState;
import com.verr1.shaolib.munitions.fuze.ImpactDelayFuzeItem;
import com.verr1.shaolib.munitions.fuze.MunitionFuzeItem;
import com.verr1.shaolib.munitions.fuze.TimedFuzeItem;
import com.verr1.shaolib.munitions.registry.MunitionsItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.index.CBCDataComponents;
import rbasamoyai.createbigcannons.munitions.fuzes.DelayedImpactFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.DelayedInertiaFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.ImpactFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.InertiaFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.ProximityFuzeItem;
import rbasamoyai.createbigcannons.munitions.fuzes.WiredFuzeItem;

/**
 * Translates a Create Big Cannons fuze into the Shaolib fuze that behaves closest to it.
 *
 * <h2>Why a translation and not an adapter</h2>
 * Shaolib decides whether a shell is fuzed with
 * {@code stack.getItem() instanceof MunitionFuzeItem}. CBC's {@code FuzeItem} does not implement
 * that interface, and cbcms registers no items of its own, so there is no way to hand a CBC fuze
 * straight to {@code FuzedMunitionState}. Instead, at launch, the fuze the player socketed into the
 * munition block is read and a matching stack of one of the three fuzes
 * {@code shaolib_munitions} already registers is built in its place.
 *
 * <p>The translated stack lives only inside the projectile's server state. It is never dropped,
 * never enters an inventory, and is not serialised into any item form the player can obtain, so no
 * new item is introduced.
 *
 * <h2>Fidelity</h2>
 * Shaolib ships three fuzes against CBC's seven, so some behaviour cannot survive the crossing:
 *
 * <ul>
 *   <li><b>Duds are lost.</b> CBC's impact and inertia fuzes carry a {@code FUZE_DAMAGE} durability
 *       and a per-hit detonate chance, so they sometimes fail. The Shaolib equivalents always fire.
 *       Translated shells are therefore strictly more reliable than they were.
 *   <li><b>Proximity changes what it looks for.</b> CBC's proximity fuze scans for <em>entities</em>;
 *       the only Shaolib equivalent is body-only, which scans for <em>physics bodies</em> (ships and
 *       similar structures). A translated proximity shell will burst near a hull but will fly past an
 *       aircraft or a mob. This matters most for the anti-air shells — see
 *       {@link #mapProximity} for the detail.
 *   <li><b>Wired fuzes lose their trigger.</b> A wired fuze is fired by redstone from the launch
 *       site, which has no meaning once the shell is airborne, so it degrades to a plain impact fuze.
 * </ul>
 */
public final class CBCMSDualCannonFuzeMapper {

	/** CBC's own fallback when {@code FUZE_TIMER} is absent. */
	private static final int DEFAULT_FUZE_TIMER_TICKS = 20;
	/** CBC's own fallback when {@code DETONATION_DISTANCE} is absent. */
	private static final int DEFAULT_DETONATION_DISTANCE = 1;
	/** Used if the CBC arming-time config cannot be read (e.g. called before config load). */
	private static final int FALLBACK_ARMING_TICKS = 5;

	private CBCMSDualCannonFuzeMapper() {}

	/**
	 * A Shaolib fuze stack plus any state it should start in.
	 *
	 * @param initiallyTriggered whether the fuze should already count as tripped at spawn
	 * @param countdownTicks     ticks remaining when {@code initiallyTriggered}; ignored otherwise
	 */
	public record MappedFuze(ItemStack stack, boolean initiallyTriggered, int countdownTicks) {

		public static final MappedFuze EMPTY = new MappedFuze(ItemStack.EMPTY, false, -1);

		public MappedFuze {
			stack = stack == null ? ItemStack.EMPTY : stack.copy();
		}

		public boolean isEmpty() {
			return this.stack.isEmpty();
		}

		/** Applies the pre-trigger, if any. Call after {@code installFuze}, which resets this state. */
		public void applyInitialState(FuzedMunitionState state) {
			if (!this.initiallyTriggered) return;
			state.setFuzeTriggered(true);
			state.setFuzeCountdownTicks(Math.max(0, this.countdownTicks));
		}

	}

	/**
	 * Installs the Shaolib equivalent of {@code cbcFuze} onto a projectile's state.
	 *
	 * <p>Must run during the spawn initializer's <em>handle</em> phase. That phase executes before
	 * {@code ProjectileType.onSpawn}, which is where {@code MunitionFuzes.onInstalled} runs — so a
	 * fuze installed here is seen and initialised by its own item. Installing later would skip that.
	 */
	public static void install(FuzedMunitionState state, ItemStack cbcFuze) {
		MappedFuze mapped = map(cbcFuze);
		if (mapped.isEmpty()) return;
		state.installFuze(mapped.stack());
		mapped.applyInitialState(state);
	}

	/**
	 * @param source the fuze read out of the munition block's {@code createbigcannons:fuze} component
	 * @return the closest Shaolib fuze, or {@link MappedFuze#EMPTY} for no fuze. An empty result is
	 *         not a failure: Shaolib treats an unfuzed shell as inert and lets it embed on impact,
	 *         which is the correct outcome for solid shot.
	 */
	public static MappedFuze map(ItemStack source) {
		if (source == null || source.isEmpty()) return MappedFuze.EMPTY;

		// Already a Shaolib fuze — another mod, or a future cbcms path, put it there. Pass it through
		// untouched rather than trying to interpret it.
		if (source.getItem() instanceof MunitionFuzeItem) {
			return new MappedFuze(source, false, -1);
		}

		Item item = source.getItem();

		if (item instanceof rbasamoyai.createbigcannons.munitions.fuzes.TimedFuzeItem) {
			return timed(fuzeTimer(source));
		}
		if (item instanceof DelayedImpactFuzeItem || item instanceof DelayedInertiaFuzeItem) {
			return impactDelay(fuzeTimer(source));
		}
		if (item instanceof ImpactFuzeItem || item instanceof InertiaFuzeItem) {
			// Zero delay: burst on contact. CBC's dud chance has no Shaolib equivalent and is dropped.
			return impactDelay(0);
		}
		if (item instanceof ProximityFuzeItem) {
			return mapProximity(source);
		}
		if (item instanceof WiredFuzeItem) {
			// Redstone-triggered at the launch site, which cannot reach the shell in flight. Falling
			// back to an impact fuze keeps the shell useful instead of making it inert.
			return impactDelay(0);
		}

		if (item instanceof FuzeItem) {
			Cbcmoreshells.LOGGER.debug("No dual cannon fuze mapping for {}; treating the shell as unfuzed",
				item);
		}
		return MappedFuze.EMPTY;
	}

	/**
	 * CBC proximity fuze to Shaolib body-only proximity fuze.
	 *
	 * <p><b>Behavioural gap.</b> CBC bursts near any entity it could hit; this one bursts only near a
	 * physics body. Anti-air shells relying on a proximity fuze will no longer burst near aircraft
	 * unless those aircraft are themselves physics bodies. Closing the gap properly needs an
	 * entity-proximity fuze on the Shaolib side; there is none today, and cbcms cannot add one
	 * without registering an item.
	 *
	 * <p>Arming is carried across faithfully: CBC arms after
	 * {@code proximityFuzeArmingTime} ticks in the air, which maps directly onto Shaolib's
	 * {@code armingTicks}. Arming distance is left at zero so the tick-based arming is the only gate,
	 * matching CBC.
	 */
	private static MappedFuze mapProximity(ItemStack source) {
		ItemStack mapped = new ItemStack(MunitionsItems.BODY_ONLY_PROXIMITY_FUZE.get());
		double radius = Math.max(1.0D, source.getOrDefault(CBCDataComponents.DETONATION_DISTANCE,
			DEFAULT_DETONATION_DISTANCE));
		BodyOnlyProximityFuzeItem.setRadius(mapped, radius);
		BodyOnlyProximityFuzeItem.setArmingTicks(mapped, proximityArmingTicks());
		BodyOnlyProximityFuzeItem.setArmingDistance(mapped, 0.0D);
		return new MappedFuze(mapped, false, -1);
	}

	private static MappedFuze impactDelay(int delayTicks) {
		ItemStack mapped = new ItemStack(MunitionsItems.IMPACT_DELAY_FUZE.get());
		ImpactDelayFuzeItem.setDelayTicks(mapped, Math.max(0, delayTicks));
		return new MappedFuze(mapped, false, -1);
	}

	private static MappedFuze timed(int timeTicks) {
		ItemStack mapped = new ItemStack(MunitionsItems.TIMED_FUZE.get());
		TimedFuzeItem.setTimeTicks(mapped, Math.max(0, timeTicks));
		return new MappedFuze(mapped, false, -1);
	}

	private static int fuzeTimer(ItemStack source) {
		return source.getOrDefault(CBCDataComponents.FUZE_TIMER, DEFAULT_FUZE_TIMER_TICKS);
	}

	private static int proximityArmingTicks() {
		try {
			return Math.max(0, CBCConfigs.server().munitions.proximityFuzeArmingTime.get());
		} catch (RuntimeException exception) {
			// Config not loaded yet, or CBC changed the field: an approximate arming delay is far
			// better than failing to build the shell.
			return FALLBACK_ARMING_TICKS;
		}
	}

}
