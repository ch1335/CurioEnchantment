package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class QuickCasting extends AttributeEnchantment {
    public QuickCasting() {
        super(Rarity.COMMON, CurioEnchantmentCategory.SPELL_BOOK);
        this.setInfo(EnchantmentInfo.createNormal(5, EnchantmentInfo.Cost.dynamicCost(15, 7), EnchantmentInfo.Cost.dynamicCost(20, 11)));
        this.addAttributeModifier(AttributeRegistry.CAST_TIME_REDUCTION.get(),  i -> i * 0.04, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
