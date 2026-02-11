package com.chen1335.curioEnchantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AttributeEnchantment extends CurioEnchantmentBase {
    private final HashMap<Attribute, BiFunction<Integer, UUID, AttributeModifier>> map = new HashMap<>();

    public AttributeEnchantment(Rarity rarity) {
        this(rarity, CurioEnchantmentCategory.CURIOS);
    }

    public AttributeEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory) {
        this(rarity, enchantmentCategory, new EquipmentSlot[]{});
    }

    public AttributeEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    public void addAttributeModifier(Attribute attribute, Function<Integer, Double> function, AttributeModifier.Operation operation) {
        map.put(attribute, (integer, uuid) -> new AttributeModifier(uuid, "", function.apply(integer), operation));
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers(int level, UUID uuid) {
        Map<Attribute, AttributeModifier> modifierMap = new HashMap<>();
        map.forEach((attribute, function) -> {
            modifierMap.put(attribute, function.apply(level, uuid));
        });

        return modifierMap;
    }

}
