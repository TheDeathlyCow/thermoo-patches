package com.github.thedeathlycow.thermoo.patches.origins.legacy;

import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatusEvents;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.tag.TemperatureStatusTags;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.origins.legacy.mixin.origins.present.OriginAccessor;
import dev.yumi.commons.TriState;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.origins.origin.Origin;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

import java.util.List;

public class OriginsPatch implements ModInitializer {
    public static final String MODID = ThermooPatches.MODID + "-origins-legacy-patch";
    public static final String PACK_NAME_KEY = MODID + ":pack_name";


    @Override
    public void onInitialize() {
        if (IntegratedMod.ORIGINS.isModLoaded()) {
            ThermooPowerFactories.initialize();

            this.registerOriginsPatches();

            if (IntegratedMod.EXTRA_ORIGINS.isModLoaded()) {
                this.registerExtraOriginsPatches();
            }

            Identifier phase = internalID("default");
            TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.addPhaseOrdering(
                    phase,
                    ThermooPatches.thermooId("default")
            );

            TemperatureStatusEvents.ALLOW_TEMPERATURE_STATUS.register(
                    (entity, statusReference) -> {
                        if (!statusReference.is(TemperatureStatusTags.HARMFUL)) {
                            return TriState.DEFAULT;
                        }

                        for (var power : PowerHolderComponent.getPowers(entity, IgnoreHarmfulStatusesPower.class)) {
                            if ( power.statuses().contains(statusReference)) {
                                return TriState.FALSE;
                            }
                        }

                        return TriState.DEFAULT;
                    }
            );
        }
    }

    private void registerOriginsPatches() {
        OriginPatchManager.registerPatcher(
                IntegratedMod.ORIGINS.createID("blazeborn"),
                base -> {
                    addPowerReferences(
                            base,
                            ThermooPatches.id("ignores_heat_effects"),
                            ThermooPatches.id("cold_vulnerability"),
                            internalID("reduce_min_temperature")
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
        List<PowerType<?>> powerReferences = accessor.getPowerTypes();

        for (Identifier id : extraPowerReferences) {
            powerReferences.add(new PowerTypeReference<>(id));
        }
    }

    public static Identifier internalID(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}