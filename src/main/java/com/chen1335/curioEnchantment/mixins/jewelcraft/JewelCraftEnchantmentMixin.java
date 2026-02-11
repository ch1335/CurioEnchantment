package com.chen1335.curioEnchantment.mixins.jewelcraft;

import net.minecraft.world.item.ItemStack;
import net.salju.jewelcraft.enchantment.AmuletEnchantment;
import net.salju.jewelcraft.enchantment.JewelryEnchantment;
import net.salju.jewelcraft.enchantment.RingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;

import java.util.Map;

@Mixin(value = {AmuletEnchantment.class, JewelryEnchantment.class, RingEnchantment.class})
public class JewelCraftEnchantmentMixin {
    @Inject(method = "canApplyAtEnchantingTable", at = @At("RETURN"), cancellable = true, remap = false)
    private void canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Map<String, ISlotType> slots = CuriosApi.getItemStackSlots(stack, false);

        if (!cir.getReturnValue() && this.getClass().equals(AmuletEnchantment.class) && slots.get("charm") != null) {
            cir.setReturnValue(true);
        }
        if (!cir.getReturnValue() && this.getClass().equals(RingEnchantment.class) && slots.get("ring") != null) {
            cir.setReturnValue(true);
        }
        if (!cir.getReturnValue() && this.getClass().equals(JewelryEnchantment.class) && (slots.get("ring") != null || slots.get("charm") != null)) {
            cir.setReturnValue(true);
        }
    }
}
