package com.github.thedeathlycow.thermoo.patches.friendsandfoes.client;

import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.client.ThermooPatchesModMenu;
import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import com.github.thedeathlycow.thermoo.patches.config.translate.Translate;
import com.github.thedeathlycow.thermoo.patches.friendsandfoes.FriendsAndFoesSettings;
import net.fabricmc.api.ClientModInitializer;

public class FriendsAndFoesPatchClient implements ClientModInitializer {
    public static final String CONFIG_CATEGORY = Translate.mainCategoryKey(FriendsAndFoesSettings.HANDLER);
    public static final String CONFIG_DESC = Translate.descKey(FriendsAndFoesSettings.HANDLER);

    @Override
    public void onInitializeClient() {
        if (IntegratedMod.FRIENDS_AND_FOES.isModLoaded()) {
            ThermooPatchesConfig.BUILD_COMMON.register(builder -> {
                builder.accept(ThermooPatchesModMenu.createConfigSubsectionButton(
                        FriendsAndFoesSettings.HANDLER,
                        CONFIG_CATEGORY,
                        CONFIG_DESC
                ));
            });
        }
    }
}