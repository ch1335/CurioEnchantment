package com.chen1335.curioEnchantment.common;

import com.chen1335.curioEnchantment.API.objects.AttachmentTypes;
import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.common.attachmentTypes.PlayerData;
import com.chen1335.curioEnchantment.data.enchantments.IronsSpellBooksEnchantments;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.Objects;
import java.util.Optional;

public class EventHandler {
    @EventBusSubscriber(modid = CurioEnchantment.MODID)
    public static class GAME {
        @SubscribeEvent
        public static void curioAttributeEvent(CurioAttributeModifierEvent event) {
            ItemStack itemStack = event.getItemStack();
            HolderLookup.@Nullable RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
            if (lookup == null) {
                return;
            }

            if (CurioEnchantment.IRONS_SPELL_BOOKS_LOADED) {
                if (itemStack.getItem() instanceof SpellBook) {
                    Optional<Holder.Reference<Enchantment>> knowledgeHolder = lookup.get(IronsSpellBooksEnchantments.KNOWLEDGE);
                    if (knowledgeHolder.isPresent() && itemStack.getEnchantmentLevel(knowledgeHolder.get()) > 0) {
                        @Nullable ISpellContainer container = itemStack.get(ComponentRegistry.SPELL_CONTAINER);
                        if (container != null) {
                            event.addModifier(AttributeRegistry.MAX_MANA, new AttributeModifier(CurioEnchantment.id("knowledge_enchantment"), 5 * container.getActiveSpellCount(), AttributeModifier.Operation.ADD_VALUE));
                            event.addModifier(AttributeRegistry.SPELL_POWER, new AttributeModifier(CurioEnchantment.id("knowledge_enchantment"), 0.01 * container.getActiveSpellCount(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                        }
                    }
                }
            }

            EnchantmentHelper.runIterationOnItem(itemStack, (enchantment, level) -> {
                enchantment.value().getEffects(DataComponentTypes.CURIO_ATTRIBUTE.value()).forEach(curioEnchantmentAttributeEffect -> {
                    CurioEnchantmentAttributeEffect effect = curioEnchantmentAttributeEffect.effect();
                    event.addModifier(effect.attribute(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CurioEnchantment.MODID, Objects.requireNonNull(enchantment.getKey()).location().getPath() + "_enchantment_" + event.getSlotContext().identifier().toString().replace(":", "_") + "_" + event.getSlotContext().index()), effect.amount().calculate(level), effect.operation()));

                });
            });
        }


        public static class IronsSpellBooksEvents {
            @SubscribeEvent
            public static void SpellOnCastEvent(SpellOnCastEvent event) {
                Player player = event.getEntity();
                AttributeInstance spellPower = player.getAttribute(AttributeRegistry.SPELL_POWER);
                if (spellPower == null) {
                    return;
                }
                spellPower.removeModifier(AttributeModifierResourceLocations.VARIOUS_ENCHANTMENT);
                spellPower.removeModifier(AttributeModifierResourceLocations.DEVOTED_ENCHANTMENT);


                if (event.getCastSource().equals(CastSource.SPELLBOOK)) {
                    @Nullable ItemStack itemStack = Utils.getPlayerSpellbookStack(player);
                    if (itemStack != null) {
                        PlayerData playerData = player.getData(AttachmentTypes.PLAYER_DATA);
                        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

                        Optional<Holder.Reference<Enchantment>> devotedHolder = enchantmentLookup.get(IronsSpellBooksEnchantments.DEVOTED);
                        if (devotedHolder.isPresent()) {
                            int devotedLevel = itemStack.getEnchantmentLevel(devotedHolder.get());
                            if (devotedLevel > 0) {
                                if (Objects.equals(playerData.lastCastSpell, event.getSpellId()) || Objects.equals(playerData.lastCastSpell, "")) {
                                    playerData.lastCastSpell = event.getSpellId();
                                    playerData.sameCastSpellCount = Math.min(playerData.sameCastSpellCount + 1, devotedLevel);
                                    spellPower.addTransientModifier(new AttributeModifier(AttributeModifierResourceLocations.DEVOTED_ENCHANTMENT, 0.15 * playerData.sameCastSpellCount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                                } else {
                                    playerData.lastCastSpell = event.getSpellId();
                                    playerData.sameCastSpellCount = 0;
                                }
                            }
                        }

                        Optional<Holder.Reference<Enchantment>> variousHolder = enchantmentLookup.get(IronsSpellBooksEnchantments.VARIOUS);
                        if (variousHolder.isPresent()) {
                            int variousLevel = itemStack.getEnchantmentLevel(variousHolder.get());
                            if (variousLevel > 0) {
                                if (!Objects.equals(playerData.lastCastSpell, event.getSpellId())) {
                                    playerData.lastCastSpell = event.getSpellId();
                                    spellPower.addTransientModifier(new AttributeModifier(AttributeModifierResourceLocations.VARIOUS_ENCHANTMENT, 0.05 * variousLevel, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                                }
                            }
                        }
                    }
                }
            }

            @SubscribeEvent
            public static void ModifySpellLevelEvent(ModifySpellLevelEvent event) {

                HolderLookup.@Nullable RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
                if (event.getEntity() instanceof Player player && lookup != null) {
                    @Nullable ItemStack itemStack = Utils.getPlayerSpellbookStack(player);
                    if (itemStack != null) {
                        Optional<Holder.Reference<Enchantment>> ancientWisdomHolder = lookup.get(IronsSpellBooksEnchantments.ANCIENT_WISDOM);
                        if (ancientWisdomHolder.isPresent()) {
                            int ancientWisdomLevel = itemStack.getEnchantmentLevel(ancientWisdomHolder.get());
                            event.addLevels(ancientWisdomLevel);
                        }
                    }
                }
            }
        }
    }
}
