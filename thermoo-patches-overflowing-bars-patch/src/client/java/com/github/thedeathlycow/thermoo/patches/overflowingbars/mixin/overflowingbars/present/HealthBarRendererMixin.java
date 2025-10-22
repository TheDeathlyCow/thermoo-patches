package com.github.thedeathlycow.thermoo.patches.overflowingbars.mixin.overflowingbars.present;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.impl.client.HeartBarContextImpl;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fuzs.overflowingbars.client.gui.HealthBarRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SequencedCollection;

@Environment(EnvType.CLIENT)
@Mixin(
        value = HealthBarRenderer.class,
        remap = false
)
public class HealthBarRendererMixin {
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
            DrawContext drawContext,
            PlayerEntity player,
            int posX, int posY,
            int heartOffsetByRegen,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int currentAbsorptionHealth,
            boolean blink,
            CallbackInfo ci,
            @Local(name = "currentPosX") int currentPosX,
            @Local(name = "currentPosY") int currentPosY,
            @Share("thermoo_heart_positions") LocalRef<SequencedCollection<Vector2i>> heartPositionsRef
    ) {
        if (heartPositionsRef.get() == null) {
            heartPositionsRef.set(new ArrayList<>());
        }
        heartPositionsRef.get().add(new Vector2i(currentPosX, currentPosY));
    }

    @Inject(
            method = "renderHearts",
            at = @At("TAIL")
    )
    private void renderOverlayBar(
            DrawContext drawContext,
            PlayerEntity player,
            int posX, int posY,
            int heartOffsetByRegen,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int currentAbsorptionHealth,
            boolean blink,
            CallbackInfo ci,
            @Share("thermoo_heart_positions") LocalRef<SequencedCollection<Vector2i>> heartPositionsRef
    ) {
        SequencedCollection<Vector2i> heartPositions = heartPositionsRef.get();
        if (heartPositions == null) {
            return;
        }

        var heartBarContext = new HeartBarContextImpl(
                Collections.unmodifiableSequencedCollection(heartPositions),
                displayHealth,
                Math.min(20, MathHelper.ceil(maxHealth))
        );

        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker().render(drawContext, player, heartBarContext);
    }
}
