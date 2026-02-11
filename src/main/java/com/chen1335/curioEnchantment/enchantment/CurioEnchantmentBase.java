package com.chen1335.curioEnchantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurioEnchantmentBase extends Enchantment {
    protected EnchantmentInfo info = new EnchantmentInfo(1);

    public CurioEnchantmentBase(Rarity rarity) {
        this(rarity, CurioEnchantmentCategory.CURIOS);
    }

    public CurioEnchantmentBase(Rarity rarity, EnchantmentCategory enchantmentCategory) {
        this(rarity, enchantmentCategory, new EquipmentSlot[]{});
    }

    protected CurioEnchantmentBase(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    public void setInfo(EnchantmentInfo info) {
        this.info = info;
    }

    @Override
    public boolean isTreasureOnly() {
        return info.isTreasureOnly();
    }

    @Override
    public boolean isDiscoverable() {
        return info.isDiscoverable();
    }

    @Override
    public boolean isTradeable() {
        return info.isTradeable();
    }

    @Override
    public int getMaxCost(int level) {
        return info.getMaxCost(level);
    }

    @Override
    public int getMinCost(int level) {
        return info.getMinCost(level);
    }

    @Override
    public int getMaxLevel() {
        return info.getMaxLevel();
    }
}
