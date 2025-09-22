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

	public static void appendBallisticInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										 TooltipFlag flag, float durabilityMass, float penetration, float deflection) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", durabilityMass,penetration,String.format("%.0f",Math.acos(deflection)*180/Math.PI)), palette.primary(), palette.highlight(), 1));
	}

	public static void appendExplosiveInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										   TooltipFlag flag, float durabilityMass, float penetration, float deflection, float explosion) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", durabilityMass,penetration,String.format("%.0f",Math.acos(deflection)*180/Math.PI),explosion), palette.primary(), palette.highlight(), 1));
	}

	public static void appendIncendiaryInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										   TooltipFlag flag, float durabilityMass, float penetration, float deflection, float explosion, float fireChance, int fireRange) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", durabilityMass,penetration,String.format("%.0f",Math.acos(deflection)*180/Math.PI),explosion,String.format("%.0f",100*fireChance),fireRange), palette.primary(), palette.highlight(), 1));
	}

	public static void appendBombInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
											TooltipFlag flag, float durabilityMass, float penetration, float deflection, float explosion, float lifetime) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", durabilityMass,penetration,String.format("%.0f",Math.acos(deflection)*180/Math.PI),explosion,String.format("%.0f",lifetime/20)), palette.primary(), palette.highlight(), 1));
	}

	public static void appendRackedTorpedoInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
									  TooltipFlag flag, float explosion, float lifetime, float buoyancyFactor, float ssVel, float waterDamp) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main",explosion,String.format("%.0f",lifetime/20), buoyancyFactor, String.format("%.1f",ssVel*38.8), waterDamp), palette.primary(), palette.highlight(), 1));
	}

	public static void appendRackedRocketInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
									  TooltipFlag flag, float durabilityMass, float penetration, float deflection, float explosion, float lifetime, float ssVel, float thrustTime) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip.ballisticInfo";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", durabilityMass,penetration,String.format("%.0f",Math.acos(deflection)*180/Math.PI),explosion,String.format("%.0f",lifetime/20), String.format("%.0f",ssVel*20), String.format("%.0f",thrustTime/20)), palette.primary(), palette.highlight(), 1));
	}

	public static void appendTorpedoDetectorInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
											  TooltipFlag flag, float activationTime, float cooldown) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = stack.getDescriptionId() + ".tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main", String.format("%.0f",activationTime/20), String.format("%.0f",cooldown/20)), palette.primary(), palette.highlight(), 1));
	}

	public static void appendAmmoRackInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
												 TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "block.cbcmoreshells.ammo_rack.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
	}

	public static void appendDishPlateInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										  TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "block.cbcmoreshells.dish_plate.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
	}

	public static void appendTorpedoTubeInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										  TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "block.cbcmoreshells.torptube.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
	}

	public static void appendProxyFuzeInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										  TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "item.cbcmoreshells.ship_proximity_fuze.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
	}

	public static void appendLandingIndicatorInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
										  TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "block.cbcmoreshells.landing_indicator.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
	}

	public static void appendProjectileRackInfo(ItemStack stack, @Nullable Level level, List<Component> tooltip,
											 TooltipFlag flag) {
		if (!Screen.hasShiftDown()) {
			return;
		}
		TooltipHelper.Palette palette = getPalette(level, stack);
		String key1 = "block.cbcmoreshells.projrack.tooltip";
		tooltip.add(Components.translatable(key1).withStyle(ChatFormatting.GRAY));
		tooltip.addAll(TooltipHelper.cutStringTextComponent(I18n.get(key1 + ".main"), palette.primary(), palette.highlight(), 1));
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
