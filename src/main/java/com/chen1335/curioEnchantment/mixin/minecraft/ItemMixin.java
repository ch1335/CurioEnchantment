package com.chen1335.curioEnchantment.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow
    public abstract ItemStack getDefaultInstance();

    @ModifyReturnValue(method = "getEnchantmentValue", at = @At("RETURN"))
    private int getEnchantmentValue(int original) {

        if (original <= 0 && !CuriosApi.getItemStackSlots(this.getDefaultInstance(), false).isEmpty()) {
            return 10;
        }
        return original;
    }
}
