package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.environment.v2.provider.EnvironmentProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.exodusstudio.stellaris.common.utils.OxygenUtils;

public record HasOxygenEnvironmentProvider(
        Holder<EnvironmentProvider> inside,
        Holder<EnvironmentProvider> outside
) implements EnvironmentProvider {
    public static final MapCodec<HasOxygenEnvironmentProvider> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    EnvironmentProvider.HOLDER_CODEC
                            .fieldOf("inside")
                            .forGetter(HasOxygenEnvironmentProvider::inside),
                    EnvironmentProvider.HOLDER_CODEC
                            .fieldOf("outside")
                            .forGetter(HasOxygenEnvironmentProvider::outside)
            ).apply(instance, HasOxygenEnvironmentProvider::new)
    );


    @Override
    public void buildCurrentComponents(Level level, BlockPos pos, Holder<Biome> biome, DataComponentMap.Builder builder) {
        if (OxygenUtils.isOxygenated(level, pos)) {
            inside.value().buildCurrentComponents(level, pos, biome, builder);
        } else {
            outside.value().buildCurrentComponents(level, pos, biome, builder);
        }
    }

    @Override
    public MapCodec<? extends HasOxygenEnvironmentProvider> codec() {
        return CODEC;
    }
}