package com.github.thedeathlycow.thermoo.patches.compat.stellaris;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.api.util.TemperatureConverter;
import com.st0x0ef.stellaris.common.data.planets.Planet;
import com.st0x0ef.stellaris.common.oxygen.DimensionOxygenManager;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.minecraft.server.world.ServerWorld;
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
        if (world instanceof ServerWorld serverWorld) {
            Planet planet = PlanetUtil.getPlanet(world.getRegistryKey().getValue());
            if (planet != null && !planet.oxygen()) {
                DimensionOxygenManager manager = GlobalOxygenManager.getInstance().getOrCreateDimensionManager(serverWorld);
                return !manager.hasOxygenAt(pos)
                        ? TemperatureConverter.celsiusToTemperatureTick(planet.temperature())
                        : super.getLocalTemperatureChange(world, pos);
            }
        }

        return super.getLocalTemperatureChange(world, pos);
    }
}
