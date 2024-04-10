package com.github.thedeathlycow.thermoo.patches.armorpointspp;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import dev.cheos.armorpointspp.core.render.Components;
import dev.cheos.libhud.LibhudGui;
import dev.cheos.libhud.VanillaComponents;
import dev.cheos.libhud.api.Component;
import dev.cheos.libhud.api.LibhudApi;
import dev.cheos.libhud.api.event.RegisterComponentsEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

public class LibhudCompat implements LibhudApi {

    public static final Component.NamedComponent TEMPERATURE_OVERLAY = Component.named(
            ThermooPatches.id("temperature_overlay"),
            LibhudCompat::capturePlayerHealth
    );

    @Override
    public void onRegisterComponents(RegisterComponentsEvent event) {
        event.registerAbove(VanillaComponents.HEALTH, TEMPERATURE_OVERLAY);
    }

    private static void capturePlayerHealth(
            LibhudGui gui,
            DrawContext graphics,
            float partialTicks,
            int screenWidth, int screenHeight
    ) {
        int baseX = screenWidth / 2 - 91;
        int[] heartYPositions = Components.HEALTH.lastHeartY();
        var positions = new Vector2i[heartYPositions.length];
        for (int i = 0; i < heartYPositions.length; i++) {
            positions[i] = new Vector2i(baseX + i * 8, heartYPositions[i]);
        }

        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                .invoker()
                .render(
                        graphics,
                        MinecraftClient.getInstance().player,
                        positions,
                        20,
                        20
                );
    }

}
