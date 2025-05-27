package com.cainiao1053.cbcmoreshells;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import com.cainiao1053.cbcmoreshells.client.render.*;
import com.cainiao1053.cbcmoreshells.Cbcmoreshells;

@Mod.EventBusSubscriber(modid = Cbcmoreshells.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CbcmoreshellsClient {

//	@SubscribeEvent
//	public static void onClientSetup(FMLClientSetupEvent event) {
//		event.enqueueWork(() -> {
//			EntityType<?> cannon = ForgeRegistries.ENTITY_TYPES.getValue(
//					new ResourceLocation("createbigcannons", "ap_shot"));
//
//			if (cannon != null) {
//				RenderHandler.registerExtraLayer(
//						cannon,
//						new BillboardOverlay(
//								new ResourceLocation(Cbcmoreshells.MODID, "textures/block/projectile/apbc_shot.png"),
//								0.6f
//						)
//				);
//			}
//		});
//	}
}