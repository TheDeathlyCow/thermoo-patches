package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StellarisPatch implements ModInitializer {
    public static final String MODID = "thermoo-patches-stellaris-patch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        if (IntegratedMod.STELLARIS.isModLoaded()) {
            TPEnvironmentProviderTypes.initialize();
            TemperatureResistanceModifiers.initialize();
        }
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}