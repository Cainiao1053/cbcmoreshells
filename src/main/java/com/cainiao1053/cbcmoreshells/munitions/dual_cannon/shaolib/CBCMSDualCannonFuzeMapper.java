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


public final class CBCMSDualCannonFuzeMapper {
	private static final int DEFAULT_FUZE_TIMER_TICKS = 20;
	private static final int DEFAULT_DETONATION_DISTANCE = 1;
	private static final int FALLBACK_ARMING_TICKS = 5;

	private CBCMSDualCannonFuzeMapper() {}

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

	public static void install(FuzedMunitionState state, ItemStack cbcFuze) {
		MappedFuze mapped = map(cbcFuze);
		if (mapped.isEmpty()) return;
		state.installFuze(mapped.stack());
		mapped.applyInitialState(state);
	}
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
