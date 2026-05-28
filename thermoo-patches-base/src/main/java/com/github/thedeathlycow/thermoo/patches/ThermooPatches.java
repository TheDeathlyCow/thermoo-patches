package com.github.thedeathlycow.thermoo.patches;

import dev.yumi.commons.event.EventManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Arrays;

public class ThermooPatches implements ModInitializer {
    public static final String MODID = "thermoo-patches";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final EventManager<Identifier> EVENT_MANAGER = new EventManager<>(id("default"), Identifier::parse);

    @Override
    public void onInitialize() {
        logPatchedMods();
    }

    public static void checkMultiDependency(IntegratedMod... requiredMods) {
        boolean isNotMet = Arrays.stream(requiredMods)
                .map(IntegratedMod::isModLoaded)
                .reduce(false, (acc, isLoaded) -> acc ^ isLoaded);
        if (isNotMet) {
            throw new MultiDependencyException(requiredMods);
        }
    }

    public static Identifier thermooId(String path) {
        return Identifier.fromNamespaceAndPath("thermoo", path);
    }

    @Contract("_->new")
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir().resolve(MODID);
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

                            FabricLoader.getInstance()
                                    .getModContainer(mod.getId())
                                    .ifPresent(container -> {
                                        builder.append(": ")
                                                .append(container.getMetadata().getName());
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