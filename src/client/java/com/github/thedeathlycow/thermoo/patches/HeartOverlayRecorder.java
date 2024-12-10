package com.github.thedeathlycow.thermoo.patches;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;

import java.util.Arrays;

public class HeartOverlayRecorder {

    public static final HeartOverlayRecorder INSTANCE = new HeartOverlayRecorder();

    private static final int MAX_OVERLAY_HEARTS = 20;

    private final Vector2i[] heartPositions = Util.make(() -> {
        var positions = new Vector2i[MAX_OVERLAY_HEARTS];
        Arrays.fill(positions, null);
        return positions;
    });

    public void setHeartPosition(int index, int heartX, int heartY) {
        if (index < heartPositions.length) {
            heartPositions[index] = new Vector2i(heartX, heartY);
        }
    }

    public Vector2i[] getHeartPositions() {
        return heartPositions;
    }

    /**
     * used with overflowing bars because they render their health bar twice for some reason?
     *
     * @param drawContext draw context
     */
    public void invokeAfterHealthBar(DrawContext drawContext) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        int maxDisplayHealth = Math.min(MathHelper.ceil(player.getMaxHealth()), 20);
        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker()
                .render(
                        drawContext,
                        player,
                        HeartOverlayRecorder.INSTANCE.getHeartPositions(),
                        MathHelper.ceil(player.getHealth()),
                        maxDisplayHealth
                );
        Arrays.fill(HeartOverlayRecorder.INSTANCE.getHeartPositions(), null);
    }

    private HeartOverlayRecorder() {

    }

}
