package com.chen1335.curioEnchantment.mixins.curio_enchantment;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow
    public abstract ItemStack getDefaultInstance();

    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    private void getEnchantmentValue(CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() <= 0 && !CuriosApi.getItemStackSlots(this.getDefaultInstance(), false).isEmpty()) {
            cir.setReturnValue(10);
        }
    }

    @Inject(method = "isEnchantable", at = @At("RETURN"), cancellable = true)
    private void isEnchantable(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && !CuriosApi.getItemStackSlots(this.getDefaultInstance(), false).isEmpty()) {
            cir.setReturnValue(true);
        }
    }
}
