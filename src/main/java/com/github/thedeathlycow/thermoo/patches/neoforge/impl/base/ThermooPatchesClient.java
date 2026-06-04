package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base;

import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.YumiMods;
import dev.yumi.mc.core.api.entrypoint.client.ClientModInitializer;
import net.minecraft.world.entity.EntityType;

public class ThermooPatchesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        if (YumiMods.get().isDevelopmentEnvironment()) {
            StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.register(new DebugHeartOverlay());
        }
    }
}