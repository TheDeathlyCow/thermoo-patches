package com.github.thedeathlycow.thermoo.patches.mixin.common.compat.immersive_weathering.present;

import com.github.thedeathlycow.thermoo.patches.compat.immersiveweathering.IcicleFreezeHandler;
import com.ordana.immersive_weathering.blocks.IcicleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = IcicleBlock.class
)
public class IcicleBlockMixin {

    @Inject(
            method = "onLandedUpon",
            at = @At("HEAD")
    )
    private void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        IcicleFreezeHandler.onLandedUpon(world, state, pos, entity, fallDistance);
    }

}
