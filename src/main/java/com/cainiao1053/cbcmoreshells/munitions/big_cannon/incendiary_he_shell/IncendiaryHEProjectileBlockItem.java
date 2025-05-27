package com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ShellessFuzedProjectileBlockItem;

import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonIncendiaryShellProperties;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonShellessShellProperties;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.ShellessInertBigCannonProperties;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;

import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.*;
import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;


public class IncendiaryHEProjectileBlockItem extends FuzedProjectileBlockItem {

	public IncendiaryHEProjectileBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		boolean desc = Screen.hasShiftDown();
		if (!desc) {
			addHoldShift(desc, tooltip);
			return;
		}

		BigCannonIncendiaryShellProperties properties = CBCMSMunitionPropertiesHandlers.INCENDIARY_SHELL_PROJECTILE.getPropertiesOf(INCENDIARY_HE_SHELL.get());
		CBCMSTooltip.appendIncendiaryInfo(stack, level, tooltip, flag, properties.ballistics().durabilityMass(), properties.ballistics().penetration(), properties.ballistics().deflection(),properties.explosion().explosivePower(), properties.fireChance(), properties.fireRange());

	}

}
