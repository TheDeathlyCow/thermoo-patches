package com.github.thedeathlycow.thermoo.patches;

import com.github.thedeathlycow.thermoo.patches.colorfulhearts.ColorfulHeartsEventListeners;
import net.fabricmc.api.ClientModInitializer;

public class ThermooPatchesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (IntegratedMod.COLORFUL_HEARTS.isModLoaded()) {
            ColorfulHeartsEventListeners.register();
        }
    }
}