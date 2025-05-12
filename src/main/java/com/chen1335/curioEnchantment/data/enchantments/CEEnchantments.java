package com.chen1335.curioEnchantment.data.enchantments;

import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.API.objects.Tags;
import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import com.chen1335.curioEnchantment.CurioEnchantment;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public class CEEnchantments extends EnchantmentsProvider {
    public static final ResourceKey<Enchantment> SOLID = key("solid");
    public static final ResourceKey<Enchantment> GROW = key("grow");
    public static final ResourceKey<Enchantment> LIGHT = key("light");
    public static final ResourceKey<Enchantment> QUICK_CLAW = key("quick_claw");

    public void bootstrap(BootstrapContext<Enchantment> context) {
        HolderSet.Named<Item> curioEnchantable = context.lookup(Registries.ITEM).getOrThrow(Tags.ItemTags.CURIOS_ENCHANTABLE);
        HolderSet.Named<Item> handsEnchantable = context.lookup(Registries.ITEM).getOrThrow(Tags.ItemTags.HANDS_ENCHANTABLE);


        register(context,
                QUICK_CLAW,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        handsEnchantable,
                                        10,
                                        4,
                                        Enchantment.dynamicCost(15, 6),
                                        Enchantment.dynamicCost(20, 11),
                                        1
                                )
                        )
                        .withEffect(DataComponentTypes.CURIO_ATTRIBUTE.value(), new CurioEnchantmentAttributeEffect(Attributes.MINING_EFFICIENCY, LevelBasedValue.perLevel(0.02F), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
        );

        register(context,
                LIGHT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        curioEnchantable,
                                        10,
                                        2,
                                        Enchantment.dynamicCost(15, 10),
                                        Enchantment.dynamicCost(20, 11),
                                        1
                                )
                        )
                        .withEffect(DataComponentTypes.CURIO_ATTRIBUTE.value(), new CurioEnchantmentAttributeEffect(Attributes.MOVEMENT_SPEED, LevelBasedValue.perLevel(0.01F), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
        );

        register(context,
                SOLID,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        curioEnchantable,
                                        10,
                                        4,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(12, 11),
                                        1
                                )
                        )
                        .withEffect(DataComponentTypes.CURIO_ATTRIBUTE.value(), new CurioEnchantmentAttributeEffect(Attributes.ARMOR, LevelBasedValue.perLevel(0.25F), AttributeModifier.Operation.ADD_VALUE))
        );

        register(context,
                GROW,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        curioEnchantable,
                                        6,
                                        5,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(12, 11),
                                        1
                                )
                        )
                        .withEffect(DataComponentTypes.CURIO_ATTRIBUTE.value(), new CurioEnchantmentAttributeEffect(Attributes.MAX_HEALTH, LevelBasedValue.perLevel(0.2F), AttributeModifier.Operation.ADD_VALUE))

        );
    }


    @Override
    protected void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        HolderSet.Named<Enchantment> exclusive = context.lookup(Registries.ENCHANTMENT).getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CurioEnchantment.MODID, key.location().getPath() + "_exclusive")));
        builder.exclusiveWith(exclusive);
        super.register(context, key, builder);
    }

    @Override
    String getModId() {
        return CurioEnchantment.MODID;
    }
}
