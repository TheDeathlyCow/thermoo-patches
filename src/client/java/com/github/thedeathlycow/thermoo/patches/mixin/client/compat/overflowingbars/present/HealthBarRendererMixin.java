package com.github.thedeathlycow.thermoo.patches.mixin.client.compat.overflowingbars.present;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.HeartOverlayRecorder;
import com.llamalad7.mixinextras.sugar.Local;
import fuzs.overflowingbars.client.gui.HealthBarRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
@Mixin(
        value = HealthBarRenderer.class,
        remap = false
)
public class HealthBarRendererMixin {

    @Shadow
    private int displayHealth;

    @Inject(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lfuzs/overflowingbars/client/gui/HealthBarRenderer$ModHeartType;renderHeart(Lnet/minecraft/client/gui/DrawContext;IIZZZ)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER,
                    remap = true
            )
    )
    private void captureHeartPosition(
            DrawContext guiGraphics,
            PlayerEntity player,
            int posX, int posY,
            int heartOffsetByRegen,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int currentAbsorptionHealth,
            boolean blink,
            CallbackInfo ci,
            @Local(name = "currentHeart") int currentHeart,
            @Local(name = "currentPosX") int currentPosX,
            @Local(name = "currentPosY") int currentPosY
    ) {
        HeartOverlayRecorder.INSTANCE.setHeartPosition(currentHeart, currentPosX, currentPosY);
    }

//    @Inject(
//            method = "renderHearts",
//            at = @At("TAIL")
//    )
//    private void renderOverlayBar(
//            DrawContext guiGraphics,
//            PlayerEntity player,
//            int posX, int posY,
//            int heartOffsetByRegen,
//            float maxHealth,
//            int currentHealth,
//            int displayHealth,
//            int currentAbsorptionHealth,
//            boolean blink,
//            CallbackInfo ci
//    ) {
//        int maxDisplayHealth = Math.min(MathHelper.ceil(player.getMaxHealth()), 20);
//
//        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker()
//                .render(
//                        guiGraphics,
//                        player,
//                        HeartOverlayRecorder.INSTANCE.getHeartPositions(),
//                        this.displayHealth,
//                        20
//                );
//        Arrays.fill(HeartOverlayRecorder.INSTANCE.getHeartPositions(), null);
//    }
}
