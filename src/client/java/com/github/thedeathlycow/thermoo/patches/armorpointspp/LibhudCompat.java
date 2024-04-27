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
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Arrays;

public class LibhudCompat implements LibhudApi {

    public static final int ORANGE = 0xFFA500;

    private static final int MAX_DISPLAY_HEALTH = 20;

    private static final Vector2i[] heartPositionsPool = new Vector2i[MAX_DISPLAY_HEALTH / 2];

    public static final Component.NamedComponent PLAYER_TEMPERATURE_OVERLAY = Component.named(
            ThermooPatches.id("player_temperature_overlay"),
            LibhudCompat::playerTemperatureOverlay
    );

    public static final Component.NamedComponent MOUNT_TEMPERATURE_OVERLAY = Component.named(
            ThermooPatches.id("mount_temperature_overlay"),
            LibhudCompat::mountTemperatureOverlay
    );

    @Override
    public void onRegisterComponents(RegisterComponentsEvent event) {
        event.registerAbove(VanillaComponents.HEALTH, PLAYER_TEMPERATURE_OVERLAY);
        event.registerAbove(VanillaComponents.VEHICLE_HEALTH, MOUNT_TEMPERATURE_OVERLAY);
    }

    private static void playerTemperatureOverlay(
            LibhudGui gui,
            DrawContext graphics,
            float tickDelta,
            int screenWidth, int screenHeight
    ) {
        PlayerEntity player = getCameraPlayer(false);
        if (player == null) {
            return;
        }

        int baseX = screenWidth / 2 - 91;
        Arrays.fill(heartPositionsPool, null);
        int[] heartYPositions = Components.HEALTH.lastHeartY();
        for (int i = 0; i < heartYPositions.length; i++) {
            heartPositionsPool[i] = new Vector2i(baseX + i * 8, heartYPositions[i]);
        }

        int displayMaxHealth = ApppConfig.instance().bool(IConfig.BooleanOption.HEALTH_BG_ALWAYS_SHOW_10)
                ? MAX_DISPLAY_HEALTH
                : getMaxDisplayHealth(player);

        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                .invoker()
                .render(
                        graphics,
                        player,
                        heartPositionsPool,
                        getDisplayHealth(player),
                        displayMaxHealth
                );
    }

    private static void mountTemperatureOverlay(
            LibhudGui gui,
            DrawContext graphics,
            float tickDelta,
            int screenWidth, int screenHeight
    ) {
        PlayerEntity player = getCameraPlayer(true);
        if (player == null) {
            return;
        }
        LivingEntity mount = getMountEntity(player);
        if (mount == null) {
            return;
        }

        int baseX = screenWidth / 2 + 91;
        int baseY = screenHeight - 39;

        Arrays.fill(heartPositionsPool, null);
        int displayMaxHealth = getMaxDisplayHealth(mount);
        for (int i = 0; i < displayMaxHealth / 2; i++) {
            heartPositionsPool[i] = new Vector2i(baseX - i * 8 - 9, baseY);
        }

        StatusBarOverlayRenderEvents.AFTER_MOUNT_HEALTH_BAR
                .invoker()
                .render(
                        graphics,
                        player,
                        mount,
                        heartPositionsPool,
                        getDisplayHealth(mount),
                        displayMaxHealth
                );
    }

    private static int getMaxDisplayHealth(LivingEntity entity) {
        return Math.min(MathHelper.ceil(entity.getMaxHealth()), MAX_DISPLAY_HEALTH);
    }

    private static int getDisplayHealth(LivingEntity entity) {
        return Math.min(MathHelper.ceil(entity.getHealth()), MAX_DISPLAY_HEALTH);
    }

    @Nullable
    private static PlayerEntity getCameraPlayer(boolean showInCreative) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (!showInCreative && interactionManager != null && !interactionManager.hasStatusBars()) {
            return null;
        }

        Entity entity = client.cameraEntity;
        return entity instanceof PlayerEntity player
                ? player
                : null;
    }

    private static LivingEntity getMountEntity(PlayerEntity player) {
        if (player != null) {
            Entity entity = player.getVehicle();
            if (entity == null) {
                return null;
            }

            if (entity instanceof LivingEntity livingEntity) {
                return livingEntity;
            }
        }

        return null;
    }
}
