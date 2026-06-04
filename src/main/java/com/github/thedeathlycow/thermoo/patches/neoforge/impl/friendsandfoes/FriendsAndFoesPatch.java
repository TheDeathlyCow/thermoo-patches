package com.github.thedeathlycow.thermoo.patches.neoforge.impl.friendsandfoes;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.world.entity.LivingEntity;

public final class FriendsAndFoesPatch {


    public static void onInitialize() {

    }

    public static FriendsAndFoesSettings getConfigSettings() {
        return FriendsAndFoesSettings.HANDLER.instance();
    }

    public static void freezeFromSlowTargetSpell(LivingEntity victim) {
        FriendsAndFoesSettings settings = getConfigSettings();
        TemperatureAware aware = TemperatureAware.get(victim);

        int temperatureChange = (int) (settings.iceologerSlowTargetTemperatureScaleChange() * aware.thermoo$getMinTemperature());

        aware.thermoo$addTemperature(temperatureChange, HeatingModes.ACTIVE);
    }

    public static void freezeFromTotem(LivingEntity victim) {
        FriendsAndFoesSettings settings = getConfigSettings();
        TemperatureAware aware = TemperatureAware.get(victim);

        int temperatureChange = (int) (settings.freezingTotemTemperatureScaleChange() * aware.thermoo$getMinTemperature());

        aware.thermoo$addTemperature(temperatureChange, HeatingModes.ACTIVE);
    }

    public static void freezeFromIceChunk(LivingEntity victim) {
        FriendsAndFoesSettings settings = getConfigSettings();
        TemperatureAware aware = TemperatureAware.get(victim);

        int temperatureChange = (int) (settings.iceologerIceChunkTemperatureScaleChange() * aware.thermoo$getMinTemperature());

        aware.thermoo$addTemperature(temperatureChange, HeatingModes.ACTIVE);
    }

    private FriendsAndFoesPatch() {

    }
}