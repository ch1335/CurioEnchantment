package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Light extends AttributeEnchantment {
    public Light() {
        super(Rarity.UNCOMMON);
        this.setInfo(EnchantmentInfo.createNormal(2, EnchantmentInfo.Cost.dynamicCost(15, 10), EnchantmentInfo.Cost.dynamicCost(20, 11)));
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,  i -> i * 0.01, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
