package com.github.thedeathlycow.thermoo.patches.compat.immersiveweathering;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import com.ordana.immersive_weathering.blocks.IcicleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IcicleFreezeHandler {

    public static void consumeIcicle(LivingEntity entity) {
        ThermooPatchesConfig config = ThermooPatches.getConfig();
        int freezing = config.immersiveWeatheringConfig.freezingFromEatingIcicle;
        entity.thermoo$addTemperature(freezing, HeatingModes.ACTIVE);
    }

    public static void applyIcicleFreezing(Entity entity) {
        if (entity instanceof TemperatureAware temperatureAware) {
            ThermooPatchesConfig config = ThermooPatches.getConfig();
            int freezing = config.immersiveWeatheringConfig.freezingFromIcicleFall;
            temperatureAware.thermoo$addTemperature(freezing, HeatingModes.ACTIVE);
        }
    }

    private IcicleFreezeHandler() {

    }

}
