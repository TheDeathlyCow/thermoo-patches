package com.github.thedeathlycow.thermoo.patches.origins;

import com.github.thedeathlycow.thermoo.api.temperature.effects.ConfiguredTemperatureEffect;
import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DisableEffectsPowerType extends PowerType {
    public static final TypedDataObjectFactory<DisableEffectsPowerType> DATA_FACTORY = TypedDataObjectFactory.simple(
            new SerializableData().add("effects", SerializableDataTypes.IDENTIFIERS),
            data -> new DisableEffectsPowerType(data.get("effects")),
            (powerType, serializableData) -> serializableData.instance()
                    .set("effects", powerType.effectIds())
    );

    private final List<Identifier> effects;

    public DisableEffectsPowerType(List<Identifier> effects) {
        this.effects = effects;
    }

    public List<Identifier> effectIds() {
        return effects;
    }

    @Override
    @NotNull
    public PowerConfiguration<DisableEffectsPowerType> getConfig() {
        return ThermooPowerTypes.CONFIGURATION;
    }

    @Override
    public void onAdded() {
        super.onAdded();
        this.setEffectsEnabled(false);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        this.setEffectsEnabled(true);
    }

    private void setEffectsEnabled(boolean value) {
        LivingEntity holder = this.getHolder();

        for (Identifier effect : this.effects) {
            ConfiguredTemperatureEffect.setEffectEnabled(holder, effect, false);
        }
    }
}