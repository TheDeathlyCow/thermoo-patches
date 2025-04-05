package com.github.thedeathlycow.thermoo.patches.fabricseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class FabricSeasonsProvider implements ModInitializer {
    @Override
    public void onInitialize() {
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

            ThermooSeasonEvents.GET_CURRENT_TROPICAL_SEASON.register((world, pos) -> {
                Biome biome = world.getBiome(pos).value();
                ThermooSeason result = null;
                if (biome.getTemperature() > 0.79f) {
                    result = switch (FabricSeasons.getCurrentSeason(world)) {
                        case SUMMER -> ThermooSeason.TROPICAL_DRY;
                        case WINTER -> ThermooSeason.TROPICAL_WET;
                        default -> null;
                    };
                }
                return Optional.ofNullable(result);
            });
        }
    }
}