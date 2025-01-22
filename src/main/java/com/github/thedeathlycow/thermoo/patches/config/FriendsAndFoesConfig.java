package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = ThermooPatches.MODID + ".friendsandfoes_config")
public class FriendsAndFoesConfig implements ConfigData {
    public float freezingTotemFreezingScaleChange = 1.0f;
}