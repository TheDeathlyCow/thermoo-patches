package com.github.thedeathlycow.thermoo.patches;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ThermooPatches implements ModInitializer {
    public static final String MODID = "thermoo-patches";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        logPatchMods();
    }

    private static void logPatchMods() {
        var builder = new StringBuilder();
        Arrays.stream(IntegratedMod.values())
                .filter(IntegratedMod::isModLoaded)
                .forEach(
                        mod -> {
                            builder.append('\n');
                            builder.append(" - ");
                            builder.append(mod.id);
                        }
                );


        String loadedMods = builder.toString();
        if (loadedMods.isEmpty()) {
            LOGGER.info("No Thermoo patches available for current mod set.");
        } else {
            LOGGER.info("Initialized Thermoo Patches for the following mods: {}", loadedMods);
        }
    }

    public enum IntegratedMod {

        ARMOR_POINTS_PP("armorpointspp");

        private final String id;

        IntegratedMod(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public boolean isModLoaded() {
            return FabricLoader.getInstance().isModLoaded(id);
        }
    }
}