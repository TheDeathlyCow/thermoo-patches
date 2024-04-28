package com.github.thedeathlycow.thermoo.patches.adastra;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.AdAstraConfig;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpaceEnvironmentController extends EnvironmentControllerDecorator {
    /**
     * Constructs a decorator out of a base controller
     *
     * @param controller The base {@link #controller}
     */
    public SpaceEnvironmentController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public int getLocalTemperatureChange(World world, BlockPos pos) {
        if (PlanetApi.API.isPlanet(world)) {
            // we still calculate this because it may not necessarily apply to a player
            return AdAstraIntegration.getPlanetTemperature(world, pos);
        } else {
            return super.getLocalTemperatureChange(world, pos);
        }
    }

    @Override
    public int getEnvironmentTemperatureForPlayer(PlayerEntity player, int localTemperature) {
        World world = player.getWorld();
        if (PlanetApi.API.isPlanet(world)) {
            // cancel for thermoo, use ad astra api
            return 0;
        }

        return super.getEnvironmentTemperatureForPlayer(player, localTemperature);
    }
}
