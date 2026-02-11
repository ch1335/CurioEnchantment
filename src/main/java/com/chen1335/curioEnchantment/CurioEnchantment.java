package com.chen1335.curioEnchantment;

import com.chen1335.curioEnchantment.API.Object.Enchantments;
import com.chen1335.curioEnchantment.common.EventHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(CurioEnchantment.MODID)
public class CurioEnchantment {
    public static final String MODID = "curio_enchantment";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static boolean IRONS_SPELLBOOKS_LOADED = false;

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> CURIO_ENCHANTMENTS = CREATIVE_MODE_TABS.register("curio_enchantment", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.curio_enchantment"))
            .icon(Items.ENCHANTED_BOOK::getDefaultInstance)
            .displayItems((parameters, output) -> {
                Enchantments.ENCHANTMENT_DEFERRED_REGISTER.getEntries().forEach(enchantmentRegistryObject -> {
                    output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentRegistryObject.get(), enchantmentRegistryObject.get().getMaxLevel())));
                });
            }).build());

    public CurioEnchantment() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_MODE_TABS.register(modEventBus);
        Enchantments.ENCHANTMENT_DEFERRED_REGISTER.register(modEventBus);

        if (ModList.get().isLoaded("irons_spellbooks")) {
            MinecraftForge.EVENT_BUS.register(EventHandler.GameEvent.IronsSpellbooksEvent.class);
            Enchantments.IronsSpellBooksEnchantments.init();
            IRONS_SPELLBOOKS_LOADED = true;
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

}
