package com.github.thedeathlycow.thermoo.patches.friendsandfoes.datagen;

import com.github.thedeathlycow.thermoo.patches.config.translate.TranslateGenerator;
import com.github.thedeathlycow.thermoo.patches.friendsandfoes.FriendsAndFoesSettings;
import com.github.thedeathlycow.thermoo.patches.friendsandfoes.client.FriendsAndFoesPatchClient;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class EnglishUSGenerator extends FabricLanguageProvider {
    public EnglishUSGenerator(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        builder.add(FriendsAndFoesPatchClient.CONFIG_CATEGORY, "Friends & Foes");
        builder.add(FriendsAndFoesPatchClient.CONFIG_DESC, "Settings for the Friends & Foes patch");
        TranslateGenerator.generateConfigOptionTranslations(FriendsAndFoesSettings.HANDLER, builder);
    }
}