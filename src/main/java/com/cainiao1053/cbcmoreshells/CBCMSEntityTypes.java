package com.cainiao1053.cbcmoreshells;

import static com.cainiao1053.cbcmoreshells.Cbcmoreshells.REGISTRATE;

import java.util.function.Consumer;

import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.AbstractCannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_torpedo.AirdroppedTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_he_shell.AntiairHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot.APFSDSShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.aphe_cannon_rocket.APHECannonRocketProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.cannon_torpedo.CannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.he_cannon_rocket.HECannonRocketProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.hesh_shell.HESHShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.highspeed_torpedo.HighspeedTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.inferior_he_shell.InferiorHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.long_range_torpedo.LongRangeTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo.MediumRangeDeepwaterTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sap_shell.SAPShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_he_shell.ShellessHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_sap_shell.ShellessSAPShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shellless_ap_shot.ShellessAPShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.CannonTorpedoProjectileRenderer;
import com.cainiao1053.cbcmoreshells.munitions.torpedo_tube.medium_range_torpedo.MediumRangeTorpedoProjectile;


import com.simibubi.create.content.contraptions.render.OrientedContraptionEntityRenderer;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import rbasamoyai.createbigcannons.index.CBCMunitionPropertiesHandlers;
import rbasamoyai.createbigcannons.multiloader.EntityTypeConfigurator;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonProjectileRenderer;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesHandler;
import rbasamoyai.createbigcannons.munitions.config.PropertiesTypeHandler;
import rbasamoyai.ritchiesprojectilelib.RPLTags;

public class CBCMSEntityTypes {




	public static final EntityEntry<InferiorHEShellProjectile> Inferior_HE_SHELL = cannonProjectile("inferior_he_shell", InferiorHEShellProjectile::new, "Inferior High Explosive Shell", CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<SAPShellProjectile> SAP_SHELL = cannonProjectile("sap_shell", SAPShellProjectile::new, "SAP Shell", CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<HESHShellProjectile> HESH_SHELL = cannonProjectile("hesh_shell", HESHShellProjectile::new, "HESH Shell", CBCMunitionPropertiesHandlers.COMMON_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<ShellessHEShellProjectile> SHELLESS_HE_SHELL = cannonProjectile("shelless_he_shell", ShellessHEShellProjectile::new, "Shelless HE Shell", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<APHECannonRocketProjectile> APHE_CANNON_ROCKET = cannonProjectile("aphe_cannon_rocket", APHECannonRocketProjectile::new, "APHE Cannon Rocket", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<ShellessAPShotProjectile> SHELLESS_AP_SHOT = cannonProjectile("shelless_ap_shot", ShellessAPShotProjectile::new, "Shelless AP Shot", CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<ShellessSAPShellProjectile> SHELLESS_SAP_SHELL = cannonProjectile("shelless_sap_shell", ShellessSAPShellProjectile::new, "Shelless SAP Shell", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<HECannonRocketProjectile> HE_CANNON_ROCKET = cannonProjectile("he_cannon_rocket", HECannonRocketProjectile::new, "HE Cannon Rocket", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<CannonTorpedoProjectile> CANNON_TORPEDO = torpedoProjectile("cannon_torpedo", CannonTorpedoProjectile::new, "Cannon Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeTorpedoProjectile> MEDIUM_RANGE_TORPEDO = torpedoProjectile("medium_range_torpedo", MediumRangeTorpedoProjectile::new, "Medium Range Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeDeepwaterTorpedoProjectile> MEDIUM_RANGE_DEEPWATER_TORPEDO = torpedoProjectile("medium_range_deepwater_torpedo", MediumRangeDeepwaterTorpedoProjectile::new, "Medium Range Deepwater Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<HighspeedTorpedoProjectile> HIGHSPEED_TORPEDO = torpedoProjectile("highspeed_torpedo", HighspeedTorpedoProjectile::new, "Highspeed Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<AirdroppedTorpedoProjectile> AIRDROPPED_TORPEDO = torpedoProjectile("airdropped_torpedo", AirdroppedTorpedoProjectile::new, "Airdropped Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<APFSDSShotProjectile> APFSDS_SHOT = cannonProjectile("apfsds_shot",APFSDSShotProjectile::new, "APFSDS Shot",CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<AntiairHEShellProjectile> ANTIAIR_HE_SHELL = cannonProjectile("antiair_he_shell", AntiairHEShellProjectile::new, "Antiair HE Shell", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<LongRangeTorpedoProjectile> LONG_RANGE_TORPEDO = torpedoProjectile("long_range_torpedo", LongRangeTorpedoProjectile::new, "Long Range Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);








	private static <T extends AbstractBigCannonProjectile> EntityEntry<T>
	cannonProjectile(String id, EntityFactory<T> factory, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
				.entity(id, factory, MobCategory.MISC)
				.properties(cannonProperties())
				.renderer(() -> BigCannonProjectileRenderer::new)
				.tag(RPLTags.PRECISE_MOTION)
				.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
				.register();
	}

	private static <T extends AbstractBigCannonProjectile> EntityEntry<T>
	cannonProjectile(String id, EntityFactory<T> factory, String enUSdiffLang, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
				.entity(id, factory, MobCategory.MISC)
				.properties(cannonProperties())
				.renderer(() -> BigCannonProjectileRenderer::new)
				.tag(RPLTags.PRECISE_MOTION)
				.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
				.register();
	}

	private static <T extends AbstractCannonTorpedoProjectile> EntityEntry<T>
	torpedoProjectile(String id, EntityFactory<T> factory, String enUSdiffLang, PropertiesTypeHandler<EntityType<?>, ?> handler) {
		return REGISTRATE
				.entity(id, factory, MobCategory.MISC)
				.properties(cannonProperties())
				.renderer(() -> CannonTorpedoProjectileRenderer::new)
				.tag(RPLTags.PRECISE_MOTION)
				.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, handler))
				.register();
	}

	private static <T> NonNullConsumer<T> configure(Consumer<EntityTypeConfigurator> cons) {
		return b -> cons.accept(EntityTypeConfigurator.of(b));
	}

	private static <T> NonNullConsumer<T> autocannonProperties() {
		return configure(c -> c.size(0.2f, 0.2f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(false) // Mixin ServerEntity to not track motion
			.trackingRange(16));
	}

	private static <T> NonNullConsumer<T> cannonProperties() {
		return configure(c -> c.size(0.8f, 0.8f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(false) // Ditto
			.trackingRange(16));
	}

	private static <T> NonNullConsumer<T> shrapnel() {
		return configure(c -> c.size(0.8f, 0.8f)
			.fireImmune()
			.updateInterval(1)
			.updateVelocity(true)
			.trackingRange(16));
	}

	public static void register() {
	}

}
