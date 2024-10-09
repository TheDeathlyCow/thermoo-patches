package com.github.thedeathlycow.thermoo.patches.mixin.client.compat.overflowingbars.present;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.HeartOverlayRecorder;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fuzs.overflowingbars.client.gui.HealthBarRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
@Mixin(
        value = HealthBarRenderer.class,
        remap = false
)
public class HealthBarRendererMixin {

    @Unique
    private final ThreadLocal<Integer> thermoo_patches$currentHeart = ThreadLocal.withInitial(() -> 0);

    @WrapOperation(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lfuzs/overflowingbars/client/gui/HealthBarRenderer$ModHeartType;renderHeart(Lnet/minecraft/client/gui/DrawContext;IIZZZ)V",
                    ordinal = 0)
    )
    private void captureHeartPosition(
            @Coerce Object instance,
            DrawContext guiGraphics,
            int posX, int posY,
            boolean blinking,
            boolean halfHeart,
            boolean hardcore,
            Operation<Void> original
    ) {
        int index = thermoo_patches$currentHeart.get();
        HeartOverlayRecorder.INSTANCE.setHeartPosition(index, posX, posY);
        thermoo_patches$currentHeart.set(index + 1);

        original.call(instance, guiGraphics, posX, posY, blinking, halfHeart, hardcore);
    }

    @Inject(
            method = "renderHearts",
            at = @At("TAIL")
    )
    private void renderOverlayBar(
            DrawContext guiGraphics,
            PlayerEntity player,
            int posX, int posY,
            int heartOffsetByRegen,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int currentAbsorptionHealth,
            boolean blink,
            CallbackInfo ci
    ) {
        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker()
                .render(
                        guiGraphics,
                        player,
                        HeartOverlayRecorder.INSTANCE.getHeartPositions(),
                        displayHealth,
                        20
                );
        Arrays.fill(HeartOverlayRecorder.INSTANCE.getHeartPositions(), null);
        thermoo_patches$currentHeart.remove();
    }

}
