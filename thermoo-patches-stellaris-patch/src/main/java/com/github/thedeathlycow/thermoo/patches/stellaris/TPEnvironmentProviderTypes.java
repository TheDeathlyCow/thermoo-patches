package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProvider;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProviderType;
import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;

public final class TPEnvironmentProviderTypes {
    public static final EnvironmentProviderType<InArtificialEnvironmentProvider> IN_ARTIFICIAL_ENVIRONMENT = register(
            "in_artificial_environment",
            InArtificialEnvironmentProvider.CODEC
    );

    public static void initialize() {
        StellarisPatch.LOGGER.debug("Initialized Thermoo Stellaris Patch Environment Provider Types");
    }

    private static <T extends EnvironmentProvider> EnvironmentProviderType<T> register(
            String name,
            MapCodec<T> codec
    ) {
        return Registry.register(
                ThermooRegistries.ENVIRONMENT_PROVIDER_TYPE,
                StellarisPatch.id(name),
                new EnvironmentProviderType<>(codec)
        );
    }

    private TPEnvironmentProviderTypes() {

    }
}
