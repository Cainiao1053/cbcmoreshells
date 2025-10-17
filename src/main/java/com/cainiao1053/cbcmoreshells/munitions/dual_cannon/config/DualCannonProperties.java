package com.cainiao1053.cbcmoreshells.munitions.dual_cannon.config;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
//import rbasamoyai.createbigcannons.munitions.big_cannon.config.TorpedoProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;

public record DualCannonProperties(BallisticPropertiesComponent ballistics, EntityDamagePropertiesComponent damage,
								   DualCannonPropertiesComponent dualCannonProperties,
											 BigCannonFuzePropertiesComponent fuze, ExplosionPropertiesComponent explosion, int explosionCooldown) {
}
