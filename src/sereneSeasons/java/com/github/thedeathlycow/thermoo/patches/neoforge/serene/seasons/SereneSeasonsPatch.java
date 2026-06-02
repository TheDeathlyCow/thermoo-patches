package com.github.thedeathlycow.thermoo.patches.neoforge.serene.seasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.patches.neoforge.IntegratedMod;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

import java.util.Optional;

public class SereneSeasonsPatch implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
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
                Holder<Biome> biome = world.getBiome(pos);
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