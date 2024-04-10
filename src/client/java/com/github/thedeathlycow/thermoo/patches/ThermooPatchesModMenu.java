package com.github.thedeathlycow.thermoo.patches;

import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ThermooPatchesModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ThermooPatchesConfig.class, parent).get();
    }

}
