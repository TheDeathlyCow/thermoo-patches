package com.github.thedeathlycow.thermoo.patches.sereneseasons;

import com.github.thedeathlycow.thermoo.api.season.*;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

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
                RegistryEntry<Biome> biome = level.getBiomeAccess().getBiomeForNoiseGen(pos);
                if (SeasonHelper.usesTropicalSeasons(biome)) {
                    ISeasonState sereneSeasonState = SeasonHelper.getSeasonState(level);

                    return Optional.of(switch (sereneSeasonState.getTropicalSeason()) {
                        case MID_DRY -> ThermooSeasonState.of(TropicalSeason.DRY);
                        case MID_WET -> ThermooSeasonState.of(TropicalSeason.WET);
                        default ->  ThermooSeasonState.of(TropicalSeason.MILD);
                    });
                }

                return Optional.empty();
            });
        }
    }
}
