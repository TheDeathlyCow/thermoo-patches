package com.github.thedeathlycow.thermoo.patches.origins.legacy.mixin.origins.present;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.origins.origin.Origin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Origin.class)
public interface OriginAccessor {
    @Accessor("powerTypes")
    List<PowerType<?>> getPowerTypes();
}
