package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import dev.isxander.yacl3.api.ButtonOption;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public class ThermooPatchesConfig {
    public static final Event<Identifier, ConfigProvider> BUILD_CLIENT = ThermooPatches.EVENT_MANAGER.create(
            ConfigProvider.class,
            listeners -> builder -> {
                for (ConfigProvider listener : listeners) {
                    listener.build(builder);
                }
            }
    );

    public static final Event<Identifier, ConfigProvider> BUILD_COMMON = ThermooPatches.EVENT_MANAGER.create(
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