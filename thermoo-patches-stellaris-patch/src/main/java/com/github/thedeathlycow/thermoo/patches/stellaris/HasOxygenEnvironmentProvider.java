package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProvider;
import com.github.thedeathlycow.thermoo.api.environment.provider.EnvironmentProviderType;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.st0x0ef.stellaris.common.oxygen.DimensionOxygenManager;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import net.minecraft.component.ComponentMap;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public record HasOxygenEnvironmentProvider(
        RegistryEntry<EnvironmentProvider> inside,
        RegistryEntry<EnvironmentProvider> outside
) implements EnvironmentProvider {
    public static final MapCodec<HasOxygenEnvironmentProvider> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    EnvironmentProvider.ENTRY_CODEC
                            .fieldOf("inside")
                            .forGetter(HasOxygenEnvironmentProvider::inside),
                    EnvironmentProvider.ENTRY_CODEC
                            .fieldOf("outside")
                            .forGetter(HasOxygenEnvironmentProvider::outside)
            ).apply(instance, HasOxygenEnvironmentProvider::new)
    );

    @Override
    public void buildCurrentComponents(World world, BlockPos pos, RegistryEntry<Biome> biome, ComponentMap.Builder builder) {
        if (world instanceof ServerWorld serverWorld) {
            DimensionOxygenManager manager = GlobalOxygenManager.getInstance().getOrCreateDimensionManager(serverWorld);
            if (manager.hasOxygenAt(pos)) {
                inside.value().buildCurrentComponents(world, pos, biome, builder);
            } else {
                outside.value().buildCurrentComponents(world, pos, biome, builder);
            }
        }
    }

    @Override
    public EnvironmentProviderType<HasOxygenEnvironmentProvider> getType() {
        return TPEnvironmentProviderTypes.HAS_OXYGEN;
    }
}