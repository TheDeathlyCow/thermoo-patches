package com.github.thedeathlycow.thermoo.patches.friendsandfoes;

import com.github.thedeathlycow.thermoo.patches.ThermooPatches;
import com.github.thedeathlycow.thermoo.patches.config.translate.Translate;
import com.github.thedeathlycow.thermoo.patches.config.ThermooPatchesConfig;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class FriendsAndFoesSettings {
    public static final Path PATH = ThermooPatches.getConfigDir().resolve("client/display.json5");

    public static final ConfigClassHandler<FriendsAndFoesSettings> HANDLER = ConfigClassHandler.createBuilder(FriendsAndFoesSettings.class)
            .id(ThermooPatches.id("client/display"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = ThermooPatchesConfig.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Freezing Totem temperature scale change")
    @SerialEntry(comment = "What percentage of a targets minimum temperature to remove when struck by the freezing effect of the Freezing Totem.")
    @FloatField(min = 0.0f, format = "%.2f")
    float freezingTotemTemperatureScaleChange = 1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Iceologer Ice Chunk temperature scale change")
    @SerialEntry(comment = "What percentage of a targets minimum temperature to remove when struck by the Iceologer's Ice Chunk spell.")
    @FloatField(format = "%.2f")
    float iceologerIceChunkTemperatureScaleChange = 0.25f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Iceologer Slow Target temperature scale change")
    @SerialEntry(comment = "What percentage of a targets minimum temperature to remove when struck by the Iceologer's Slow Target spell.")
    @FloatField(format = "%.2f")
    float iceologerSlowTargetTemperatureScaleChange = 0.25f;

    public static void initialize() {
        FriendsAndFoesSettings.HANDLER.load();
        FriendsAndFoesSettings.HANDLER.save();
    }

    public float freezingTotemTemperatureScaleChange() {
        return freezingTotemTemperatureScaleChange;
    }

    public float iceologerIceChunkTemperatureScaleChange() {
        return iceologerIceChunkTemperatureScaleChange;
    }

    public float iceologerSlowTargetTemperatureScaleChange() {
        return iceologerSlowTargetTemperatureScaleChange;
    }
}