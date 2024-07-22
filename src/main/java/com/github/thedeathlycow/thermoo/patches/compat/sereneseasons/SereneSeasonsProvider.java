package com.github.thedeathlycow.thermoo.patches.compat.sereneseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Optional;

public class SereneSeasonsProvider {

    public static void registerSeasonProviderEvent() {
        if (IntegratedMod.SERENE_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register(world -> {
                Season sereneSeason = SeasonHelper.getSeasonState(world).getSeason();
                return Optional.ofNullable(switch (sereneSeason) {
                    case SPRING -> ThermooSeason.SPRING;
                    case SUMMER -> ThermooSeason.SUMMER;
                    case AUTUMN -> ThermooSeason.AUTUMN;
                    case WINTER -> ThermooSeason.WINTER;
                    default -> null;
                });
            });
        }
    }

    private SereneSeasonsProvider() {

    }

}
