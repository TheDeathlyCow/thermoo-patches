package com.github.thedeathlycow.thermoo.patches.neoforge.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.common.util.TotemUtil;
import com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.compat.RequiresMods;
import com.github.thedeathlycow.thermoo.patches.neoforge.impl.friendsandfoes.FriendsAndFoesPatch;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@RequiresMods("friendsandfoes")
@Mixin(TotemUtil.class)
public class TotemUtilMixin {
    @WrapOperation(
            method = "lambda$freezeEntities$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V"
            )
    )
    private static void applyThermooTotemFreezing(LivingEntity instance, int value, Operation<Void> original) {
        FriendsAndFoesPatch.freezeFromTotem(instance);
        original.call(instance, 0);
    }
}