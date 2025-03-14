package com.cainiao1053.cbcmoreshells.index;

import static rbasamoyai.createbigcannons.CreateBigCannons.REGISTRATE;

import com.cainiao1053.cbcmoreshells.CBCMSBlocks;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.TorpedoTubeBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.sliding_breech.TorpedoSlidingBreechBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.torpedo_end.TorpedoTubeEndBlockEntity;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechBlockEntityRenderer;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.quick_firing_breech.TorpQuickfiringBreechInstance;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.sliding_breech.TorpedoSlidingBreechBlockEntityRenderer;
import com.cainiao1053.cbcmoreshells.cannons.torpedo_tube.breeches.sliding_breech.TorpedoSlidingBreechInstance;

import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;


public class CBCMSBlockEntities {

	public static final BlockEntityEntry<TorpedoTubeBlockEntity> TORPEDO_TUBE_BODY = REGISTRATE
		.blockEntity("torpedo_tube_body", TorpedoTubeBlockEntity::new)
		.validBlocks(
//			CBCMSBlocks.CAST_IRON_TORPEDO_BARREL, CBCMSBlocks.CAST_IRON_TORPEDO_CHAMBER,
//			CBCMSBlocks.BRONZE_TORPEDO_BARREL, CBCMSBlocks.BRONZE_TORPEDO_CHAMBER,
			CBCMSBlocks.STEEL_TORPEDO_BARREL, CBCMSBlocks.STEEL_TORPEDO_CHAMBER)
		.register();

	public static final BlockEntityEntry<TorpedoTubeEndBlockEntity> TORPEDO_TUBE_END = REGISTRATE
			.blockEntity("torpedo_tube_end", TorpedoTubeEndBlockEntity::new)
			.validBlocks(
//					CBCMSBlocks.CAST_IRON_CANNON_END, CBCMSBlocks.BRONZE_CANNON_END
			)
			.register();

	public static final BlockEntityEntry<TorpQuickfiringBreechBlockEntity> TORPEDO_QUICKFIRING_BREECH = REGISTRATE
			.blockEntity("torpedo_quickfiring_breech", TorpQuickfiringBreechBlockEntity::new)
			.instance(() -> TorpQuickfiringBreechInstance::new)
			.renderer(() -> TorpQuickfiringBreechBlockEntityRenderer::new)
			.validBlocks(CBCMSBlocks.STEEL_TORPEDO_QUICKFIRING_BREECH)
			.register();

	public static final BlockEntityEntry<TorpedoSlidingBreechBlockEntity> TORPEDO_SLIDING_BREECH = REGISTRATE
			.blockEntity("torpedo_sliding_breech", TorpedoSlidingBreechBlockEntity::new)
			.instance(() -> TorpedoSlidingBreechInstance::new, false)
			.renderer(() -> TorpedoSlidingBreechBlockEntityRenderer::new)
			.validBlocks(CBCMSBlocks.STEEL_TORPEDO_SLIDING_BREECH)
			.register();












	public static void register() {
	}

}
