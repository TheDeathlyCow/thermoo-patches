package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.config;

import com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.ThermooPatches;
import dev.isxander.yacl3.api.ButtonOption;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class ThermooPatchesConfig {
    public static final Event<ResourceLocation, ConfigProvider> BUILD_CLIENT = ThermooPatches.EVENT_MANAGER.create(
            ConfigProvider.class,
            listeners -> builder -> {
                for (ConfigProvider listener : listeners) {
                    listener.build(builder);
                }
            }
    );

    public static final Event<ResourceLocation, ConfigProvider> BUILD_COMMON = ThermooPatches.EVENT_MANAGER.create(
            ConfigProvider.class,
            listeners -> builder -> {
                for (ConfigProvider listener : listeners) {
                    listener.build(builder);
                }
            }
    );

    public static final String MAIN_CATEGORY_NAME = "general";

    @FunctionalInterface
    public interface ConfigProvider {
        void build(Consumer<ButtonOption> builder);
    }
}