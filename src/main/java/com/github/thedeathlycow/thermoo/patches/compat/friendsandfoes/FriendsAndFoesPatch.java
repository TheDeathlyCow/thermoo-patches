package com.github.thedeathlycow.thermoo.patches.compat.friendsandfoes;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.FriendsAndFoesConfig;
import net.minecraft.entity.LivingEntity;

public final class FriendsAndFoesPatch {
    public static void freezeFromTotem(LivingEntity victim) {
        FriendsAndFoesConfig config = ThermooPatches.getConfig().friendsAndFoesConfig;

        int temperatureChange = (int) (config.freezingTotemFreezingScaleChange * victim.thermoo$getMinTemperature());

        victim.thermoo$addTemperature(temperatureChange);
    }

    private FriendsAndFoesPatch() {

    }
}