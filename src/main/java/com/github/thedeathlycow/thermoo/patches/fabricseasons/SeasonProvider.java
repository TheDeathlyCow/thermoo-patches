package com.github.thedeathlycow.thermoo.patches.fabricseasons;

import com.github.thedeathlycow.thermoo.api.season.ThermooSeasonEvents;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeasons;
import net.minecraft.world.World;

import java.util.Optional;

public class SeasonProvider {

    public static Optional<ThermooSeasons> getCurrentSeason(World world) {
        return Optional.empty();
    }
}
