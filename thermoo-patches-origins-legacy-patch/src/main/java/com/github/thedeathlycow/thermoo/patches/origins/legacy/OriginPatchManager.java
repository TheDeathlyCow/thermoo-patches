package com.github.thedeathlycow.thermoo.patches.origins.legacy;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import io.github.apace100.origins.origin.Origin;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class OriginPatchManager {
    // take care to not mess with custom datapacks, if possible
    private static final Set<String> BUILTIN_PACK_NAMES = Set.of("origins", "extraorigins");
    private static final Map<Identifier, Patcher> PATCHERS = new HashMap<>();

    public static Origin patchOrigin(Origin origin, Identifier originID, String packName) {
        if (!BUILTIN_PACK_NAMES.contains(packName)) {
            return origin;
        }

        Patcher patcher = PATCHERS.get(originID);

        if (patcher != null) {
            ThermooPatches.LOGGER.info("Patching origin {} from pack {} to work with Thermoo", originID, packName);
            return patcher.patchOrigin(origin);
        }

        return origin;
    }

    public static void registerPatcher(Identifier originID, Patcher patcher) {
        if (PATCHERS.put(originID, patcher) != null) {
            ThermooPatches.LOGGER.warn("Overridding existing origin patcher for origin {}", originID);
        }
    }

    @FunctionalInterface
    public interface Patcher {
        Origin patchOrigin(Origin base);
    }

    private OriginPatchManager() {

    }
}