package com.github.thedeathlycow.thermoo.patches.friendsandfoes;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public class FriendsAndFoesPatch implements ModInitializer {
    public static final String MODID = ThermooPatches.MODID + "-friendsandfoes-patch";

    @Override
    public void onInitialize() {
        if (IntegratedMod.FRIENDS_AND_FOES.isModLoaded()) {
            Identifier phase = ThermooPatches.id("override");
            var maxTemperature = ThermooAttributes.baseValueEvent(ThermooAttributes.MAX_TEMPERATURE);
            maxTemperature.addPhaseOrdering(Event.DEFAULT_PHASE, phase);
            maxTemperature.register(
                    phase,
                    (entity, baseValue) -> {
                        if (entity.getType() == FriendsAndFoesEntityTypes.ICEOLOGER.get()) {
                            return 0;
                        }
                        return baseValue;
                    }
            );


            var minTemperature = ThermooAttributes.baseValueEvent(ThermooAttributes.MIN_TEMPERATURE);
            minTemperature.addPhaseOrdering(Event.DEFAULT_PHASE, phase);
            minTemperature.register(
                    phase,
                    (entity, baseValue) -> {
                        if (entity.getType() == FriendsAndFoesEntityTypes.WILDFIRE.get()) {
                            return 0;
                        }
                        return baseValue;
                    }
            );
        }
    }

    public static FriendsAndFoesSettings getConfigSettings() {
        return FriendsAndFoesSettings.HANDLER.instance();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static void freezeFromTotem(LivingEntity victim) {
        int temperatureChange = (int) (getConfigSettings().freezingTotemTemperatureScaleChange() * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange, victim.level().thermoo$temperatureSources().active());
    }

    public static void freezeFromIceChunk(LivingEntity victim) {
        int temperatureChange = (int) (getConfigSettings().iceologerIceChunkTemperatureScaleChange() * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange, victim.level().thermoo$temperatureSources().active());
    }

    public static void freezeFromSlowTargetSpell(LivingEntity victim) {
        int temperatureChange = (int) (getConfigSettings().iceologerSlowTargetTemperatureScaleChange() * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange, victim.level().thermoo$temperatureSources().active());
    }
}