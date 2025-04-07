package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.st0x0ef.stellaris.common.oxygen.DimensionOxygenManager;
import com.st0x0ef.stellaris.common.oxygen.GlobalOxygenManager;
import com.st0x0ef.stellaris.common.utils.PlanetUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;

public final class OutdoorTemperatureModifier implements ServerTickEvents.EndWorldTick {
    private static final TagKey<Item> SPACE_SUITS = TagKey.of(RegistryKeys.ITEM, StellarisPatch.id("space_suits"));

    private static final Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> OUTDOOR_MODIFIERS = Util.make(
            HashMultimap.create(4, 1),
            map -> {
                map.put(
                        ThermooAttributes.HEAT_RESISTANCE,
                        new EntityAttributeModifier(
                                StellarisPatch.id("outdoor_heat_resistance"),
                                -1,
                                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );

                map.put(
                        ThermooAttributes.ENVIRONMENT_HEAT_RESISTANCE,
                        new EntityAttributeModifier(
                                StellarisPatch.id("outdoor_environment_heat_resistance"),
                                -1,
                                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );

                map.put(
                        ThermooAttributes.FROST_RESISTANCE,
                        new EntityAttributeModifier(
                                StellarisPatch.id("outdoor_frost_resistance"),
                                -1,
                                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );

                map.put(
                        ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                        new EntityAttributeModifier(
                                StellarisPatch.id("outdoor_environment_frost_resistance"),
                                -1,
                                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
    );

    @Override
    public void onEndTick(ServerWorld world) {
        if (PlanetUtil.hasOxygen(world)) {
            return;
        }

        DimensionOxygenManager manager = GlobalOxygenManager.getInstance()
                .getOrCreateDimensionManager(world);

        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.isAlive()) {
                if (isWearingSpaceSuit(player) || manager.hasOxygenAt(player.getBlockPos())) {
                    player.getAttributes().removeModifiers(OUTDOOR_MODIFIERS);
                } else {
                    player.getAttributes().addTemporaryModifiers(OUTDOOR_MODIFIERS);
                }
            }
        }
    }

    private static boolean isWearingSpaceSuit(LivingEntity entity) {
        for (ItemStack stack : entity.getArmorItems()) {
            if (!stack.isIn(SPACE_SUITS)) {
                return false;
            }
        }

        return true;
    }
}