package com.github.thedeathlycow.thermoo.patches.libhud;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import dev.cheos.libhud.LibhudGui;
import dev.cheos.libhud.VanillaComponents;
import dev.cheos.libhud.api.Component;
import dev.cheos.libhud.api.LibhudApi;
import dev.cheos.libhud.api.event.RegisterComponentsEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Util;
import org.joml.Vector2i;

import java.util.Arrays;

public class LibhudCompat implements LibhudApi {

    public static final Component.NamedComponent TEMPERATURE_OVERLAY_CAPTURE = Component.named(
            ThermooPatches.id("temperature_overlay_capture"),
            PlayerHealthEventInvoker.INSTANCE::capturePlayerHealth
    );

    public static final Component.NamedComponent TEMPERATURE_OVERLAY_CALLBACK = Component.named(
            ThermooPatches.id("temperature_overlay_callback"),
            PlayerHealthEventInvoker.INSTANCE::callbackPlayerHealth
    );

    @Override
    public void onRegisterComponents(RegisterComponentsEvent event) {
        event.registerAbove(VanillaComponents.HEALTH, TEMPERATURE_OVERLAY_CAPTURE);
        event.registerAbove(TEMPERATURE_OVERLAY_CAPTURE, TEMPERATURE_OVERLAY_CALLBACK);
    }

    public static class PlayerHealthEventInvoker {

        public static final PlayerHealthEventInvoker INSTANCE = new PlayerHealthEventInvoker();

        private static final int MAX_DISPLAY_HEALTH = 20;

        private final Vector2i[] positions = Util.make(
                new Vector2i[MAX_DISPLAY_HEALTH],
                pos -> Arrays.fill(pos, null)
        );

        private int index = 0;

        private void capturePlayerHealth(
                LibhudGui gui,
                DrawContext graphics,
                float partialTicks,
                int screenWidth, int screenHeight
        ) {
            if (index < positions.length) {
                positions[index] = new Vector2i(gui.leftOffset, screenHeight - gui.rightOffset);
                ThermooPatches.LOGGER.info("capture heart {}", positions[index]);
                index++;
            }
        }

        private void callbackPlayerHealth(
                LibhudGui gui,
                DrawContext graphics,
                float partialTicks,
                int screenWidth, int screenHeight
        ) {
            ThermooPatches.LOGGER.info("invoke after health bar");
            StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                    .invoker()
                    .render(
                            graphics,
                            MinecraftClient.getInstance().player,
                            positions,
                            20,
                            MAX_DISPLAY_HEALTH
                    );
            Arrays.fill(positions, null);
            index = 0;
        }

        private PlayerHealthEventInvoker() {

        }
    }

}
