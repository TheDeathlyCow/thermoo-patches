package com.github.thedeathlycow.thermoo.patches.origins.legacy;

import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.ClassUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;

public class IgnoreHarmfulStatusesPower extends Power {
    public static final SerializableDataType<HolderSet<TemperatureStatus>> TEMPERATURE_STATUS_DATA = holderSet(ThermooRegistries.TEMPERATURE_STATUS);

    private final HolderSet<TemperatureStatus> statuses;

    public IgnoreHarmfulStatusesPower(PowerType<?> type, LivingEntity entity, HolderSet<TemperatureStatus> statuses) {
        super(type, entity);
        this.statuses = statuses;
    }

    public static <T> SerializableDataType<HolderSet<T>> holderSet(ResourceKey<Registry<T>> registryKey
    ) {
        Codec<HolderSet<T>> codec = RegistryCodecs.homogeneousList(registryKey);
        StreamCodec<RegistryFriendlyByteBuf, HolderSet<T>> streamCodec = ByteBufCodecs.holderSet(registryKey);

        return new SerializableDataType<>(
                ClassUtil.castClass(HolderSet.class),
                streamCodec::encode,
                streamCodec::decode,
                (jsonElement, provider) -> codec.decode(
                        RegistryOps.create(JsonOps.INSTANCE, provider),
                        jsonElement
                ).getOrThrow().getFirst()
        );
    }

    public HolderSet<TemperatureStatus> statuses() {
        return statuses;
    }

    public static PowerFactory<Power> createFactory() {
        return new PowerFactory<>(ThermooPatches.id("ignore_harmful_statuses"),
                new SerializableData()
                        .add("statuses", TEMPERATURE_STATUS_DATA),
                data ->
                        (type, player) ->
                                new IgnoreHarmfulStatusesPower(type, player, data.get("statuses")))
                .allowCondition();
    }
}