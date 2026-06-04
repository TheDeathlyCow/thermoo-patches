package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base;


import com.github.thedeathlycow.thermoo.patches.neoforge.impl.serene.seasons.SereneSeasonsPatch;
import dev.yumi.commons.event.EventManager;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.YumiMods;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Arrays;

public class ThermooPatches implements ModInitializer {
    public static final String MOD_ID = "thermoo_patches";
    public static final String RES_ID = "thermoo-patches";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EventManager<ResourceLocation> EVENT_MANAGER = new EventManager<>(id("default"), ResourceLocation::parse);

    @Override
    public void onInitialize(ModContainer mod) {
        logPatchedMods();

        if (IntegratedMod.SERENE_SEASONS.isModLoaded()) {
            SereneSeasonsPatch.onInitialize(mod);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(RES_ID, path);
    }

    public static Path getConfigDir() {
        return YumiMods.get().getConfigDirectory().resolve(RES_ID);
    }

    private static void logPatchedMods() {
        var builder = new StringBuilder();
        Arrays.stream(IntegratedMod.values())
                .filter(IntegratedMod::isModLoaded)
                .forEach(
                        mod -> {
                            builder.append('\n')
                                    .append(" - ")
                                    .append(mod.getId());

                            YumiMods.get()
                                    .getMod(mod.getId())
                                    .ifPresent(container -> {
                                        builder.append(": ")
                                                .append(container.getName());
                                    });
                        }
                );

        if (builder.isEmpty()) {
            LOGGER.warn("Thermoo Patches has no available patches for current mod set, are you sure you need this mod?");
        } else {
            LOGGER.info("Initialized Thermoo Patches for the following mods: {}", builder);
        }
    }
}