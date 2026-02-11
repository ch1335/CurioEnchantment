package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentBase;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;

import java.util.UUID;

public class Devoted extends CurioEnchantmentBase {
    public static final UUID MODIFIER_UUID = UUID.fromString("a4dd8966-a1af-45e3-be2c-ad35bc16c24e");

    public Devoted() {
        super(Rarity.UNCOMMON, CurioEnchantmentCategory.SPELL_BOOK);
        this.setInfo(EnchantmentInfo.createNormal(3, EnchantmentInfo.Cost.dynamicCost(20, 7), EnchantmentInfo.Cost.dynamicCost(20, 11)));
    }
}
