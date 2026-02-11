package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Grow extends AttributeEnchantment {
    public Grow() {
        super(Rarity.COMMON);
        setInfo(new EnchantmentInfo(5));
        this.addAttributeModifier(Attributes.MAX_HEALTH, i -> i * 0.2, AttributeModifier.Operation.ADDITION);
    }
}
