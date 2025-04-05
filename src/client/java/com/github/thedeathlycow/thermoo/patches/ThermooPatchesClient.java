package com.github.thedeathlycow.thermoo.patches;

import com.github.thedeathlycow.thermoo.patches.client.HeartOverlayRecorder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ThermooPatchesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (IntegratedMod.OVERFLOWING_BARS.isModLoaded()) {
            HudRenderCallback.EVENT.register(
                    (drawContext, tickCounter) -> {
                        HeartOverlayRecorder.INSTANCE.invokeAfterHealthBar(drawContext);
                    }
            );
        }
    }
}