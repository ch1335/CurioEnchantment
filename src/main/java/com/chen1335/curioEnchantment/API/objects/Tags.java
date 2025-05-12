package com.chen1335.curioEnchantment.API.objects;

import com.chen1335.curioEnchantment.CurioEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class Tags {
    public static class ItemTags {
        public static final TagKey<Item> CURIOS_ENCHANTABLE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curio_enchantment", "curios_enchantable"));
        public static final TagKey<Item> SPELLBOOK_ENCHANTABLE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curio_enchantment", "spellbook_enchantable"));
        public static final TagKey<Item> HANDS_ENCHANTABLE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curio_enchantment", "hands_enchantable"));
    }

    public static class EnchantmentTags {
        public static final TagKey<Enchantment> ENCHANTMENT = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("curio_enchantment", "enchantment"));
        public static final TagKey<Enchantment> COMMON_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("curio_enchantment", "common_enchantments"));
        public static final TagKey<Enchantment> DEVOTED_EXCLUSIVE = TagKey.create(Registries.ENCHANTMENT,ResourceLocation.fromNamespaceAndPath(CurioEnchantment.MODID,"devoted_exclusive"));
    }
}
