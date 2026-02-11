package com.chen1335.curioEnchantment.API.Object;

import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.enchantment.enchantments.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Enchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CurioEnchantment.MODID);
    public static final RegistryObject<Enchantment> SOLID = ENCHANTMENT_DEFERRED_REGISTER.register("solid", Solid::new);
    public static final RegistryObject<Enchantment> GROW = ENCHANTMENT_DEFERRED_REGISTER.register("grow", Grow::new);
    public static final RegistryObject<Enchantment> LIGHT = ENCHANTMENT_DEFERRED_REGISTER.register("light", Light::new);
    public static final RegistryObject<Enchantment> QUICK_CLAW = ENCHANTMENT_DEFERRED_REGISTER.register("quick_claw", QuickClaw::new);


    public static class IronsSpellBooksEnchantments {
        public static RegistryObject<Enchantment> QUICK_CASTING = ENCHANTMENT_DEFERRED_REGISTER.register("quick_casting", QuickCasting::new);
        public static RegistryObject<Enchantment> DEVOTED = ENCHANTMENT_DEFERRED_REGISTER.register("devoted", Devoted::new);
        public static RegistryObject<Enchantment> VARIOUS = ENCHANTMENT_DEFERRED_REGISTER.register("various", Various::new);
        public static RegistryObject<Enchantment> KNOWLEDGE = ENCHANTMENT_DEFERRED_REGISTER.register("knowledge", Knowledge::new);
        public static RegistryObject<Enchantment> ANCIENT_WISDOM = ENCHANTMENT_DEFERRED_REGISTER.register("ancient_wisdom", AncientWisdom::new);

        public static void init() {
        }
    }
}
