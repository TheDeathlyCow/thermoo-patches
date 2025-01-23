package com.github.thedeathlycow.thermoo.patches.mixin.common.compat.friendsandfoes.present;

import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.github.thedeathlycow.thermoo.patches.compat.friendsandfoes.FriendsAndFoesPatch;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IceologerIceChunkEntity.class)
public class IceologerIceChunkEntityMixin {
    @WrapOperation(
            method = "damage(Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;canFreeze()Z"
            )
    )
    private boolean applyFreezing(LivingEntity instance, Operation<Boolean> original) {
        return instance.thermoo$canFreeze();
    }

    @WrapOperation(
            method = "damage(Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private void applyFreezing(LivingEntity instance, int value, Operation<Void> original) {
        FriendsAndFoesPatch.freezeFromIceChunk(instance);
        original.call(instance, 0);
    }
}