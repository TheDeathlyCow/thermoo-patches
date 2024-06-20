package com.github.thedeathlycow.thermoo.patches;

import net.minecraft.util.Util;
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

    private HeartOverlayRecorder() {

    }

}
