package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.api.util.TemperatureConverter;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ThermooPatches.MODID + ".ad_astra_config")
public class AdAstraConfig implements ConfigData {

    int hotPlanetTemperaturePerSecond = TemperatureConverter.celsiusToTemperatureTick(70.0) * 20;

    int coldPlanetTemperaturePerSecond = TemperatureConverter.celsiusToTemperatureTick(-50.0) * 20;

    int orbitTemperaturePerSecond = TemperatureConverter.celsiusToTemperatureTick(-50.0) * 20;

    public int getHotPlanetTemperaturePerSecond() {
        return hotPlanetTemperaturePerSecond;
    }

    public int getColdPlanetTemperaturePerSecond() {
        return coldPlanetTemperaturePerSecond;
    }

    public int getOrbitTemperaturePerSecond() {
        return orbitTemperaturePerSecond;
    }
}
