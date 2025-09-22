package com.cainiao1053.cbcmoreshells.cannons.projectile_rack;

import com.cainiao1053.cbcmoreshells.base.CBCMSTooltip;
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
import rbasamoyai.createbigcannons.base.CBCTooltip;
//import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import com.cainiao1053.cbcmoreshells.cannons.projectile_rack.material.ProjectileRackMaterial;

import javax.annotation.Nullable;
import java.util.List;

import static com.cainiao1053.cbcmoreshells.base.CBCMSTooltip.addHoldShift;

public class ProjectileRackBlockItem<T extends Block & ProjectileRackBlock> extends BlockItem {

	private final T cannonBlock;

	public ProjectileRackBlockItem(T block, Properties properties) {
		super(block, properties);
		this.cannonBlock = block;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		boolean desc = Screen.hasShiftDown();
		if (!desc) {
			addHoldShift(desc, tooltip);
			return;
		}
		CBCMSTooltip.appendProjectileRackInfo(stack,level,tooltip,flag);
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult result = super.place(context);
		Player player = context.getPlayer();
		ProjectileRackMaterial material = this.cannonBlock.getCannonMaterial();
		if (player != null && (material.properties().connectsInSurvival() || player.isCreative()))
			ProjectileRackBlock.onPlace(context.getLevel(), context.getClickedPos());
		return result;
	}

}
