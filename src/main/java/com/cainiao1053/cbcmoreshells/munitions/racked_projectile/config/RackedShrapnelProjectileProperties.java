package com.cainiao1053.cbcmoreshells.munitions.racked_projectile.config;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.TorpedoProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.ProjectileBurstParentPropertiesComponent;

public record RackedShrapnelProjectileProperties(BallisticPropertiesComponent ballistics, EntityDamagePropertiesComponent damage,
										 RackedProjectilePropertiesComponent rackedProjectileProperties,
											 BigCannonFuzePropertiesComponent fuze, ExplosionPropertiesComponent explosion, int explosionCooldown, int lifetime, ProjectileBurstParentPropertiesComponent burst, int destroyRange) {
}
