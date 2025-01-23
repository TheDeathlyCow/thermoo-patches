package com.github.thedeathlycow.thermoo.patches.compat.friendsandfoes;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.FriendsAndFoesConfig;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public final class FriendsAndFoesPatch {
    public static void registerAttributes() {
        if (IntegratedMod.FRIENDS_AND_FOES.isModLoaded()) {
            Identifier phase = ThermooPatches.id("override");
            Event<ThermooAttributes.SetBaseAttributeValue> maxTemperature = ThermooAttributes.baseValueEvent(ThermooAttributes.MAX_TEMPERATURE);
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


            Event<ThermooAttributes.SetBaseAttributeValue> minTemperature = ThermooAttributes.baseValueEvent(ThermooAttributes.MIN_TEMPERATURE);
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

    public static void freezeFromTotem(LivingEntity victim) {
        FriendsAndFoesConfig config = ThermooPatches.getConfig().friendsAndFoesConfig;

        int temperatureChange = (int) (config.freezingTotemFreezingScaleChange * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange, HeatingModes.ACTIVE);
    }

    public static void freezeFromIceChunk(LivingEntity victim) {
        FriendsAndFoesConfig config = ThermooPatches.getConfig().friendsAndFoesConfig;

        int temperatureChange = (int) (config.iceologerIceChunkFreezingScaleChange * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange, HeatingModes.ACTIVE);
    }

    private FriendsAndFoesPatch() {

    }
}