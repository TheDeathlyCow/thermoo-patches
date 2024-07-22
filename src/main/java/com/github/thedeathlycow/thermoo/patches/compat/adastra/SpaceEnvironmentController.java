package com.github.thedeathlycow.thermoo.patches.compat.adastra;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.planets.PlanetApi;
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
        Planet planet = PlanetApi.API.getPlanet(world);
        if (AdAstraIntegration.isNotEarthLike(planet)) {
            // we still calculate this because it may not necessarily apply to a player
            // eg, may want to have it for the scorchful thermometer
            return AdAstraIntegration.getPlanetTemperature(world, pos);
        } else {
            return super.getLocalTemperatureChange(world, pos);
        }
    }

    @Override
    public int getEnvironmentTemperatureForPlayer(PlayerEntity player, int localTemperature) {
        World world = player.getWorld();
        Planet planet = PlanetApi.API.getPlanet(world);
        if (AdAstraIntegration.isNotEarthLike(planet)) {
            // cancel for thermoo, use ad astra api
            return 0;
        }

        return super.getEnvironmentTemperatureForPlayer(player, localTemperature);
    }
}
