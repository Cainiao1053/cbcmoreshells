package com.cainiao1053.cbcmoreshells.cannons.dual_cannon;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
import com.cainiao1053.cbcmoreshells.cannons.dual_cannon.material.DualCannonMaterial;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;

public class DualCannonBlockItem<T extends Block & DualCannonBlock> extends BlockItem {

	private final T cannonBlock;

	public DualCannonBlockItem(T block, Properties properties) {
		super(block, properties);
		this.cannonBlock = block;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
//		boolean desc = Screen.hasShiftDown();
//		if (!desc) {
//			addHoldShift(desc, tooltip);
//			return;
//		}
		CBCMSTooltip.appendDualCannonBlockText(stack, level, tooltip, flag, cannonBlock);
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult result = super.place(context);
		Player player = context.getPlayer();
		DualCannonMaterial material = this.cannonBlock.getCannonMaterial();
		if (player != null && (material.properties().connectsInSurvival() || player.isCreative()))
			DualCannonBlock.onPlace(context.getLevel(), context.getClickedPos());
		return result;
	}

}
