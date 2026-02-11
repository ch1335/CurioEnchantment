package com.chen1335.curioEnchantment.common.capability;

import com.chen1335.curioEnchantment.API.Object.Capabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerData implements ICapabilityProvider {

    private final LazyOptional<PlayerData> holder = LazyOptional.of(() -> this);

    public String LastCastSpell = "";

    public int sameCastSpellCount = 0;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return Capabilities.PLAYER_DATA.orEmpty(cap, holder);
    }
}
