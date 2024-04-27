package com.github.thedeathlycow.thermoo.patches;

import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import com.github.thedeathlycow.thermoo.patches.fabricseasons.FabricSeasonsProvider;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
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

    public static ThermooPatchesConfig getConfig() {
        return AutoConfig.getConfigHolder(ThermooPatchesConfig.class).get();
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(ThermooPatchesConfig.class, GsonConfigSerializer::new);
        checkMultiDependency(IntegratedMod.ARMOR_POINTS_PP, IntegratedMod.LIBHUD);
        FabricSeasonsProvider.registerSeasonProviderEvent();
        logPatchedMods();
    }

    private static void checkMultiDependency(IntegratedMod... requiredMods) {
        boolean isNotMet = Arrays.stream(requiredMods)
                .map(IntegratedMod::isModLoaded)
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
                            builder.append(mod.getId());
                        }
                );

        if (builder.isEmpty()) {
            LOGGER.warn("No Thermoo patches available for current mod set!");
        } else {
            LOGGER.info("Initialized Thermoo Patches for the following mods: {}", builder);
        }
    }
}