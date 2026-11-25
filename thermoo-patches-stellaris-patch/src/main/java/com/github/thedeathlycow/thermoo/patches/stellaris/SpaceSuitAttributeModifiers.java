package com.github.thedeathlycow.thermoo.patches.stellaris;

import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.item.v2.ModifyItemAttributeModifiersCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;

import java.util.EnumMap;
import java.util.Map;

public class SpaceSuitAttributeModifiers implements ModifyItemAttributeModifiersCallback {
    private final Map<EquipmentSlot, ModifierGroup> modifierCache = new EnumMap<>(EquipmentSlot.class);

    @Override
    public void modifyAttributeModifiers(ItemStack stack, ItemAttributeModifiers.Builder builder) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);

        if (equippable != null && stack.is(StellarisPatch.SPACE_SUITS)) {
            EquipmentSlot slot = equippable.slot();
            EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(slot);
            ModifierGroup group = this.getModifierGroup(slot);

            builder.add(ThermooAttributes.FROST_RESISTANCE, group.frostResistance(), slotGroup);
            builder.add(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE, group.envFrostResistance(), slotGroup);
            builder.add(ThermooAttributes.HEAT_RESISTANCE, group.heatResistance(), slotGroup);
            builder.add(ThermooAttributes.ENVIRONMENT_HEAT_RESISTANCE, group.envHeatResistance(), slotGroup);
        }
    }

    private ModifierGroup getModifierGroup(EquipmentSlot slot) {
        return this.modifierCache.computeIfAbsent(slot, s -> {
            return new ModifierGroup(
                    new AttributeModifier(
                            StellarisPatch.id("base_frost_resistance/" + s.getSerializedName()),
                            1.0,
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    new AttributeModifier(
                            StellarisPatch.id("base_environment_frost_resistance/" + s.getSerializedName()),
                            0.25,
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    new AttributeModifier(
                            StellarisPatch.id("base_heat_resistance/" + s.getSerializedName()),
                            1.0,
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    new AttributeModifier(
                            StellarisPatch.id("base_environment_heat_resistance/" + s.getSerializedName()),
                            0.25,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        });
    }

    private record ModifierGroup(
            AttributeModifier frostResistance,
            AttributeModifier envFrostResistance,
            AttributeModifier heatResistance,
            AttributeModifier envHeatResistance
    ) {

    }
}