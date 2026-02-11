package com.chen1335.curioEnchantment.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioEnchantmentCategory {
    public static final EnchantmentCategory CURIOS = EnchantmentCategory.create("curios", item -> !CuriosApi.getItemStackSlots(item.getDefaultInstance(), false).isEmpty());

    public static final EnchantmentCategory SPELL_BOOK = EnchantmentCategory.create("spellbook", item -> CuriosApi.getItemStackSlots(item.getDefaultInstance(), false).get("spellbook") != null);

    public static final EnchantmentCategory HANDS = EnchantmentCategory.create("hands", item -> CuriosApi.getItemStackSlots(item.getDefaultInstance(), false).get("hands") != null);


}
