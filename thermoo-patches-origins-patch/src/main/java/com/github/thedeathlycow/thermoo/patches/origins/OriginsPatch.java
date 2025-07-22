package com.github.thedeathlycow.thermoo.patches.origins;

import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.origins.mixin.origins.present.OriginAccessor;
import io.github.apace100.apoli.power.PowerReference;
import net.fabricmc.api.ModInitializer;

import java.util.Set;

public class OriginsPatch implements ModInitializer {
    @Override
    public void onInitialize() {
        if (IntegratedMod.ORIGINS.isModLoaded()) {
            this.registerOriginsPatches();
        }
    }

    private void registerOriginsPatches() {
        OriginPatchManager.registerPatcher(
                IntegratedMod.ORIGINS.createID("blazeborn"),
                base -> {
                    OriginAccessor accessor = (OriginAccessor) base;
                    Set<PowerReference> powerReferences = accessor.getPowerReferences();

                    powerReferences.add(PowerReference.of(ThermooPatches.id("ignores_heat_effects")));
                    powerReferences.add(PowerReference.of(ThermooPatches.id("cold_vulnerability")));

                    return base;
                }
        );
    }
}