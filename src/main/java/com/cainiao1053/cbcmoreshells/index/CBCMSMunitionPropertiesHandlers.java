package com.cainiao1053.cbcmoreshells.index;

import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.BigCannonShellessShellPropertiesHandler;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.ShellessInertBigCannonPropertiesHandler;
import com.cainiao1053.cbcmoreshells.munitions.big_cannon.config.TorpedoPropertiesHandler;

public class CBCMSMunitionPropertiesHandlers {

	public static final BigCannonShellessShellPropertiesHandler SHELLESS_SHELL_BIG_CANNON_PROJECTILE = new BigCannonShellessShellPropertiesHandler();
	public static final ShellessInertBigCannonPropertiesHandler SHELLESS_INERT_BIG_CANNON_PROJECTILE = new ShellessInertBigCannonPropertiesHandler();
	public static final TorpedoPropertiesHandler TORPEDO_PROJECTILE = new TorpedoPropertiesHandler();


	public static void init() {}

}
