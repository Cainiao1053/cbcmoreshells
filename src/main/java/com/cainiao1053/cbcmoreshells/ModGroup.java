package com.cainiao1053.cbcmoreshells;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.simibubi.create.Create;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModGroup {

	public static final ResourceKey<CreativeModeTab> MAIN_TAB_KEY = makeKey("shells");

	private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cbcmoreshells.MODID);
	private static Map<ResourceKey<CreativeModeTab>, RegistryObject<CreativeModeTab>> TABS = new HashMap<>();

	public static final Supplier<CreativeModeTab> GROUP = wrapGroup("shells", () -> createBuilder()
		.title(Component.translatable("itemGroup." + Cbcmoreshells.MODID))
		.icon(CBCMSBlocks.SHELLESS_HE_SHELL::asStack)
		.displayItems((param, output) -> {
			output.acceptAll(Arrays.asList(
				CBCMSBlocks.Inferior_HE_SHELL.asStack(),
				CBCMSBlocks.SAP_SHELL.asStack(),
				CBCMSBlocks.HESH_SHELL.asStack(),
				CBCMSBlocks.SHELLESS_HE_SHELL.asStack(),
				CBCMSBlocks.APHE_CANNON_ROCKET.asStack(),
				CBCMSBlocks.SHELLESS_AP_SHOT.asStack(),
				CBCMSBlocks.SHELLESS_SAP_SHELL.asStack(),
				CBCMSBlocks.HE_CANNON_ROCKET.asStack(),
				CBCMSBlocks.MEDIUM_RANGE_TORPEDO.asStack(),
				CBCMSBlocks.MEDIUM_RANGE_DEEPWATER_TORPEDO.asStack(),
				CBCMSBlocks.HIGHSPEED_TORPEDO.asStack(),
				CBCMSBlocks.AIRDROPPED_TORPEDO.asStack(),
				CBCMSBlocks.LONG_RANGE_TORPEDO.asStack(),
				CBCMSBlocks.APFSDS_SHOT.asStack(),
				CBCMSBlocks.ANTIAIR_HE_SHELL.asStack(),





				CBCMSBlocks.STEEL_TORPEDO_QUICKFIRING_BREECH.asStack(),
				CBCMSBlocks.STEEL_TORPEDO_CHAMBER.asStack(),
				CBCMSBlocks.STEEL_TORPEDO_BARREL.asStack(),

				CBCMSBlocks.ANTIBLAST_COPYCAT_PANEL.asStack()
			));
		})
		.build());

	public static Supplier<CreativeModeTab> wrapGroup(String id, Supplier<CreativeModeTab> sup) {
		RegistryObject<CreativeModeTab> obj = TAB_REGISTER.register(id, sup);
		TABS.put(ModGroup.makeKey(id), obj);
		return obj;
	}
	public static CreativeModeTab.Builder createBuilder() {
		return CreativeModeTab.builder().withTabsBefore(Create.asResource("palettes"));
	}

	public static void useModTab(ResourceKey<CreativeModeTab> key) {
		Cbcmoreshells.REGISTRATE.setCreativeTab(TABS.get(key));
	}

	public static ResourceKey<CreativeModeTab> makeKey(String id) {
		return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Cbcmoreshells.resource(id));
	}

	public static void register(IEventBus modBus) {
		Cbcmoreshells.REGISTRATE.addRawLang("itemGroup." + Cbcmoreshells.MODID, "CBC More Shells");
		TAB_REGISTER.register(modBus);
	}

}
