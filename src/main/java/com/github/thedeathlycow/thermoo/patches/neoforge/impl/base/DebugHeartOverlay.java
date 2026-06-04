package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public final class DebugHeartOverlay implements StatusBarOverlayRenderEvents.RenderHealthBarCallback {
    @Override
    public void render(
            GuiGraphics graphics,
            Player player,
            Vector2i[] heartPositions,
            int displayHealth, int maxDisplayHealth
    ) {
        if (TemperatureAware.get(player).thermoo$isWarm()) {
            return;
        }

        int halfHearts = getHalfHearts(player, maxDisplayHealth);
        int fullHearts = getFullHearts(halfHearts);
        for (int i = 0; i < fullHearts; i++) {
            Vector2i pos = heartPositions[i];
            if (pos == null) {
                continue;
            }
            // is half heart if this is the last heart being rendered and we have an odd
            // number of frozen health points
            boolean isHalfHeart = i + 1 >= fullHearts && (halfHearts & 1) == 1; // is odd check
            graphics.blitSprite(
                    Gui.HeartType.FROZEN.getSprite(false, isHalfHeart, false),
                    pos.x, pos.y,
                    9, 9
            );
        }
    }

    static int getHalfHearts(@NotNull LivingEntity entity, int maxDisplayHealth) {
        float progress = -TemperatureAware.get(entity).thermoo$getTemperatureScale();
        return Math.round(progress * maxDisplayHealth);
    }

    static int getFullHearts(int halfHearts) {
        // number of whole hearts
        return Mth.ceil(halfHearts / 2.0f);
    }
}