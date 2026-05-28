package com.github.thedeathlycow.thermoo.patches.nycto;

import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatusEvents;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.tag.TemperatureStatusTags;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import dev.yumi.commons.TriState;
import moriyashiine.nycto.api.NyctoAPI;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class NyctoPatch implements ModInitializer {
    public static final String MODID = "thermoo-patches-nycto-patch";

    @Override
    public void onInitialize() {
        if (IntegratedMod.NYCTO.isModLoaded()) {
            Identifier phase = id("default");
            TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.addPhaseOrdering(
                    phase,
                    ThermooPatches.thermooId("default")
            );

            TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.register(phase, (entity, statusReference) -> {
                boolean isColdAndHarmful = statusReference.is(TemperatureStatusTags.COLD)
                        && statusReference.is(TemperatureStatusTags.HARMFUL);

                if (isColdAndHarmful && NyctoAPI.isVampire(entity)) {
                    return TriState.FALSE;
                }

                return TriState.DEFAULT;
            });
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}