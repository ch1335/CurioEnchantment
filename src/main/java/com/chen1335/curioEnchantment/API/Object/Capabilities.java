package com.chen1335.curioEnchantment.API.Object;

import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.common.capability.PlayerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final ResourceLocation PLAYER_DATA_RESOURCELOCATION = new ResourceLocation(CurioEnchantment.MODID,"player_data");
    public static Capability<PlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
}
