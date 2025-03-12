package com.chen1335.curioEnchantment.mixin.ironsSpellBooks;

import com.llamalad7.mixinextras.sugar.Local;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastResult;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractSpell.class)
public class AbstractSpellMixin {
    @ModifyVariable(method = "attemptInitiateCast", at = @At(value = "STORE", ordinal = 0))
    private CastResult modifyCastResult(CastResult value, @Local ServerPlayer serverPlayer, @Local(argsOnly = true) CastSource castSource) {
        if (value.type == CastResult.Type.FAILURE && value.message != null) {

        }
        return value;
    }
}
