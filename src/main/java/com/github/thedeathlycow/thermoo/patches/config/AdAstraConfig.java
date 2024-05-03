package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ThermooPatches.MODID + ".ad_astra_config")
public class AdAstraConfig implements ConfigData {


    boolean spaceSuitsBlockPassiveTemperatureOnEarth = false;

    public boolean spaceSuitsBlockPassiveTemperatureOnEarth() {
        return spaceSuitsBlockPassiveTemperatureOnEarth;
    }
}
