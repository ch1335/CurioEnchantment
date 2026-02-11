package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Solid extends AttributeEnchantment {
    public Solid() {
        super(Rarity.COMMON);
        this.setInfo(new EnchantmentInfo(4));
        this.addAttributeModifier(Attributes.ARMOR,  i -> i * 0.25, AttributeModifier.Operation.ADDITION);
    }
}
