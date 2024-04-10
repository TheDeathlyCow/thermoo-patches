package com.github.thedeathlycow.thermoo.patches.mixin.client.compat.armorpointspp.present;

import dev.cheos.armorpointspp.core.RenderContext;
import dev.cheos.armorpointspp.core.RenderableText;
import dev.cheos.armorpointspp.core.adapter.IConfig;
import dev.cheos.armorpointspp.core.render.HealthTextComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
        value = HealthTextComponent.class,
        remap = false
)
@Environment(EnvType.CLIENT)
public class HealthTextComponentMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/cheos/armorpointspp/core/adapter/IConfig;bool(Ldev/cheos/armorpointspp/core/adapter/IConfig$Option;)Z",
                    ordinal = 4,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderTemperaturePercentage(RenderContext ctx, CallbackInfoReturnable<Boolean> cir, RenderableText text) {
        float temperature = MinecraftClient.getInstance().player.thermoo$getTemperatureScale();

        if (temperature != 0.0f && ctx.config.bool(IConfig.BooleanOption.FROSTBITE_TEXT_ENABLE)) {
            text.append(
                    new RenderableText(",")
                            .padRight(1.0f)
                            .withColor(ctx.config.hex(IConfig.HexOption.TEXT_COLOR_SEPARATOR))
            );
            text.append(
                    new RenderableText(Math.round(temperature * 100f))
                            .padRight(1.0f)
                            .withColor(
                                    ctx.config.hex(
                                            temperature > 0f
                                                    ? IConfig.HexOption.TEXT_COLOR_ABSORPTION
                                                    : IConfig.HexOption.TEXT_COLOR_FROSTBITE
                                    )
                            )
            );
            text.append(
                    new RenderableText("%")
                            .padRight(1.0f)
                            .withColor(ctx.config.hex(IConfig.HexOption.TEXT_COLOR_SEPARATOR))
            );
        }
    }
}
