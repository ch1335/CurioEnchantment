package com.chen1335.curioEnchantment.data.enchantments;

import com.chen1335.curioEnchantment.CurioEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EnchantmentsProvider {
    protected Map<ResourceKey<?>, List<ICondition>> thisConditions = new HashMap<>();

    abstract void bootstrap(BootstrapContext<Enchantment> context);

    public void bootstrap(BootstrapContext<Enchantment> context, Map<ResourceKey<?>, List<ICondition>> conditions) {
        this.bootstrap(context);
        thisConditions.forEach((resourceKey, iConditions) -> {
            conditions.computeIfAbsent(resourceKey, resourceKey1 -> new ArrayList<>()).addAll(iConditions);
        });
    }

    abstract String getModId();

    protected void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        thisConditions.computeIfAbsent(key, (key1) -> new ArrayList<>()).add(new ModLoadedCondition(getModId()));
        context.register(key, builder.build(key.location()));
    }

    protected static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CurioEnchantment.MODID, name));
    }
}
