package com.github.thedeathlycow.thermoo.patches.compat.stellaris;

import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;

public class StellarisIntegration {


    public static void init() {
        if (IntegratedMod.STELLARIS.isModLoaded()) {
            EnvironmentControllerInitializeEvent.EVENT.register(
                    EnvironmentControllerInitializeEvent.OVERRIDE_PHASE,
                    SpaceEnvironmentController::new
            );
        }
    }

    private StellarisIntegration() {

    }
}
