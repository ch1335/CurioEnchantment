package com.chen1335.curioEnchantment.common;

import com.chen1335.curioEnchantment.API.Object.Capabilities;
import com.chen1335.curioEnchantment.API.Object.Enchantments;
import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.common.capability.PlayerData;
import com.chen1335.curioEnchantment.enchantment.AttributeEnchantment;
import com.chen1335.curioEnchantment.enchantment.enchantments.Devoted;
import com.chen1335.curioEnchantment.enchantment.enchantments.Knowledge;
import com.chen1335.curioEnchantment.enchantment.enchantments.Various;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = CurioEnchantment.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GameEvent {
        @SubscribeEvent
        public static void AttachEntityCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                event.addCapability(Capabilities.PLAYER_DATA_RESOURCELOCATION, new PlayerData());
            }
        }

        public static final UUID KNOWLEDGE_SPELL_POWER = UUID.fromString("c8bec5df-98a4-494b-a867-49226be17426");
        public static final UUID KNOWLEDGE_MAX_MANA = UUID.fromString("0b2dff1f-a1e1-4f6a-ac2c-d4770f1881b0");
        @SubscribeEvent
        public static void CurioAttributeModifierEvent(CurioAttributeModifierEvent event) {
            ItemStack itemStack = event.getItemStack();
            itemStack.getAllEnchantments().forEach((enchantment, integer) -> {
                if (enchantment instanceof AttributeEnchantment attributeEnchantment) {
                    attributeEnchantment.getAttributeModifiers(integer,event.getUuid()).forEach(event::addModifier);
                }
            });


            if (CurioEnchantment.IRONS_SPELLBOOKS_LOADED) {
                if (itemStack.getItem() instanceof SpellBook) {
                    int knowledgeLevel = itemStack.getEnchantmentLevel(Enchantments.IronsSpellBooksEnchantments.KNOWLEDGE.get());
                    if (knowledgeLevel > 0) {
                        int spells = ISpellContainer.get(itemStack).getActiveSpellCount();
                        event.addModifier(AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(KNOWLEDGE_SPELL_POWER, "", 0.01 * spells, AttributeModifier.Operation.MULTIPLY_BASE));
                        event.addModifier(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(KNOWLEDGE_MAX_MANA, "", 5 * spells, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void BreakSpeed(PlayerEvent.BreakSpeed event) {
            CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(iCuriosItemHandler -> {
                iCuriosItemHandler.getStacksHandler("hands").ifPresent(iCurioStacksHandler -> {
                    int quickClawLevel = 0;
                    for (int i = 0; i < iCurioStacksHandler.getSlots(); i++) {
                        quickClawLevel += iCurioStacksHandler.getStacks().getStackInSlot(i).getEnchantmentLevel(Enchantments.QUICK_CLAW.get());
                    }
                    event.setNewSpeed((float) (event.getNewSpeed() * (1 + (0.025 * quickClawLevel))));
                });
            });


        }

        public static class IronsSpellbooksEvent {
            @SubscribeEvent
            public static void SpellOnCastEvent(SpellOnCastEvent event) {
                Player player = event.getEntity();
                if (player != null) {
                    Objects.requireNonNull(player.getAttribute(AttributeRegistry.SPELL_POWER.get())).removeModifier(Devoted.MODIFIER_UUID);
                    Objects.requireNonNull(player.getAttribute(AttributeRegistry.SPELL_POWER.get())).removeModifier(Various.MODIFIER_UUID);

                    Optional<PlayerData> optional = player.getCapability(Capabilities.PLAYER_DATA).resolve();
                    if (optional.isPresent()) {
                        PlayerData playerData = optional.get();
                        ItemStack spellbookStack = Utils.getPlayerSpellbookStack(player);
                        if (spellbookStack != null) {
                            int devotedLevel = spellbookStack.getEnchantmentLevel(Enchantments.IronsSpellBooksEnchantments.DEVOTED.get());
                            int variousLevel = spellbookStack.getEnchantmentLevel(Enchantments.IronsSpellBooksEnchantments.VARIOUS.get());

                            if (Objects.equals(playerData.LastCastSpell, event.getSpellId())) {
                                playerData.sameCastSpellCount = Math.min(devotedLevel, playerData.sameCastSpellCount + 1);
                                Objects.requireNonNull(player.getAttribute(AttributeRegistry.SPELL_POWER.get())).addTransientModifier(new AttributeModifier(Devoted.MODIFIER_UUID, "", playerData.sameCastSpellCount * 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
                            } else {
                                playerData.LastCastSpell = event.getSpellId();
                                playerData.sameCastSpellCount = 0;
                                Objects.requireNonNull(player.getAttribute(AttributeRegistry.SPELL_POWER.get())).addTransientModifier(new AttributeModifier(Various.MODIFIER_UUID, "", variousLevel * 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
                            }
                        }
                    }
                }
            }

            @SubscribeEvent
            public static void ModifySpellLevelEvent(ModifySpellLevelEvent event) {
                if (event.getEntity() instanceof Player player) {
                    ItemStack spellbookStack = Utils.getPlayerSpellbookStack(player);
                    if (spellbookStack != null) {
                        int ancientWisdom = spellbookStack.getEnchantmentLevel(Enchantments.IronsSpellBooksEnchantments.ANCIENT_WISDOM.get());
                        if (ancientWisdom > 0) {
                            event.addLevels(1);
                        }
                    }
                }
            }

            @SubscribeEvent
            public static void LivingDropsEvent(LivingDropsEvent event) {
                if (event.getEntity().getType() == EntityRegistry.DEAD_KING.get()) {
                    ItemEntity itemEntity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.IronsSpellBooksEnchantments.ANCIENT_WISDOM.get(), 1)));
                    event.getDrops().add(itemEntity);
                }
            }
        }
    }
}
