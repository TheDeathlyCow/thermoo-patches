package com.github.thedeathlycow.thermoo.patches.mixin.common.compat.friendsandfoes.present;

import com.faboslav.friendsandfoes.common.util.TotemUtil;
import com.github.thedeathlycow.thermoo.patches.compat.friendsandfoes.FriendsAndFoesPatch;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TotemUtil.class)
public class TotemUtilMixin {
    @WrapOperation(
            method = "lambda$freezeEntities$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private static void applyThermooTotemFreezing(LivingEntity instance, int value, Operation<Void> original) {
        FriendsAndFoesPatch.freezeFromTotem(instance);
        original.call(instance, 0);
    }
}