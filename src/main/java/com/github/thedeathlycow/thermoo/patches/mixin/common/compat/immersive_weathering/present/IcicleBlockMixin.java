package com.github.thedeathlycow.thermoo.patches.mixin.common.compat.immersive_weathering.present;

import com.github.thedeathlycow.thermoo.patches.compat.immersiveweathering.IcicleFreezeHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ordana.immersive_weathering.blocks.IcicleBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
        value = IcicleBlock.class
)
public class IcicleBlockMixin {

    @WrapOperation(
            method = "onLandedUpon",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
                    ordinal = 0
            )
    )
    private boolean onLandedUpon(
            Entity instance,
            float fallDistance, float damageMultiplier,
            DamageSource damageSource,
            Operation<Boolean> original
    ) {
        boolean damaged = original.call(instance, fallDistance, damageMultiplier, damageSource);
        if (damaged) {
            IcicleFreezeHandler.applyIcicleFreezing(instance);
        }
        return damaged;
    }

}
