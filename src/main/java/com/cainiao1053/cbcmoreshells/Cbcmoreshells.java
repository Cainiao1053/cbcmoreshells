package com.cainiao1053.cbcmoreshells;

import com.cainiao1053.cbcmoreshells.cannon_control.cannon_types.CBCMSCannonContraptionTypes;
import com.cainiao1053.cbcmoreshells.datagen.assets.CBCMSLangGen;
import com.cainiao1053.cbcmoreshells.index.*;
import com.cainiao1053.cbcmoreshells.network.CBCMSNetwork;
import com.cainiao1053.cbcmoreshells.network.CBCMSRootNetwork;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import rbasamoyai.createbigcannons.utils.CBCUtils;

@Mod(Cbcmoreshells.MODID)
public class Cbcmoreshells {

    public static final String MODID = "cbcmoreshells";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    public static final Logger LOGGER = LogUtils.getLogger();


    public Cbcmoreshells() {

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        modEventBus.addListener(this::addCreative);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Cbcmoreshells::init);

        ModGroup.register(modEventBus);

        CBCMSBlocks.register();
        CBCMSEntityTypes.register();
        CBCMSBlockEntities.register();
        CBCMSBlockPartials.init();
        CBCMSItems.register();
        CBCMSCannonContraptionTypes.register();
        //CBCMSRecipeTypes.register();

        //CBCMSArmInteractionPointTypes.register();

        CBCMSContraptionTypes.prepare();
        //CBCMSLangGen.prepare();
        CBCMSSoundEvents.prepare();
        CBCMSRootNetwork.init();
        CBCMSArmInteractionPointTypes.register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> CbcmoreshellsClient::clientInit);



        // 注册事件
        MinecraftForge.EVENT_BUS.register(this);


    }

    public static void init(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            // TODO: custom registration should all happen in one place
            // Most registration happens in the constructor.
            // These registrations use Create's registered objects directly so they must run after registration has finished.
            // --


        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        CBCMSNetwork.init();

    }

    private void clientSetup(FMLClientSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    public static ResourceLocation resource(String path) {
        return CBCUtils.location(MODID, path);
    }

}