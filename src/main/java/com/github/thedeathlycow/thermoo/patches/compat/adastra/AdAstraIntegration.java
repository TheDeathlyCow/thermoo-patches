package com.github.thedeathlycow.thermoo.patches.compat.adastra;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentControllerInitializeEvent;
import com.github.thedeathlycow.thermoo.api.temperature.event.PlayerEnvironmentEvents;
import com.github.thedeathlycow.thermoo.api.util.TemperatureConverter;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.AdAstraConfig;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdAstraIntegration {

    public static void init() {
        // prevent ad astra temperature ticks for players - to be replaced by Thermoo env controller
        AdAstraEvents.HotTemperatureTickEvent.register(
                (serverWorld, livingEntity) -> {
                    temperatureTick(serverWorld, livingEntity);
                    return false;
                }
        );
        AdAstraEvents.ColdTemperatureTickEvent.register(
                (serverWorld, livingEntity) -> {
                    temperatureTick(serverWorld, livingEntity);
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
        if (config.enableSpacePassiveTemperature() && !planet.oxygen()) {
            double temperatureCelsius = TemperatureApi.API.getTemperature(world, pos);
            return TemperatureConverter.celsiusToTemperatureTick(temperatureCelsius);
        } else {
            return 0;
        }
    }

    private static void temperatureTick(ServerWorld world, LivingEntity entity) {
        int temperatureChange = getPlanetTemperature(world, entity.getBlockPos());

        boolean applyChange = true;
        if (entity instanceof PlayerEntity player) {
            applyChange = PlayerEnvironmentEvents.CAN_APPLY_PASSIVE_TEMPERATURE_CHANGE.invoker()
                    .canApplyChange(temperatureChange, player);
        }

        if (applyChange) {
            // note: temperature tick only occurs once per second
            entity.thermoo$addTemperature(temperatureChange * 20, HeatingModes.PASSIVE);
        }
    }

    private AdAstraIntegration() {

    }

}
