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

public class EventHandler {
    @EventBusSubscriber(modid = CurioEnchantment.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class GAME {
        @SubscribeEvent
        public static void curioAttributeEvent(CurioAttributeModifierEvent event) {
            ItemStack itemStack = event.getItemStack();
            HolderLookup.@Nullable RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
            if (lookup == null) {
                return;
            }

            if (CurioEnchantment.IRONS_SPELL_BOOKS_LOADED) {
                if (itemStack.getItem() instanceof SpellBook && itemStack.getEnchantmentLevel(lookup.getOrThrow(IronsSpellBooksEnchantments.KNOWLEDGE)) > 0) {
                    @Nullable ISpellContainer container = itemStack.get(ComponentRegistry.SPELL_CONTAINER);
                    if (container != null) {
                        event.addModifier(AttributeRegistry.MAX_MANA, new AttributeModifier(CurioEnchantment.getResourceLocation("knowledge_enchantment"), 5 * container.getActiveSpellCount(), AttributeModifier.Operation.ADD_VALUE));
                        event.addModifier(AttributeRegistry.SPELL_POWER, new AttributeModifier(CurioEnchantment.getResourceLocation("knowledge_enchantment"), 0.01 * container.getActiveSpellCount(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    }
                }
            }

            EnchantmentHelper.runIterationOnItem(itemStack, (enchantment, level) -> {
                enchantment.value().getEffects(DataComponentTypes.CURIO_ATTRIBUTE.value()).forEach(curioEnchantmentAttributeEffect -> {
                    CurioEnchantmentAttributeEffect effect = curioEnchantmentAttributeEffect.effect();
                    event.addModifier(effect.attribute(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Objects.requireNonNull(enchantment.getKey()).location().getNamespace(), Objects.requireNonNull(enchantment.getKey()).location().getPath() + "_enchantment_" + event.getSlotContext().identifier() + "_" + event.getSlotContext().index()), effect.amount().calculate(level), effect.operation()));

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

                        int devotedLevel = itemStack.getEnchantmentLevel(event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(IronsSpellBooksEnchantments.DEVOTED));
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

                        int variousLevel = itemStack.getEnchantmentLevel(event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(IronsSpellBooksEnchantments.VARIOUS));
                        if (variousLevel > 0) {
                            if (!Objects.equals(playerData.lastCastSpell, event.getSpellId())) {
                                playerData.lastCastSpell = event.getSpellId();
                                spellPower.addTransientModifier(new AttributeModifier(AttributeModifierResourceLocations.VARIOUS_ENCHANTMENT, 0.05 * variousLevel, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
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
                        int ancientWisdomLevel = itemStack.getEnchantmentLevel(lookup.getOrThrow(IronsSpellBooksEnchantments.ANCIENT_WISDOM));
                        event.addLevels(ancientWisdomLevel);
                    }
                }
            }
        }
    }
}
