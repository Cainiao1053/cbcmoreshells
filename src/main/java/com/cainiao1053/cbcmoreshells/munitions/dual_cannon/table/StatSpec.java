package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.table;

import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * One column or one line of a firing table: where to get the number, how to print it, and how
 * willing the table is to drop it when it runs out of width.
 *
 * <p>The value function takes the whole context rather than named arguments, so adding an input
 * later means adding a field to {@link DualCannonShellContext} or {@link DualCannonLoadout} and
 * nothing else has to change. Returning a non-finite value means "not applicable to this shell",
 * and the table shows {@link StatFormat#NOT_APPLICABLE} or skips the row entirely.
 *
 * @param <C> the context this stat reads. Shell-only stats use {@link DualCannonShellContext} so
 *            they cannot accidentally depend on the barrel; per-material stats use
 *            {@link DualCannonLoadout}.
 */
public record StatSpec<C>(String key, ToDoubleFunction<C> value, StatFormat format, int priority) {

	/** Columns the table must never drop. */
	public static final int PRIORITY_REQUIRED = 100;
	public static final int PRIORITY_HIGH = 90;
	public static final int PRIORITY_NORMAL = 80;
	public static final int PRIORITY_LOW = 60;

	public StatSpec {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		Objects.requireNonNull(format, "format");
	}

	public static <C> StatSpec<C> of(String key, ToDoubleFunction<C> value, StatFormat format) {
		return new StatSpec<>(key, value, format, PRIORITY_NORMAL);
	}

	public static <C> StatSpec<C> of(String key, ToDoubleFunction<C> value, StatFormat format, int priority) {
		return new StatSpec<>(key, value, format, priority);
	}

	public double valueOf(C context) {
		return this.value.applyAsDouble(context);
	}

	public String formatted(C context) {
		return this.format.format(this.valueOf(context));
	}

	/** True when this stat has nothing to say about the given context. */
	public boolean applies(C context) {
		return Double.isFinite(this.valueOf(context));
	}

}
