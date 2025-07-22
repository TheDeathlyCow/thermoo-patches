package com.github.thedeathlycow.thermoo.patches.origins;

import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.origins.mixin.origins.present.OriginAccessor;
import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.origins.origin.Origin;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.Set;

public class OriginsPatch implements ModInitializer {
    @Override
    public void onInitialize() {
        if (IntegratedMod.ORIGINS.isModLoaded()) {
            this.registerOriginsPatches();

            if (IntegratedMod.EXTRA_ORIGINS.isModLoaded()) {
                this.registerExtraOriginsPatches();
            }
        }
    }

    private void registerOriginsPatches() {
        OriginPatchManager.registerPatcher(
                IntegratedMod.ORIGINS.createID("blazeborn"),
                base -> {
                    addPowerReferences(
                            base,
                            ThermooPatches.id("ignores_heat_effects"),
                            ThermooPatches.id("cold_vulnerability")
                    );

                    return base;
                }
        );
    }

    private void registerExtraOriginsPatches() {
        OriginPatchManager.registerPatcher(
                IntegratedMod.EXTRA_ORIGINS.createID("piglin"),
                base -> {
                    addPowerReferences(
                            base,
                            ThermooPatches.id("extra_heat_resistance")
                    );

                    return base;
                }
        );
    }

    private static void addPowerReferences(Origin origin, Identifier... extraPowerReferences) {
        OriginAccessor accessor = (OriginAccessor) origin;
        Set<PowerReference> powerReferences = accessor.getPowerReferences();

        for (Identifier id : extraPowerReferences) {
            powerReferences.add(PowerReference.of(id));
        }
    }
}