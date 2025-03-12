package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.API.objects.Tags;
import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.data.enchantments.CEEnchantments;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class CEEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public CEEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CurioEnchantment.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(Tags.EnchantmentTags.ENCHANTMENT)
                .addTag(Tags.EnchantmentTags.COMMON_ENCHANTMENTS);

        tag(EnchantmentTags.TOOLTIP_ORDER)
                .addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location())
                .addOptional(IronsSpellBooksEnchantments.KNOWLEDGE.location())
                .addOptional(IronsSpellBooksEnchantments.DEVOTED.location())
                .addOptional(IronsSpellBooksEnchantments.VARIOUS.location())
                .addOptional(IronsSpellBooksEnchantments.QUICK_CASTING.location())
                .addTag(Tags.EnchantmentTags.COMMON_ENCHANTMENTS);

        tag(Tags.EnchantmentTags.COMMON_ENCHANTMENTS).add(
                CEEnchantments.SOLID,
                CEEnchantments.GROW,
                CEEnchantments.LIGHT,
                CEEnchantments.QUICK_CLAW
        );

        tag(EnchantmentTags.TRADEABLE).addTag(
                Tags.EnchantmentTags.COMMON_ENCHANTMENTS
        );

        tag(EnchantmentTags.IN_ENCHANTING_TABLE).addTag(
                Tags.EnchantmentTags.COMMON_ENCHANTMENTS
        );

        tag(EnchantmentTags.NON_TREASURE).addTag(
                Tags.EnchantmentTags.COMMON_ENCHANTMENTS
        );

        tag(EnchantmentTags.ON_RANDOM_LOOT).addTag(
                Tags.EnchantmentTags.COMMON_ENCHANTMENTS
        );
        addOptionTags(provider);
    }

    protected void addOptionTags(HolderLookup.@NotNull Provider provider) {
        tag(Tags.EnchantmentTags.ENCHANTMENT)
                .addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location())
        ;


        tag(EnchantmentTags.TREASURE)
                .addOptional(IronsSpellBooksEnchantments.ANCIENT_WISDOM.location())
        ;

        tag(Tags.EnchantmentTags.COMMON_ENCHANTMENTS)
                .addOptional(IronsSpellBooksEnchantments.QUICK_CASTING.location())
                .addOptional(IronsSpellBooksEnchantments.DEVOTED.location())
                .addOptional(IronsSpellBooksEnchantments.VARIOUS.location())
                .addOptional(IronsSpellBooksEnchantments.KNOWLEDGE.location())
        ;
    }
}
