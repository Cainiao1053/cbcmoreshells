package com.cainiao1053.cbcmoreshells.munitions.big_cannon.config;

import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonFuzePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.big_cannon.config.BigCannonProjectilePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.BallisticPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.EntityDamagePropertiesComponent;
import rbasamoyai.createbigcannons.munitions.config.components.ExplosionPropertiesComponent;
import rbasamoyai.createbigcannons.munitions.fragment_burst.ProjectileBurstParentPropertiesComponent;

public record ShrapnelShellessShellProperties(BallisticPropertiesComponent ballistics, EntityDamagePropertiesComponent damage,
											  BigCannonProjectilePropertiesComponent bigCannonProperties,
											  BigCannonFuzePropertiesComponent fuze, ExplosionPropertiesComponent explosion, float maxCharges, int lifetime, ProjectileBurstParentPropertiesComponent burst) {
}
