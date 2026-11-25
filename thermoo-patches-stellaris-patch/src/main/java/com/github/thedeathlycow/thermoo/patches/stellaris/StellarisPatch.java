package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooBuiltInRegistries;
import com.github.thedeathlycow.thermoo.api.item.v2.ModifyItemAttributeModifiersCallback;
import com.github.thedeathlycow.thermoo.patches.IntegratedMod;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class StellarisPatch implements ModInitializer {
    public static final String MODID = "thermoo-patches-stellaris-patch";
    public static final TagKey<Item> SPACE_SUITS = TagKey.create(Registries.ITEM, id("space_suits"));


    @Override
    public void onInitialize(ModContainer modContainer) {
        if (IntegratedMod.STELLARIS.isModLoaded()) {
            Registry.register(ThermooBuiltInRegistries.ENVIRONMENT_PROVIDER_TYPE, id("has_oxygen"), HasOxygenEnvironmentProvider.CODEC);
            ModifyItemAttributeModifiersCallback.EVENT.register(new SpaceSuitAttributeModifiers());
            DimensionModifier.initialize();
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static Identifier stellarisId(String path) {
        return Identifier.fromNamespaceAndPath("stellaris", path);
    }
}