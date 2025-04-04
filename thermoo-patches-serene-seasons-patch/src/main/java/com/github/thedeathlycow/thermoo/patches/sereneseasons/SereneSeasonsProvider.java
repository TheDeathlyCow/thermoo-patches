package com.github.thedeathlycow.thermoo.patches.sereneseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Optional;

public class SereneSeasonsProvider implements ModInitializer {
    @Override
    public void onInitialize() {
        if (IntegratedMod.SERENE_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register(world -> {
                Season sereneSeason = SeasonHelper.getSeasonState(world)
                        .getSeason();

                return Optional.ofNullable(switch (sereneSeason) {
                    case SPRING -> ThermooSeason.SPRING;
                    case SUMMER -> ThermooSeason.SUMMER;
                    case AUTUMN -> ThermooSeason.AUTUMN;
                    case WINTER -> ThermooSeason.WINTER;
                    default -> null;
                });
            });

            ThermooSeasonEvents.GET_CURRENT_TROPICAL_SEASON.register((world, pos) -> {
                RegistryEntry<Biome> biome = world.getBiome(pos);
                if (SeasonHelper.usesTropicalSeasons(biome)) {
                    Season.TropicalSeason tropicalSeason = SeasonHelper.getSeasonState(world)
                            .getTropicalSeason();

                    return Optional.ofNullable(switch (tropicalSeason) {
                        case EARLY_DRY, MID_DRY, LATE_DRY -> ThermooSeason.TROPICAL_DRY;
                        case EARLY_WET, MID_WET, LATE_WET -> ThermooSeason.TROPICAL_WET;
                        default -> null;
                    });
                }

                return Optional.empty();
            });
        }
    }
}
