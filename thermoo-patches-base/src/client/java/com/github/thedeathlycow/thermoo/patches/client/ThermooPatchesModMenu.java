package com.github.thedeathlycow.thermoo.patches.client;

import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;

public class ThermooPatchesModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfigClient.getConfigScreen(ThermooPatchesConfig.class, parent).get();
    }

}
