package com.github.thedeathlycow.thermoo.patches.origins.legacy.mixin.origins.present;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.origins.legacy.OriginsPatch;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.apace100.calio.data.MultiJsonDataLoader;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.Reader;

@Mixin(MultiJsonDataLoader.class)
public class MultiJsonDataLoaderMixin {
    @WrapOperation(
            method = "lambda$prepare$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/GsonHelper;fromJson(Lcom/google/gson/Gson;Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"
            )
    )
    private <T> T injectPackIdToJsonElement(
            Gson jsonReader,
            Reader result,
            Class<T> e,
            Operation<T> original,
            @Local(argsOnly = true) Resource resource
    ) {
        T loaded = original.call(jsonReader, result, e);

        if (loaded instanceof JsonObject json) {
            json.addProperty(OriginsPatch.PACK_NAME_KEY, resource.sourcePackId());
        }

        return loaded;
    }
}