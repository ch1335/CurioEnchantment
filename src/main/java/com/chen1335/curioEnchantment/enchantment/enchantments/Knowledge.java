package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentBase;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;

import java.util.UUID;

public class Knowledge extends CurioEnchantmentBase {
    public static final UUID MAX_MANA_MODIFIER_UUID = UUID.fromString("16620ced-dbcb-415b-8a21-5033ebf40c4b");
    public static final UUID SPELL_POWER_MODIFIER_UUID = UUID.fromString("8e48f65d-cc66-4c67-ad41-ca943801049c");

    public Knowledge() {
        super(Rarity.RARE, CurioEnchantmentCategory.SPELL_BOOK);
        this.setInfo(EnchantmentInfo.createNormal(1, EnchantmentInfo.Cost.constantCost(30), EnchantmentInfo.Cost.constantCost(70)));

    }
}
