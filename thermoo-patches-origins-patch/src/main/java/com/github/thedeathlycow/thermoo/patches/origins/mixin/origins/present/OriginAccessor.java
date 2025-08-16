package com.github.thedeathlycow.thermoo.patches.origins.mixin.origins.present;

import io.github.apace100.apoli.power.PowerReference;
import io.github.apace100.origins.origin.Origin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(value = Origin.class, remap = false)
public interface OriginAccessor {
    @Accessor("powerReferences")
    Set<PowerReference> getPowerReferences();
}
