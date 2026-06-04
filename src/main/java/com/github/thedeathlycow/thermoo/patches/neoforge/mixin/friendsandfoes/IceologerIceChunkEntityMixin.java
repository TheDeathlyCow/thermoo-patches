package com.github.thedeathlycow.thermoo.patches.neoforge.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.compat.RequiresMods;
import com.github.thedeathlycow.thermoo.patches.neoforge.impl.friendsandfoes.FriendsAndFoesPatch;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@RequiresMods("friendsandfoes")
@Mixin(IceologerIceChunkEntity.class)
public class IceologerIceChunkEntityMixin {
    @WrapOperation(
            method = "damage(Lnet/minecraft/world/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;canFreeze()Z"
            )
    )
    private boolean applyFreezing(LivingEntity instance, Operation<Boolean> original) {
        return TemperatureAware.get(instance).thermoo$canFreeze();
    }

    @WrapOperation(
            method = "damage(Lnet/minecraft/world/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V"
            )
    )
    private void applyFreezing(LivingEntity instance, int value, Operation<Void> original) {
        FriendsAndFoesPatch.freezeFromIceChunk(instance);
        original.call(instance, 0);
    }
}