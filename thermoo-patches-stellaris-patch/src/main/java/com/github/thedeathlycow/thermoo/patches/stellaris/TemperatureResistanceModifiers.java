package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.st0x0ef.stellaris.common.registry.ArmorMaterialsRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

public final class TemperatureResistanceModifiers {
    public static void initialize() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
                    context.modify(
                            TemperatureResistanceModifiers::isSpaceSuit,
                            TemperatureResistanceModifiers::applyEnvironmentResistance
                    );
                }
        );
    }

    private static void applyEnvironmentResistance(ComponentMap.Builder builder, Item item) {
        AttributeModifiersComponent modifiers = builder.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        if (modifiers.modifiers().isEmpty()) {
            modifiers = item.getAttributeModifiers();
        }

        if (item instanceof ArmorItem armorItem) {
            String modifierSubID = armorItem.getType().asString();
            AttributeModifierSlot slot = AttributeModifierSlot.forEquipmentSlot(armorItem.getSlotType());

            modifiers = modifiers
                    .with(
                            ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                            new EntityAttributeModifier(
                                    StellarisPatch.id("base_environment_frost_resistance/" + modifierSubID),
                                    0.25,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            slot
                    )
                    .with(
                            ThermooAttributes.ENVIRONMENT_HEAT_RESISTANCE,
                            new EntityAttributeModifier(
                                    StellarisPatch.id("base_environment_heat_resistance/" + modifierSubID),
                                    0.25,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            slot
                    );

            modifiers = applyJetSuitResistance(
                    modifiers,
                    armorItem,
                    slot,
                    modifierSubID
            );
        }


        builder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers);
    }

    private static AttributeModifiersComponent applyJetSuitResistance(
            AttributeModifiersComponent component,
            ArmorItem item,
            AttributeModifierSlot slot,
            String slotID
    ) {
        if (item.getMaterial().value() == ArmorMaterialsRegistry.JET_SUIT_MATERIAL) {
            return component
                    .with(
                            ThermooAttributes.FROST_RESISTANCE,
                            new EntityAttributeModifier(
                                    StellarisPatch.id("base_frost_resistance/" + slotID),
                                    1,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            slot
                    )
                    .with(
                            ThermooAttributes.HEAT_RESISTANCE,
                            new EntityAttributeModifier(
                                    StellarisPatch.id("base_heat_resistance/" + slotID),
                                    1,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            slot
                    );
        }
        return component;
    }

    private static boolean isSpaceSuit(Item item) {
        if (item instanceof ArmorItem armor) {
            ArmorMaterial material = armor.getMaterial().value();
            if (material == ArmorMaterialsRegistry.SPACE_SUIT_MATERIAL || material == ArmorMaterialsRegistry.JET_SUIT_MATERIAL) {
                return true;
            }
        }

        return false;
    }

    private TemperatureResistanceModifiers() {

    }
}