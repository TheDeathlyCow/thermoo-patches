package com.github.thedeathlycow.thermoo.patches.compat.sereneseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasons;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import net.minecraft.item.ItemStack;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

import java.util.Optional;

public final class SereneSeasonsProvider {
    public static void initialize() {
        ItemStack
        if (IntegratedMod.SERENE_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register(world -> {
                ISeasonState state = SeasonHelper.getSeasonState(world);
                return Optional.of(
                        switch (state.getSeason()) {
                            case SUMMER -> ThermooSeasons.SUMMER;
                            case AUTUMN -> ThermooSeasons.AUTUMN;
                            case WINTER -> ThermooSeasons.WINTER;
                            default -> ThermooSeasons.SPRING;
                        }
                );
            });
        }
    }

    private SereneSeasonsProvider() {

    }
}