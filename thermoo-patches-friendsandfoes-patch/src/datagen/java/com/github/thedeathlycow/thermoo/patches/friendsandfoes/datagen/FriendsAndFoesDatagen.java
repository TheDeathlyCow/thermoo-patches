package com.github.thedeathlycow.thermoo.patches.friendsandfoes.datagen;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jspecify.annotations.Nullable;

public class FriendsAndFoesDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ThermooPatches.LOGGER.info("Running Friends and Foes Patch datagen");
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(EnglishUSGenerator::new);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return DataGeneratorEntrypoint.super.getEffectiveModId();
    }
}