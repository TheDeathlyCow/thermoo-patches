package com.github.thedeathlycow.thermoo.patches.homeostaticseasons;

import com.github.thedeathlycow.thermoo.api.season.v2.TemperateSeason;
import com.github.thedeathlycow.thermoo.api.season.v2.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.v2.ThermooSeasonState;
import com.github.thedeathlycow.thermoo.api.season.v2.TropicalSeason;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import homeostaticseasons.common.biome.BiomeColormap;
import homeostaticseasons.common.biome.BiomeColormapManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class HomeostaticSeasonsProvider implements ModInitializer {
    @Override
    public void onInitialize() {
        if (IntegratedMod.HOMEOSTATIC_SEASONS.isModLoaded()) {
            ThermooSeasonEvents.GET_CURRENT_SEASON.register((level, _) -> {
                Season homeostaticSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);

                return Optional.ofNullable(switch (homeostaticSeason) {
                    case EARLY_SPRING, MID_SPRING, LATE_SPRING -> ThermooSeasonState.of(TemperateSeason.SPRING);
                    case EARLY_SUMMER, MID_SUMMER, LATE_SUMMER -> ThermooSeasonState.of(TemperateSeason.SUMMER);
                    case EARLY_AUTUMN, MID_AUTUMN, LATE_AUTUMN -> ThermooSeasonState.of(TemperateSeason.AUTUMN);
                    case EARLY_WINTER, MID_WINTER, LATE_WINTER -> ThermooSeasonState.of(TemperateSeason.WINTER);
                    case null, default -> null;
                });
            });

            ThermooSeasonEvents.GET_CURRENT_TROPICAL_SEASON.register((level, pos) -> {
                Holder<Biome> biome = level.getBiomeManager().getNoiseBiomeAtPosition(pos);
                BiomeColormap.ColormapType colormapType = BiomeColormapManager.getColormapType(biome);

                if (colormapType == BiomeColormap.ColormapType.TEMPERATE) {
                    Season homeostaticSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);

                    if (homeostaticSeason != null && homeostaticSeason.isWetSeason()) {
                        return Optional.of(ThermooSeasonState.of(TropicalSeason.WET));
                    }
                }

                return Optional.empty();
            });
        }
    }
}