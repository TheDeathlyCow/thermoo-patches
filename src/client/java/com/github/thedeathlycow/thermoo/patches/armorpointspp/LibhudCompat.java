package com.github.thedeathlycow.thermoo.patches.armorpointspp;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import dev.cheos.armorpointspp.config.ApppConfig;
import dev.cheos.armorpointspp.core.adapter.IConfig;
import dev.cheos.armorpointspp.core.render.Components;
import dev.cheos.libhud.LibhudGui;
import dev.cheos.libhud.VanillaComponents;
import dev.cheos.libhud.api.Component;
import dev.cheos.libhud.api.LibhudApi;
import dev.cheos.libhud.api.event.RegisterComponentsEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;

public class LibhudCompat implements LibhudApi {

    private static final Vector2i[] heartPositionsPool = new Vector2i[10];

    private static final int MAX_DISPLAY_HEALTH = 20;

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
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }

        int baseX = screenWidth / 2 - 91;
        int[] heartYPositions = Components.HEALTH.lastHeartY();
        for (int i = 0; i < heartYPositions.length; i++) {
            heartPositionsPool[i] = new Vector2i(baseX + i * 8, heartYPositions[i]);
        }

        int displayMaxHealth = ApppConfig.instance().bool(IConfig.BooleanOption.HEALTH_BG_ALWAYS_SHOW_10)
                ? MAX_DISPLAY_HEALTH
                : Math.min(MathHelper.ceil(player.getMaxHealth()), MAX_DISPLAY_HEALTH);

        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                .invoker()
                .render(
                        graphics,
                        player,
                        heartPositionsPool,
                        Math.min(MathHelper.ceil(player.getHealth()), MAX_DISPLAY_HEALTH),
                        displayMaxHealth
                );
    }

}
