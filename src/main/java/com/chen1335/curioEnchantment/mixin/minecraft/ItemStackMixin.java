package com.chen1335.curioEnchantment.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackExtension, DataComponentHolder {

    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    private boolean isEnchantable(boolean original) {
        if (!original) {
            ItemEnchantments itemenchantments = this.get(DataComponents.ENCHANTMENTS);
            if (!CuriosApi.getItemStackSlots((ItemStack) (Object) this, false).keySet().isEmpty() && itemenchantments != null && itemenchantments.isEmpty()) {
                return true;
            }
        }
        return original;
    }
}
