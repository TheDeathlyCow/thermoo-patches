package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ThermooPatches.MODID + ".immersive_weathering_config")
public class ImmersiveWeatheringConfig implements ConfigData {

    public int freezingFromIcicleFall = -3000;

}
