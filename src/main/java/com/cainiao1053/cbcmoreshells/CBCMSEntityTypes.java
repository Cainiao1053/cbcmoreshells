package com.cainiao1053.cbcmoreshells;

import static com.cainiao1053.cbcmoreshells.Cbcmoreshells.REGISTRATE;

import java.util.function.Consumer;

import com.cainiao1053.cbcmoreshells.index.CBCMSMunitionPropertiesHandlers;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.AbstractCannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_shrapnel_torpedo.AirdroppedShrapnelTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.airdropped_torpedo.AirdroppedTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.antiair_he_shell.AntiairHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.ap_super_heavy.APSuperHeavyShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apbc_shot.APBCShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.apfsds_shot.APFSDSShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.aphe_cannon_rocket.APHECannonRocketProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baked_apfsds_shot.BakedAPFSDSShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.cannon_torpedo.CannonTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.deepwater_shrapnel_torpedo.DeepwaterShrapnelTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.he_cannon_rocket.HECannonRocketProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.hesh_shell.HESHShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.highspeed_torpedo.HighspeedTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.incendiary_he_shell.IncendiaryHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.inferior_he_shell.InferiorHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.long_range_torpedo.LongRangeTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo.MediumRangeDeepwaterTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_deepwater_torpedo_typeb.MediumRangeDeepwaterTorpedoTypeBProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.medium_range_torpedo_typeb.MediumRangeTorpedoTypeBProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sap_shell.SAPShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo.SharpnelTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo.TorpedoBurst;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.sharpnel_torpedo.TorpedoBurstRenderer;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_he_shell.ShellessHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_incendiary_he_shell.ShellessIncendiaryHEShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shelless_sap_shell.ShellessSAPShellProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.shellless_ap_shot.ShellessAPShotProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.CannonTorpedoProjectileRenderer;
import com.cainiao1053.cbcmoreshells.munitions.torpedo_tube.medium_range_torpedo.MediumRangeTorpedoProjectile;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.baguette_shot.BaguetteShotProjectile;


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
import rbasamoyai.createbigcannons.munitions.big_cannon.ap_shot.APShotProjectile;
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
	public static final EntityEntry<APBCShotProjectile> APBC_SHOT = cannonProjectile("apbc_shot", APBCShotProjectile::new, "APBC Shot", CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<APSuperHeavyShotProjectile> AP_SUPER_HEAVY_SHOT = cannonProjectile("ap_super_heavy_shot", APSuperHeavyShotProjectile::new, "AP Super Heavy Shot", CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<BakedAPFSDSShotProjectile> BAKED_APFSDS_SHOT = cannonProjectile("baked_apfsds_shot",BakedAPFSDSShotProjectile::new, "Baked APFSDS Shot",CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<BaguetteShotProjectile> BAGUETTE_SHOT = cannonProjectile("baguette_shot",BaguetteShotProjectile::new, "Baguette Shot",CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<IncendiaryHEShellProjectile> INCENDIARY_HE_SHELL = cannonProjectile("incendiary_he_shell", IncendiaryHEShellProjectile::new, "Incendiary HE Shell", CBCMSMunitionPropertiesHandlers.INCENDIARY_SHELL_PROJECTILE);
	public static final EntityEntry<ShellessIncendiaryHEShellProjectile> SHELLESS_INCENDIARY_HE_SHELL = cannonProjectile("shelless_incendiary_he_shell", ShellessIncendiaryHEShellProjectile::new, "Shelless Incendiary HE Shell", CBCMSMunitionPropertiesHandlers.INCENDIARY_SHELL_PROJECTILE);


	public static final EntityEntry<CannonTorpedoProjectile> CANNON_TORPEDO = torpedoProjectile("cannon_torpedo", CannonTorpedoProjectile::new, "Cannon Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeTorpedoProjectile> MEDIUM_RANGE_TORPEDO = torpedoProjectile("medium_range_torpedo", MediumRangeTorpedoProjectile::new, "Medium Range Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeDeepwaterTorpedoProjectile> MEDIUM_RANGE_DEEPWATER_TORPEDO = torpedoProjectile("medium_range_deepwater_torpedo", MediumRangeDeepwaterTorpedoProjectile::new, "Medium Range Deepwater Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<HighspeedTorpedoProjectile> HIGHSPEED_TORPEDO = torpedoProjectile("highspeed_torpedo", HighspeedTorpedoProjectile::new, "Highspeed Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<AirdroppedTorpedoProjectile> AIRDROPPED_TORPEDO = torpedoProjectile("airdropped_torpedo", AirdroppedTorpedoProjectile::new, "Airdropped Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<APFSDSShotProjectile> APFSDS_SHOT = cannonProjectile("apfsds_shot",APFSDSShotProjectile::new, "APFSDS Shot",CBCMSMunitionPropertiesHandlers.SHELLESS_INERT_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<AntiairHEShellProjectile> ANTIAIR_HE_SHELL = cannonProjectile("antiair_he_shell", AntiairHEShellProjectile::new, "Antiair HE Shell", CBCMSMunitionPropertiesHandlers.SHELLESS_SHELL_BIG_CANNON_PROJECTILE);
	public static final EntityEntry<LongRangeTorpedoProjectile> LONG_RANGE_TORPEDO = torpedoProjectile("long_range_torpedo", LongRangeTorpedoProjectile::new, "Long Range Torpedo", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<SharpnelTorpedoProjectile> SHARPNEL_TORPEDO = torpedoProjectile("sharpnel_torpedo", SharpnelTorpedoProjectile::new, "Sharpnel Torpedo", CBCMSMunitionPropertiesHandlers.SHRAPNEL_TORPEDO_PROJECTILE);
	public static final EntityEntry<AirdroppedShrapnelTorpedoProjectile> AIRDROPPED_SHRAPNEL_TORPEDO = torpedoProjectile("airdropped_shrapnel_torpedo", AirdroppedShrapnelTorpedoProjectile::new, "Airdropped Shrapnel Torpedo", CBCMSMunitionPropertiesHandlers.SHRAPNEL_TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeTorpedoTypeBProjectile> MEDIUM_RANGE_TORPEDO_TYPEB = torpedoProjectile("medium_range_torpedo_typeb", MediumRangeTorpedoTypeBProjectile::new, "Medium Range Torpedo Type B", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<MediumRangeDeepwaterTorpedoTypeBProjectile> MEDIUM_RANGE_DEEPWATER_TORPEDO_TYPEB = torpedoProjectile("medium_range_deepwater_torpedo_typeb", MediumRangeDeepwaterTorpedoTypeBProjectile::new, "Medium Range Deepwater Torpedo Type B", CBCMSMunitionPropertiesHandlers.TORPEDO_PROJECTILE);
	public static final EntityEntry<DeepwaterShrapnelTorpedoProjectile> DEEPWATER_SHRAPNEL_TORPEDO = torpedoProjectile("deepwater_shrapnel_torpedo", DeepwaterShrapnelTorpedoProjectile::new, "Deepwater Shrapnel Torpedo", CBCMSMunitionPropertiesHandlers.SHRAPNEL_TORPEDO_PROJECTILE);






	public static final EntityEntry<TorpedoBurst> TORPEDO_BURST = REGISTRATE
			.entity("torpedo_burst", TorpedoBurst::new, MobCategory.MISC)
			.properties(shrapnel())
			.renderer(() -> TorpedoBurstRenderer::new)
			.onRegister(type -> MunitionPropertiesHandler.registerProjectileHandler(type, CBCMunitionPropertiesHandlers.PROJECTILE_BURST))
			.register();


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
