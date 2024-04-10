package com.github.thedeathlycow.thermoo.patches.config;


import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = ThermooPatches.MODID)
public class ThermooPatchesConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ArmorPointsPPConfig armorPointsPPConfig = new ArmorPointsPPConfig();

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ImmersiveWeatheringConfig immersiveWeatheringConfig = new ImmersiveWeatheringConfig();

}
