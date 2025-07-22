package com.github.thedeathlycow.thermoo.patches.origins.mixin.origins.present;

import com.github.thedeathlycow.thermoo.patches.origins.OriginPatchManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = OriginManager.class, remap = false)
public abstract class OriginManagerMixin {
    @WrapOperation(
            method = "lambda$apply$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/github/apace100/calio/data/SerializableDataType;read(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"
            )
    )
    private static DataResult<Origin> dynamicallyPatchOrigins(
            SerializableDataType<Origin> instance,
            DynamicOps<Object> ops,
            Object input,
            Operation<DataResult<Origin>> original,
            @Local(argsOnly = true) String packName,
            @Local(argsOnly = true) Identifier id
    ) {
        DataResult<Origin> base = original.call(instance, ops, input);

        if (base.isSuccess()) {
            return DataResult.success(OriginPatchManager.patchOrigin(base.getOrThrow(), id, packName));
        }

        return base;
    }
}