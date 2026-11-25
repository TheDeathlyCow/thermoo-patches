package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureRecord;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureUnit;
import com.github.thedeathlycow.thermoo.api.environment.v2.attribute.ThermooEnvironmentAttributes;
import net.fabricmc.fabric.api.dimension.v1.DimensionEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;

public final class DimensionModifier {
    public static final ResourceKey<DimensionType> MOON = ResourceKey.create(Registries.DIMENSION_TYPE, StellarisPatch.stellarisId("moon"));
    public static final ResourceKey<DimensionType> EARTH_ORBIT = ResourceKey.create(Registries.DIMENSION_TYPE, StellarisPatch.stellarisId("earth_orbit"));

    public static void initialize() {
        DimensionEvents.MODIFY_ATTRIBUTES.register((dimension, attributes, registries) -> {
            if (dimension.is(MOON)) {
                attributes.set(ThermooEnvironmentAttributes.ATMOSPHERIC_PRESSURE, 3e-12);
                attributes.set(ThermooEnvironmentAttributes.TEMPERATURE, new TemperatureRecord(140, TemperatureUnit.KELVIN));
            } else if (dimension.is(EARTH_ORBIT)) {
                attributes.set(ThermooEnvironmentAttributes.ATMOSPHERIC_PRESSURE, 7e-9);
                attributes.set(ThermooEnvironmentAttributes.TEMPERATURE, new TemperatureRecord(210, TemperatureUnit.KELVIN));
            }
        });
    }


    private DimensionModifier() {

    }
}