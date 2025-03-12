package com.chen1335.curioEnchantment.API.objects;

import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import com.chen1335.curioEnchantment.CurioEnchantment;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class DataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.DataComponents.createDataComponents(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, CurioEnchantment.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<CurioEnchantmentAttributeEffect>>>> CURIO_ATTRIBUTE = DATA_COMPONENT_TYPES.registerComponentType("curio_attribute", listBuilder -> listBuilder.persistent(ConditionalEffect.codec(CurioEnchantmentAttributeEffect.CODEC.codec(), LootContextParamSets.ENCHANTED_ITEM).listOf()));
}
