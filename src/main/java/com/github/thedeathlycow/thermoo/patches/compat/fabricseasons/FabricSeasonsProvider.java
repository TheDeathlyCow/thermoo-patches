package com.github.thedeathlycow.thermoo.patches.compat.fabricseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;

import java.util.Optional;

public class FabricSeasonsProvider {

    public static void registerSeasonProviderEvent() {
        if (IntegratedMod.FABRIC_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register(world -> {
                Season fabricSeason = FabricSeasons.getCurrentSeason(world);
                return Optional.ofNullable(
                        switch (fabricSeason) {
                            case WINTER -> ThermooSeason.WINTER;
                            case SUMMER -> ThermooSeason.SUMMER;
                            case FALL -> ThermooSeason.AUTUMN;
                            case SPRING -> ThermooSeason.SPRING;
                            default -> null;
                        }
                );
            });
        }
    }

    private FabricSeasonsProvider() {

    }
}
