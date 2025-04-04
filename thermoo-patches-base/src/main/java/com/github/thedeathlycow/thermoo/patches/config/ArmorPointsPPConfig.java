package com.github.thedeathlycow.thermoo.patches.config;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ThermooPatches.MODID + ".armorpointspp_config")
public class ArmorPointsPPConfig implements ConfigData {

    @ConfigEntry.ColorPicker
    public int overheatingTextColor = 0xFFA500;

}
