package com.github.thedeathlycow.thermoo.patches.fabricseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasons;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
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
                            case WINTER -> ThermooSeasons.WINTER;
                            case SUMMER -> ThermooSeasons.SUMMER;
                            case FALL -> ThermooSeasons.AUTUMN;
                            case SPRING -> ThermooSeasons.SPRING;
                            default -> null;
                        }
                );
            });
        }
    }

    private FabricSeasonsProvider() {

    }
}
