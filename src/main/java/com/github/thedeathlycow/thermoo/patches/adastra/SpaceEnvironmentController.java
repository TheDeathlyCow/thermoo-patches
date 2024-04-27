package com.github.thedeathlycow.thermoo.patches.adastra;

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
        Planet planet = PlanetApi.API.getPlanet(world.getRegistryKey());
//
//        if (planet == null) {
//            return super.getLocalTemperatureChange(world, pos);
//        } else {
//
//        }
        return 0;
    }

    @Override
    public int getEnvironmentTemperatureForPlayer(PlayerEntity player, int localTemperature) {
        return super.getEnvironmentTemperatureForPlayer(player, localTemperature);
    }
}
