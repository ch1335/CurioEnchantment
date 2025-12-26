package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.CurioEnchantment;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class LootModifiers extends GlobalLootModifierProvider {
    public LootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(output, registries, modid);
    }

    @Override
    protected void start() {
        add("entities/dead_king", new AddTableLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "entities/dead_king")).build(),
        }, LootTables.LootModifierLoot.DEAD_KING_LOOT_MODIFIER));
    }

    private static ResourceKey<LootTable> key(String pName) {
        return ResourceKey.create(Registries.LOOT_TABLE, CurioEnchantment.id(pName));
    }
}
