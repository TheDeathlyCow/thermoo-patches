package com.github.thedeathlycow.thermoo.patches.sereneseasons;

import com.github.thedeathlycow.thermoo.api.season.v2.TemperateSeason;
import com.github.thedeathlycow.thermoo.api.season.v2.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.v2.ThermooSeasonState;
import com.github.thedeathlycow.thermoo.api.season.v2.TropicalSeason;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import dev.yumi.commons.TriState;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.season.SeasonHooks;

import java.util.Optional;

public class SereneSeasonsProvider implements ModInitializer {

    @Override
    public void onInitialize() {
        if (IntegratedMod.SERENE_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register((level, pos) -> {
                ISeasonState sereneSeasonState = SeasonHelper.getSeasonState(level);

                return Optional.ofNullable(switch (sereneSeasonState.getSeason()) {
                    case SPRING -> ThermooSeasonState.of(TemperateSeason.SPRING);
                    case SUMMER -> ThermooSeasonState.of(TemperateSeason.SUMMER);
                    case AUTUMN -> ThermooSeasonState.of(TemperateSeason.AUTUMN);
                    case WINTER -> ThermooSeasonState.of(TemperateSeason.WINTER);
                    default -> null;
                });
            });

            ThermooSeasonEvents.GET_CURRENT_TROPICAL_SEASON.register((level, pos) -> {
                Holder<Biome> biome = level.getBiomeManager().getNoiseBiomeAtPosition(pos);
                if (SeasonHelper.usesTropicalSeasons(biome)) {
                    ISeasonState sereneSeasonState = SeasonHelper.getSeasonState(level);

                    return Optional.of(switch (sereneSeasonState.getTropicalSeason()) {
                        case MID_DRY -> ThermooSeasonState.of(TropicalSeason.DRY);
                        case MID_WET -> ThermooSeasonState.of(TropicalSeason.WET);
                        default -> ThermooSeasonState.of(TropicalSeason.MILD);
                    });
                }

                return Optional.empty();
            });

            ThermooSeasonEvents.IS_COLD_ENOUGH_TO_SNOW.register((level, pos, biomeHolder) -> {
                Biome.Precipitation precip = SeasonHooks.getPrecipitationAtSeasonal(level, biomeHolder, pos, level.getSeaLevel());
                return TriState.from(precip == Biome.Precipitation.SNOW);
            });
        }
    }
}
