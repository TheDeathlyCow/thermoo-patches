package com.github.thedeathlycow.thermoo.patches.colorfulhearts;

import com.github.thedeathlycow.thermoo.api.client.v1.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.impl.client.HeartBarContextImpl;
import net.minecraft.util.Mth;
import org.joml.Vector2i;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SequencedCollection;

public class ColorfulHeartsEventListeners implements ColorfulHeartsApi {
    private static final ThreadLocal<SequencedCollection<Vector2i>> HEART_POSITIONS = ThreadLocal.withInitial(ArrayList::new);

    public ColorfulHeartsEventListeners() {
        FabHeartEvents.SINGLE_RENDER.register(event -> {
            HEART_POSITIONS.get().add(new Vector2i(event.getX(), event.getY()));
        });

        FabHeartEvents.POST_RENDER.register(event -> {
            SequencedCollection<Vector2i> heartPositions = HEART_POSITIONS.get();
            if (heartPositions == null) {
                return;
            }

            var heartBarContext = new HeartBarContextImpl(
                    Collections.unmodifiableSequencedCollection(heartPositions),
                    event.getDisplayHealth(),
                    Math.min(20, Mth.ceil(event.getMaxHealth()))
            );

            HEART_POSITIONS.remove();

            StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker().render(event.getGuiGraphics(), event.getPlayer(), heartBarContext);
        });
    }
}