package com.cainiao1053.cbcmoreshells;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class CBCMSClientEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){

        if (event.phase == TickEvent.Phase.START){
            CbcmoreshellsClient.CLIENT_LERPED_OUTLINER.tickOutlines();
        }

        if (event.phase == TickEvent.Phase.END) { // Only call code once as the tick event is called twice every tick
        }

    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            return;
        PoseStack ms = event.getPoseStack();
        ms.pushPose();
        SuperRenderTypeBuffer buffer = SuperRenderTypeBuffer.getInstance();
        float partialTicks = AnimationTickHolder.getPartialTicks();
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
                .getPosition();
        RenderSystem.disableDepthTest();

        CbcmoreshellsClient.CLIENT_LERPED_OUTLINER.renderOutlines(ms, buffer, camera, partialTicks);

        RenderSystem.enableDepthTest();
        buffer.draw();
        //RenderSystem.enableCull();
        RenderSystem.disableCull();
        ms.popPose();

    }

}
