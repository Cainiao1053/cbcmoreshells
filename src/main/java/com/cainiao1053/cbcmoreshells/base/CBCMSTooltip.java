package com.cainiao1053.cbcmoreshells.base;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CBCMSTooltip {

	private static Style primary = TooltipHelper.Palette.GRAY_AND_WHITE.primary();
	private static Style highlight = TooltipHelper.Palette.GRAY_AND_WHITE.highlight();

	public static void addHoldShift(boolean desc, List<Component> tooltip) {
		String[] holdDesc = Lang.translateDirect("tooltip.holdForDescription", "$").getString().split("\\$");

		Component keyShift = Lang.translateDirect("tooltip.keyShift");
		MutableComponent tabBuilder = Components.literal("");
		tabBuilder.append(Components.literal(holdDesc[0]).withStyle(ChatFormatting.DARK_GRAY));
		tabBuilder.append(keyShift.plainCopy().withStyle(ChatFormatting.GRAY));
		tabBuilder.append(Components.literal(holdDesc[1]).withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(tabBuilder);

	}

	public static void appendTorpedoInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
											 TooltipFlag flag, float torpSpeed, float buoyancyFactor, float lifetime) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.torpInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", String.format("%.1f",torpSpeed*38.8),buoyancyFactor,String.format("%.1f",lifetime*torpSpeed)), palette.primary(), palette.highlight(), 1));
	}

	private static Component getNoGogglesMeter(int outOfFive, boolean invertColor, boolean canBeInvalid) {
		int value = invertColor ? 5 - outOfFive : outOfFive;
		ChatFormatting color = switch (value) {
			case 0, 1 -> ChatFormatting.RED;
			case 2, 3 -> ChatFormatting.GOLD;
			case 4, 5 -> ChatFormatting.YELLOW;
			default -> canBeInvalid ? ChatFormatting.DARK_GRAY : value < 0 ? ChatFormatting.RED : ChatFormatting.YELLOW;
		};
		return Components.literal(" " + TooltipHelper.makeProgressBar(5, outOfFive)).withStyle(color);
	}

	public static TooltipHelper.Palette getPalette(Level level, ItemStack stack) {
		return TooltipHelper.Palette.STANDARD_CREATE;
	}

}
