package com.github.thedeathlycow.thermoo.patches.mixin.client.compat.colorfulhearts.present;

import com.github.thedeathlycow.thermoo.patches.HeartOverlayRecorder;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.render.HeartRenderer;

@Mixin(HeartRenderer.class)
public class HeartRendererMixin {

    @Unique
    private int thermoo_patches$heartIndex = 0;

    @Inject(
            method = "renderPlayerHearts",
            at = @At("HEAD")
    )
    private void recordExtraInformation(
            DrawContext guiGraphics,
            PlayerEntity player,
            int x, int y,
            int maxHealth, int currentHealth, int displayHealth,
            int absorption,
            boolean blinking,
            CallbackInfo ci
    ) {
        var recorder = HeartOverlayRecorder.INSTANCE;
        recorder.setPlayer(player);
        recorder.setDisplayHealth(displayHealth);
    }

    @WrapOperation(
            method = "renderPlayerHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lterrails/colorfulhearts/api/heart/drawing/Heart;draw(Lnet/minecraft/client/gui/DrawContext;IIZZZ)V"
            )
    )
    private void captureHeartPositions(
            Heart instance,
            DrawContext guiGraphics,
            int x, int y,
            boolean hardcore,
            boolean highlightContainer,
            boolean highlightHeart,
            Operation<Void> original
    ) {
        HeartOverlayRecorder.INSTANCE.setHeartPosition(thermoo_patches$heartIndex, x, y);
        thermoo_patches$heartIndex++;

        original.call(instance, guiGraphics, x, y, hardcore, highlightContainer, highlightHeart);
    }

    @Inject(
            method = "renderPlayerHearts",
            at = @At("TAIL")
    )
    private void resetIndex(
            DrawContext guiGraphics,
            PlayerEntity player,
            int x, int y,
            int maxHealth,
            int currentHealth,
            int displayHealth,
            int absorption,
            boolean blinking,
            CallbackInfo ci
    ) {
        thermoo_patches$heartIndex = 0;
    }
}
