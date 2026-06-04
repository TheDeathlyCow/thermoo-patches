package com.github.thedeathlycow.thermoo.patches.neoforge.mixin.overflowingbars;

import com.github.thedeathlycow.thermoo.api.client.StatusBarOverlayRenderEvents;
import com.github.thedeathlycow.thermoo.patches.neoforge.impl.base.compat.RequiresMods;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fuzs.overflowingbars.client.gui.HealthBarRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@RequiresMods("overflowingbars")
@Mixin(HealthBarRenderer.class)
public class HealthBarRendererMixin {
    @Unique
    private static final int THERMOO_PATCHES_MAX_DISPLAY_HEARTS = 20;

    @Inject(
            method = "renderHearts",
            at = @At(
                    value = "INVOKE",
                    target = "Lfuzs/overflowingbars/client/gui/HealthBarRenderer$ModHeartType;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;IIZZZ)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void captureHeartPosition(
            GuiGraphics guiGraphics,
            Player player,
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
            @Local(name = "currentPosY") int currentPosY,
            @Share("thermoo_heart_positions") LocalRef<Vector2i[]> heartPositionsRef
    ) {
        if (heartPositionsRef.get() == null) {
            heartPositionsRef.set(new Vector2i[THERMOO_PATCHES_MAX_DISPLAY_HEARTS]);
        }

        if (currentHeart < THERMOO_PATCHES_MAX_DISPLAY_HEARTS) {
            heartPositionsRef.get()[currentHeart] = new Vector2i(currentPosX, currentPosY);
        }
    }

    @Inject(
            method = "renderHearts",
            at = @At("TAIL")
    )
    private void renderOverlayBar(
            GuiGraphics graphics,
            Player player,
            int posX, int posY,
            int heartOffsetByRegen,
            float maxHealth,
            int currentHealth,
            int displayHealth,
            int currentAbsorptionHealth,
            boolean blink,
            CallbackInfo ci,
            @Share("thermoo_heart_positions") LocalRef<Vector2i[]> heartPositionsRef
    ) {
        Vector2i[] heartPositions = heartPositionsRef.get();
        if (heartPositions == null) {
            return;
        }

        graphics.pose().pushPose();
        // overflowing bars renders stuff at a weird depth for some reason...
        graphics.pose().translate(0.0F, 0.0F, 200f);
        StatusBarOverlayRenderEvents.AFTER_HEALTH_BAR.invoker().render(
                graphics,
                player,
                heartPositions,
                displayHealth,
                Math.min(THERMOO_PATCHES_MAX_DISPLAY_HEARTS, Mth.ceil(maxHealth))
        );
        graphics.pose().popPose();
    }
}