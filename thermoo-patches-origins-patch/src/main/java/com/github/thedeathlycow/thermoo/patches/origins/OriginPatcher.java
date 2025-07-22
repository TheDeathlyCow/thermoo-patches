package com.github.thedeathlycow.thermoo.patches.origins;

import io.github.apace100.origins.origin.Origin;

@FunctionalInterface
public interface OriginPatcher {
    Origin patchOrigin(Origin base);
}