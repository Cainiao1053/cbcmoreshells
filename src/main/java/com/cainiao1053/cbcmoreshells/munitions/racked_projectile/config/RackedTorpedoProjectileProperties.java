package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.TorpedoProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;

public record RackedTorpedoProjectileProperties(BallisticPropertiesComponent ballistics, EntityDamagePropertiesComponent damage,
										 RackedProjectilePropertiesComponent rackedProjectileProperties,
											 BigCannonFuzePropertiesComponent fuze, ExplosionPropertiesComponent explosion, int explosionCooldown, int lifetime, float buoyancyFactor, float steadyStateVel, float waterDamp) {
}
