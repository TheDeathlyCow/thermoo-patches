package com.github.thedeathlycow.thermoo.patches.friendsandfoes.mixin.friendsandfoes.present;

import com.faboslav.friendsandfoes.common.entity.IceologerEntity;
import com.github.thedeathlycow.thermoo.patches.friendsandfoes.FriendsAndFoesPatch;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IceologerEntity.SlowTargetGoal.class)
public class SlowTargetGoalMixin {
    @WrapOperation(
            method = "castSpell",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V")
    )
    private void setTemperature(LivingEntity instance, int i, Operation<Void> original) {
        FriendsAndFoesPatch.freezeFromSlowTargetSpell(instance);
        original.call(instance, 0);
    }
}