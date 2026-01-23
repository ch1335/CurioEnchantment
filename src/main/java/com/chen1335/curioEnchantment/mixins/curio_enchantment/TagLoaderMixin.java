package com.chen1335.curioEnchantment.mixins.curio_enchantment;

import com.chen1335.curioEnchantment.API.objects.CETags;
import com.chen1335.curioEnchantment.CurioEnchantment;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.*;

@Mixin(TagLoader.class)
public class TagLoaderMixin {
    @Shadow
    @Final
    private String directory;

    @ModifyReturnValue(method = "load", at = @At("RETURN"))
    private Map<ResourceLocation, List<TagLoader.EntryWithSource>> load(Map<ResourceLocation, List<TagLoader.EntryWithSource>> original, @Local(argsOnly = true) ResourceManager resourceManager) {


        if (directory.equals("tags/item")) {
            final Set<String> slots = new HashSet<>();
            resourceManager.listPacks().forEach(packResources -> {
                Set<String> namespaces = packResources.getNamespaces(PackType.SERVER_DATA);
                namespaces.forEach((namespace) -> {
                    packResources.listResources(PackType.SERVER_DATA, namespace, "curios/slots", (resourceLocation, inputStreamIoSupplier) -> {
                        String path = resourceLocation.getPath();
                        String slotName = path.substring("curios/slots/".length(), path.length() - ".json".length());
                        slots.add(slotName);
                    });
                });
            });

            CurioEnchantment.LOGGER.info("Discover {} possible curios type:{}", slots.size(), slots);
            slots.forEach(id -> {
                TagEntry tagEntry = TagEntry.optionalTag(ResourceLocation.fromNamespaceAndPath("curios", id));
                TagLoader.EntryWithSource entryWithSource = new TagLoader.EntryWithSource(tagEntry, "curio_enchantment");
                original.computeIfAbsent(CETags.ItemTags.CURIOS_ENCHANTABLE.location(), (resourceLocation) -> new ArrayList<>()).add(entryWithSource);
                original.computeIfAbsent(ResourceLocation.fromNamespaceAndPath(CurioEnchantment.MODID, id + "_enchantable"), (resourceLocation) -> new ArrayList<>()).add(entryWithSource);

            });
        }
        return original;
    }

}
