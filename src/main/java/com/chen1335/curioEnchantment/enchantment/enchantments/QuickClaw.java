package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;

public class QuickClaw extends AttributeEnchantment {
    public QuickClaw() {
        super(Rarity.UNCOMMON, CurioEnchantmentCategory.HANDS);
        this.setInfo(EnchantmentInfo.createNormal(4, EnchantmentInfo.Cost.dynamicCost(15, 10), EnchantmentInfo.Cost.dynamicCost(20, 11)));
    }
}
