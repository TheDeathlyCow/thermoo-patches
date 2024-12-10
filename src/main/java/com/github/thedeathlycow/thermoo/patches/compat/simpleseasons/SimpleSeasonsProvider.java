package com.github.thedeathlycow.thermoo.patches.compat.simpleseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import io.github.steveplays28.simpleseasons.api.SimpleSeasonsApi;
import io.github.steveplays28.simpleseasons.state.world.SeasonTracker;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public final class SimpleSeasonsProvider {

    public static void registerSeasonProviderEvent() {
        if (IntegratedMod.SIMPLE_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register(world -> {
                if (!SimpleSeasonsApi.worldHasSeasons(world)) {
                    return Optional.empty();
                }

                SeasonTracker.Seasons season = SimpleSeasonsApi.getSeason(world);
                return Optional.ofNullable(
                        switch (season) {
                            case SUMMER -> ThermooSeason.SUMMER;
                            case WINTER -> ThermooSeason.WINTER;
                            case FALL -> ThermooSeason.AUTUMN;
                            case SPRING -> ThermooSeason.SPRING;
                            default -> null;
                        }
                );
            });

            ThermooSeasonEvents.GET_CURRENT_TROPICAL_SEASON.register((world, pos) -> {
                RegistryEntry<Biome> biome = world.getBiome(pos);
                if (SimpleSeasonsApi.biomeHasWetAndDrySeasons(biome)) {
                    SeasonTracker.Seasons season = SimpleSeasonsApi.getSeason(world);

                    return Optional.ofNullable(
                            switch (season) {
                                case SUMMER, SPRING -> ThermooSeason.TROPICAL_WET;
                                case WINTER, FALL -> ThermooSeason.TROPICAL_DRY;
                                default -> null;
                            }
                    );
                }

                return Optional.empty();
            });
        }
    }

    private SimpleSeasonsProvider() {

    }
}