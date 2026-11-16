package com.github.thedeathlycow.thermoo.patches.origins.legacy.mixin.origins.present;

import com.github.thedeathlycow.thermoo.patches.origins.legacy.OriginPatchManager;
import com.github.thedeathlycow.thermoo.patches.origins.legacy.OriginsPatch;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OriginManager.class)
public abstract class OriginManagerMixin {
    @WrapOperation(
            method = "lambda$apply$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/github/apace100/origins/origin/Origin;fromJson(Lnet/minecraft/resources/Identifier;Lcom/google/gson/JsonObject;Lnet/minecraft/core/HolderLookup$Provider;)Lio/github/apace100/origins/origin/Origin;"
            )
    )
    private static Origin dynamicallyPatchOrigins(
            Identifier id,
            JsonObject json,
            HolderLookup.Provider provider,
            Operation<Origin> original
    ) {
        Origin base = original.call(id, json, provider);

        if (json.has(OriginsPatch.PACK_NAME_KEY)) {
            return OriginPatchManager.patchOrigin(base, id, json.get(OriginsPatch.PACK_NAME_KEY).getAsString());
        }

        return base;
    }
}