package com.chen1335.curioEnchantment.enchantment;

public class EnchantmentInfo {
    private final boolean isTreasureOnly;
    private final boolean isDiscoverable;
    private final boolean isTradeable;
    private final Cost minCostFunction;
    private final Cost maxCostFunction;
    private final int maxLevel;

    public EnchantmentInfo(int maxLevel) {
        this.maxLevel = maxLevel;
        minCostFunction = Cost.dynamicCost(1, 11);
        maxCostFunction = Cost.dynamicCost(12, 11);
        isTradeable = true;
        isDiscoverable = true;
        isTreasureOnly = false;
    }

    public EnchantmentInfo(boolean isDiscoverable, boolean isTreasureOnly, boolean isTradeable, Cost minCostFunction, Cost maxCostFunction, int maxLevel) {
        this.minCostFunction = minCostFunction;
        this.maxCostFunction = maxCostFunction;
        this.isTradeable = isTradeable;
        this.isDiscoverable = isDiscoverable;
        this.isTreasureOnly = isTreasureOnly;
        this.maxLevel = maxLevel;
    }

    public boolean isTreasureOnly() {
        return isTreasureOnly;
    }

    public boolean isDiscoverable() {
        return isDiscoverable;
    }

    public boolean isTradeable() {
        return isTradeable;
    }

    public int getMinCost(int level) {
        return minCostFunction.get(level);
    }

    public int getMaxCost(int level) {
        return maxCostFunction.get(level);
    }

    public static EnchantmentInfo createNormal(int maxLevel, Cost minCostFunction, Cost maxCostFunction) {
        return new EnchantmentInfo(true, false, true, minCostFunction, maxCostFunction, maxLevel);
    }

    public static EnchantmentInfo createTreasure(int maxLevel) {
        return new EnchantmentInfo(false, true, false, EnchantmentInfo.Cost.constantCost(200), EnchantmentInfo.Cost.constantCost(200), maxLevel);
    }

    public int getMaxLevel() {
        return maxLevel;
    }


    public interface Cost {
        static DynamicCost dynamicCost(int first, int afterFirst) {
            return new DynamicCost(first, afterFirst);
        }

        static ConstantCost constantCost(int cost) {
            return new ConstantCost(cost);
        }

        int get(int level);

        record DynamicCost(int first, int afterFirst) implements Cost {
            @Override
            public int get(int level) {
                return first + (level - 1) * afterFirst;
            }
        }

        record ConstantCost(int level) implements Cost {
            @Override
            public int get(int level) {
                return this.level;
            }
        }
    }
}
