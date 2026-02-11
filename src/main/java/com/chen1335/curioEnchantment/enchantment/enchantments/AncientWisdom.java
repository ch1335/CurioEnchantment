package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentBase;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;

public class AncientWisdom extends CurioEnchantmentBase {
    public AncientWisdom() {
        super(Rarity.RARE, CurioEnchantmentCategory.SPELL_BOOK);
        this.setInfo(EnchantmentInfo.createTreasure(1));
    }
}
