package com.github.thedeathlycow.thermoo.patches.adastra;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.AdAstraConfig;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdAstraIntegration {

    public static void init() {
        // prevent ad astra temperature ticks for players - to be replaced by Thermoo env controller
        AdAstraEvents.HotTemperatureTickEvent.register(
                (serverWorld, livingEntity) -> {
                    int temperatureChange = getPlanetTemperature(
                            serverWorld, livingEntity.getBlockPos()
                    );
                    livingEntity.thermoo$addTemperature(temperatureChange, HeatingModes.PASSIVE);

                    return false;
                }
        );
        AdAstraEvents.ColdTemperatureTickEvent.register(
                (serverWorld, livingEntity) -> {
                    int temperatureChange = getPlanetTemperature(
                            serverWorld, livingEntity.getBlockPos()
                    );
                    livingEntity.thermoo$addTemperature(temperatureChange, HeatingModes.PASSIVE);

                    return false;
                }
        );

        EnvironmentControllerInitializeEvent.EVENT.register(
                EnvironmentControllerInitializeEvent.OVERRIDE_PHASE,
                SpaceEnvironmentController::new
        );
    }

    static int getPlanetTemperature(World world, BlockPos pos) {
        Planet planet = PlanetApi.API.getPlanet(world);
        if (planet == null) {
            return 0;
        }

        AdAstraConfig config = ThermooPatches.getConfig().adAstraConfig;

        int temperature = 0;
        if (planet.isSpace()) {
            temperature = config.getOrbitTemperaturePerSecond();
        } else if (TemperatureApi.API.isHot(world, pos)) {
            temperature = config.getHotPlanetTemperaturePerSecond();
        } else if (TemperatureApi.API.isCold(world, pos)) {
            temperature = config.getColdPlanetTemperaturePerSecond();
        }

        return temperature;
    }

    private AdAstraIntegration() {

    }

}
