package com.github.thedeathlycow.thermoo.patches.origins;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import io.github.apace100.origins.origin.Origin;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class OriginPatchManager {
    // take care to not mess with custom datapacks, if possible
    // plan to expand later with origins addons, hence using a set with 1 item
    private static final Set<String> BUILTIN_PACK_NAMES = Set.of("origins");
    private static final Map<Identifier, OriginPatcher> PATCHERS = new HashMap<>();

    public static Origin patchOrigin(Origin origin, Identifier originID, String packName) {
        if (!BUILTIN_PACK_NAMES.contains(packName)) {
            return origin;
        }

        OriginPatcher patcher = PATCHERS.get(originID);

        if (patcher != null) {
            ThermooPatches.LOGGER.info("Patching origin {} from pack {}", originID, packName);
            return patcher.patchOrigin(origin);
        }

        return origin;
    }

    public static void registerPatcher(Identifier originID, OriginPatcher patcher) {
        if (PATCHERS.put(originID, patcher) != null) {
            ThermooPatches.LOGGER.warn("Overridding existing origin patcher for origin {}", originID);
        }
    }

    private OriginPatchManager() {

    }
}