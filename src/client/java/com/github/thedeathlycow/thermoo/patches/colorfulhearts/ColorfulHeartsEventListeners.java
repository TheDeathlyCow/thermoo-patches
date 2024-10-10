package com.github.thedeathlycow.thermoo.patches.colorfulhearts;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.HeartOverlayRecorder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;

public class ColorfulHeartsEventListeners implements ColorfulHeartsApi {

    public ColorfulHeartsEventListeners() {
        FabHeartEvents.SINGLE_RENDER.register(event -> {
            HeartOverlayRecorder.INSTANCE.setHeartPosition(event.getIndex(), event.getX(), event.getY());
        });

        FabHeartEvents.POST_RENDER.register(post -> {
            PlayerEntity player = post.getPlayer();
            int displayHealth = post.getDisplayHealth();

            Vector2i[] positions = HeartOverlayRecorder.INSTANCE.getHeartPositions();
            int maxDisplayHealth = Math.min(MathHelper.ceil(player.getMaxHealth()), 20);

            StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                    .invoker()
                    .render(
                            post.getGuiGraphics(),
                            player,
                            positions,
                            displayHealth,
                            maxDisplayHealth
                    );
        });
    }

}
