package com.github.thedeathlycow.thermoo.patches;

import java.util.Arrays;

/**
 * Used to express that multiple mods are (optionally) required at the same time, because fabric.mod.json doesn't have
 * that functionality
 */
public class MultiDependencyException extends RuntimeException {

    public MultiDependencyException(ThermooPatches.IntegratedMod[] requiredMods) {
        super(buildMessage(requiredMods));
    }

    private static String buildMessage(ThermooPatches.IntegratedMod[] requiredMods) {
        var stringBuilder = new StringBuilder("Sorry, you must install ALL of the following mods in order for them to work with Thermoo Patches: ");
        Arrays.stream(requiredMods).forEach(mod -> {
            stringBuilder.append("\n - ");
            stringBuilder.append(mod);
        });
        return stringBuilder.toString();
    }

}
