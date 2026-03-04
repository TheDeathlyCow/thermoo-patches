package com.github.thedeathlycow.thermoo.patches.origins;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public final class ThermooPowerTypes {
    public static final PowerConfiguration<DisableEffectsPowerType> CONFIGURATION = register(
            PowerConfiguration.of(
                    ThermooPatches.id("disable_temperature_effects"),
                    DisableEffectsPowerType.DATA_FACTORY
            )
    );

    static void initialize() {
        ThermooPatches.LOGGER.debug("Initialized Thermoo Patches Origins Power Types");
    }

    @SuppressWarnings("unchecked")
    private static <T extends PowerType> PowerConfiguration<T> register(PowerConfiguration<T> configuration) {
        PowerConfiguration<PowerType> casted = (PowerConfiguration<PowerType>) configuration;
        Registry.register(ApoliRegistries.POWER_TYPE, casted.id(), casted);
        return configuration;
    }

    private ThermooPowerTypes() {

    }
}