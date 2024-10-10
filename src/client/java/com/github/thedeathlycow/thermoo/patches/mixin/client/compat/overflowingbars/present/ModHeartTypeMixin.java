package com.github.thedeathlycow.thermoo.patches.mixin.client.compat.overflowingbars.present;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
        targets = "fuzs.overflowingbars.client.gui.HealthBarRenderer$ModHeartType"
)
public class ModHeartTypeMixin {
    @WrapOperation(
            method = "forPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isFrozen()Z"
            )
    )
    private static boolean checkThermooFrozen(PlayerEntity instance, Operation<Boolean> original) {
        int minTemperature = instance.thermoo$getMinTemperature();
        if (minTemperature >= 0) {
            return original.call(instance);
        } else {
            return instance.thermoo$getTemperature() <= minTemperature;
        }
    }
}
