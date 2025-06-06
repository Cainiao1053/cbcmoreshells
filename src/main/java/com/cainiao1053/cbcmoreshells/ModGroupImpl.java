package com.cainiao1053.cbcmoreshells;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.simibubi.create.Create;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModGroupImpl {

	private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cbcmoreshells.MODID);
	private static Map<ResourceKey<CreativeModeTab>, RegistryObject<CreativeModeTab>> TABS = new HashMap<>();

	public static Supplier<CreativeModeTab> wrapGroup(String id, Supplier<CreativeModeTab> sup) {
		RegistryObject<CreativeModeTab> obj = TAB_REGISTER.register(id, sup);
		TABS.put(ModGroup.makeKey(id), obj);
		return obj;
	}

	public static CreativeModeTab.Builder createBuilder() {
		return CreativeModeTab.builder().withTabsBefore(Create.asResource("palettes"));
	}

	public static void registerForge(IEventBus modBus) {
		TAB_REGISTER.register(modBus);
	}

	public static void useModTab(ResourceKey<CreativeModeTab> key) {
		Cbcmoreshells.REGISTRATE.setCreativeTab(TABS.get(key));
	}

}
