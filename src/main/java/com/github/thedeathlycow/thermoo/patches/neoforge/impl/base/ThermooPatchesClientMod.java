package com.github.thedeathlycow.thermoo.patches.neoforge.impl.base;

import com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.config.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ThermooPatches.MOD_ID, dist = Dist.CLIENT)
public class ThermooPatchesClientMod {
    public ThermooPatchesClientMod(ModContainer mod) {
        mod.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> {
            return ConfigScreen.get(parent);
        });
    }
}