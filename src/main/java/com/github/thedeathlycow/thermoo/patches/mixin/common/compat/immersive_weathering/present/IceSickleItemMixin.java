package com.github.thedeathlycow.thermoo.patches.mixin.common.compat.immersive_weathering.present;

import com.github.thedeathlycow.thermoo.patches.immersiveweathering.IcicleFreezeHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ordana.immersive_weathering.items.IceSickleItem;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
        value = IceSickleItem.class,
        remap = false
)
public class IceSickleItemMixin {

    @WrapOperation(
            method = "finishUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private void convertFrozenTicksToTemperature(LivingEntity instance, int frozenTicks, Operation<Void> original) {
        IcicleFreezeHandler.consumeIcicle(instance);
    }

}
