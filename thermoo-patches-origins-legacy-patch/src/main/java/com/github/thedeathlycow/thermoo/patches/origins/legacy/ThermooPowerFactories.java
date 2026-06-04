package com.github.thedeathlycow.thermoo.patches.origins.legacy;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.core.Registry;

public final class ThermooPowerFactories {
    static void initialize() {
        ThermooPatches.LOGGER.debug("Initialized Thermoo Patches Origins Powers");
        register(IgnoreHarmfulStatusesPower::createFactory);
    }

    private static void register(PowerFactory<?> powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }

    private static void register(PowerFactorySupplier<?> factorySupplier) {
        register(factorySupplier.createFactory());
    }

    private ThermooPowerFactories() {

    }
}