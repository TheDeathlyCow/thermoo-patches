package com.github.thedeathlycow.thermoo.patches.colorfulhearts;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.HeartOverlayRecorder;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector2i;
import terrails.colorfulhearts.fabric.api.event.FabHeartEvents;

public class ColorfulHeartsEventListeners {

    public static void register() {
        FabHeartEvents.POST_RENDER.register(post -> {
            var recorder = HeartOverlayRecorder.INSTANCE;

            Vector2i[] positions = recorder.getHeartPositions();

            PlayerEntity player = recorder.getPlayer();
            if (player != null) {
                int displayHealth = recorder.getDisplayHealth();

                StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR
                        .invoker()
                        .render(
                                post.getGuiGraphics(),
                                player,
                                positions,
                                displayHealth,
                                20
                        );
            }
        });
    }

    private ColorfulHeartsEventListeners() {

    }

}
