package com.github.thedeathlycow.thermoo.patches.nycto;

import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatusEvents;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.tag.TemperatureStatusTags;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import dev.yumi.commons.TriState;
import moriyashiine.nycto.api.NyctoAPI;
import net.fabricmc.api.ModInitializer;

public class NyctoPatch implements ModInitializer {
    @Override
    public void onInitialize() {
        if (IntegratedMod.NYCTO.isModLoaded()) {
            TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.register((entity, statusReference) -> {
                boolean isColdAndHarmful = statusReference.is(TemperatureStatusTags.COLD)
                        && statusReference.is(TemperatureStatusTags.HARMFUL);

                if (isColdAndHarmful && NyctoAPI.isVampire(entity)) {
                    return TriState.FALSE;
                }

                return TriState.DEFAULT;
            });
        }
    }
}