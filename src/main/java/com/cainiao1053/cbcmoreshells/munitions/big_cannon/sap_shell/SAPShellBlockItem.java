package com.cainiao1053.cbcmoreshells.munitions.big_cannon.sap_shell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.cainiao1053.cbcmoreshells.Cbcmoreshells;
import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.ShellessInertBigCannonProperties;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;
import rbasamoyai.createbigcannons.munitions.FuzedProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlockItem;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonCommonShellProperties;
import rbasamoyai.createbigcannons.munitions.fuzes.FuzeItem;
import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;

import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.APBC_SHOT;
import static com.cainiao1053.cbcmoreshells.CBCMSEntityTypes.SAP_SHELL;
import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;
import static rbasamoyai.createbigcannons.base.CBCTooltip.getPalette;

public class SAPShellBlockItem extends FuzedProjectileBlockItem {

	public SAPShellBlockItem(Block block, Properties properties) {
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

		BigCannonCommonShellProperties properties = CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE.getPropertiesOf(SAP_SHELL.get());
		CBCMSTooltip.appendExplosiveInfo(stack, level, tooltip, flag, properties.ballistics().durabilityMass(), properties.ballistics().penetration(), properties.ballistics().deflection(),properties.explosion().explosivePower());

	}

}
