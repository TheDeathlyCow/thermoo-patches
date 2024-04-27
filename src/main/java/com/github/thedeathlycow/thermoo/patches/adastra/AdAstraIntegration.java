package com.github.thedeathlycow.thermoo.patches.adastra;

import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import earth.terrarium.adastra.api.events.AdAstraEvents;

public class AdAstraIntegration {



    public static void init() {
        // prevent ad astra temperature ticks - to be replaced by Thermoo env controller
        AdAstraEvents.TemperatureTickEvent.register((serverLevel, livingEntity) -> false);

        EnvironmentControllerInitializeEvent.EVENT.register(
                EnvironmentControllerInitializeEvent.OVERRIDE_PHASE,
                SpaceEnvironmentController::new
        );
    }

    private AdAstraIntegration() {

    }

}
