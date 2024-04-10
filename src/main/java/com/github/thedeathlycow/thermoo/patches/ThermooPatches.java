package com.github.thedeathlycow.thermoo.patches;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ThermooPatches implements ModInitializer {
    public static final String MODID = "thermoo-patches";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Contract("_->new")
    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    @Override
    public void onInitialize() {
        checkMultiDependency(IntegratedMod.ARMOR_POINTS_PP, IntegratedMod.LIBHUD);
        logPatchedMods();
    }

    private static void checkMultiDependency(ThermooPatches.IntegratedMod... requiredMods) {
        boolean isNotMet = Arrays.stream(requiredMods)
                .map(ThermooPatches.IntegratedMod::isModLoaded)
                .reduce(false, (acc, isLoaded) -> acc ^ isLoaded);
        if (isNotMet) {
            throw new MultiDependencyException(requiredMods);
        }
    }

    private static void logPatchedMods() {
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

        if (builder.isEmpty()) {
            LOGGER.warn("No Thermoo patches available for current mod set!");
        } else {
            LOGGER.info("Initialized Thermoo Patches for the following mods: {}", builder);
        }
    }

    public enum IntegratedMod {

        LIBHUD("libhud", "https://modrinth.com/mod/libhud"),
        ARMOR_POINTS_PP("armorpointspp", "https://modrinth.com/mod/armorpoints");

        private final String id;

        private final String modpage;

        IntegratedMod(String id, String modpage) {
            this.id = id;
            this.modpage = modpage;
        }

        public String getId() {
            return id;
        }

        public String getModpage() {
            return modpage;
        }

        public boolean isModLoaded() {
            return FabricLoader.getInstance().isModLoaded(id);
        }
    }
}