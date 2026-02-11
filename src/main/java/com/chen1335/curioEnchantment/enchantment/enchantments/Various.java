package com.chen1335.curioEnchantment.enchantment.enchantments;

import com.chen1335.curioEnchantment.API.Object.Enchantments;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentBase;
import com.chen1335.curioEnchantment.enchantment.CurioEnchantmentCategory;
import com.chen1335.curioEnchantment.enchantment.EnchantmentInfo;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Various extends CurioEnchantmentBase {
    public static final UUID MODIFIER_UUID = UUID.fromString("a6eacf13-764a-452e-aa46-34178d8b9135");

    public Various() {
        super(Rarity.UNCOMMON, CurioEnchantmentCategory.SPELL_BOOK);
        this.setInfo(EnchantmentInfo.createNormal(3, EnchantmentInfo.Cost.dynamicCost(20, 7), EnchantmentInfo.Cost.dynamicCost(20, 11)));
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && (enchantment != Enchantments.IronsSpellBooksEnchantments.DEVOTED.get());
    }
}
