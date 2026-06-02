package com.github.thedeathlycow.thermoo.patches.neoforge.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.concurrent.atomic.AtomicBoolean;

public final class ConfigScreen {
    public static final String TITLE = "thermoo-patches.title";
    public static final String CLIENT_TITLE = "thermoo-patches.config.client.title";
    public static final String COMMON_TITLE = "thermoo-patches.config.common.title";

    public static Screen get(Screen parent) {
        var generatedAnyClientSettings = new AtomicBoolean(false);
        var clientOptions = OptionGroup.createBuilder().name(Component.translatable(CLIENT_TITLE));
        ThermooPatchesConfig.BUILD_CLIENT.invoker().build(option -> {
            generatedAnyClientSettings.set(true);
            clientOptions.option(option);
        });

        var generatedAnyCommonSettings = new AtomicBoolean(false);
        var commonOptions = OptionGroup.createBuilder().name(Component.translatable(COMMON_TITLE));
        ThermooPatchesConfig.BUILD_COMMON.invoker().build(option -> {
            generatedAnyCommonSettings.set(true);
            commonOptions.option(option);
        });

        var configBuilder = ConfigCategory.createBuilder().name(Component.translatable(TITLE));

        if (generatedAnyClientSettings.get()) {
            configBuilder.group(clientOptions.build());
        }

        if (generatedAnyCommonSettings.get()) {
            configBuilder.group(commonOptions.build());
        }

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable(TITLE))
                .category(configBuilder.build())
                .build()
                .generateScreen(parent);
    }

    public static ButtonOption createConfigSubsectionButton(ConfigClassHandler<?> handler, String titleKey, String descKey) {
        return ButtonOption.createBuilder()
                .name(Component.translatable(titleKey))
                .description(
                        OptionDescription.createBuilder()
                                .text(Component.translatable(descKey))
                                .build()
                )
                .text(Component.literal(""))
                .action((yaclScreen, buttonOption) -> {
                    Minecraft.getInstance()
                            .setScreen(handler
                                    .generateGui()
                                    .generateScreen(yaclScreen));
                }).build();
    }
}