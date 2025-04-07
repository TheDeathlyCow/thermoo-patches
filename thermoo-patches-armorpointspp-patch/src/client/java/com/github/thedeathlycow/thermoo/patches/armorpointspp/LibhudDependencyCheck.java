package com.github.thedeathlycow.thermoo.patches.armorpointspp;

import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import net.fabricmc.api.ClientModInitializer;

public class LibhudDependencyCheck implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ThermooPatches.checkMultiDependency(IntegratedMod.ARMOR_POINTS_PP, IntegratedMod.LIBHUD);
    }
}