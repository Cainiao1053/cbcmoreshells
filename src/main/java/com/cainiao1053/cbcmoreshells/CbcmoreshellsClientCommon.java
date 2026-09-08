package com.cainiao1053.cbcmoreshells;

import com.cainiao1053.cbcmoreshells.munitions.dual_cannon.shaolib.client.CBCMSDualCannonProjectileClient;
import com.cainiao1053.cbcmoreshells.ponder.CBCMSPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;

public class CbcmoreshellsClientCommon {
    public static void onClientSetup() {
        PonderIndex.addPlugin(new CBCMSPonderPlugin());
        CBCMSDualCannonProjectileClient.register();
    }
}