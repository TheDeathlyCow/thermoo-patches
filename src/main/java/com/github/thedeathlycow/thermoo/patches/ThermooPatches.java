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

        if (IntegratedMod.ARMOR_POINTS_PP.isModLoaded() != IntegratedMod.LIBHUD.isModLoaded()) {
            throw new IllegalStateException("BOTH libhud AND Armor Points++ are required for Thermoo Patches to work. " +
                    "You can get libhud here: https://modrinth.com/mod/libhud, and Armor Points++ here: https://modrinth.com/mod/armorpoints");
        }

        logPatchedMods();
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


        String loadedMods = builder.toString();
        if (loadedMods.isEmpty()) {
            LOGGER.info("No Thermoo patches available for current mod set.");
        } else {
            LOGGER.info("Initialized Thermoo Patches for the following mods: {}", loadedMods);
        }
    }

    public enum IntegratedMod {

        LIBHUD("libhud"),
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