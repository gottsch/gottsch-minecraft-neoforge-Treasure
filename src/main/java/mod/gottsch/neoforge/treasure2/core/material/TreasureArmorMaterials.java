/*
 * This file is part of Treasure2.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the Open Software Licence 3.0.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * Open Software Licence 3.0 for more details.
 *
 * You should have received a copy of the Open Software Licence
 * along with Treasure2. If not, see <https://www.tldrlegal.com/license/open-software-licence-3-0>.
 */
package mod.gottsch.neoforge.treasure2.core.material;

import java.util.EnumMap;
import java.util.List;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 1.21 port of Forge's {@code TreasureArmorMaterial} enum. In 1.21 {@code ArmorMaterial} is a
 * record registered to the {@code ARMOR_MATERIAL} registry; durability moved off the material onto
 * the item's {@link net.minecraft.world.item.Item.Properties}.
 *
 * @author Mark Gottschling
 */
public class TreasureArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, Treasure.MODID);

    /**
     * Eye patch — a dyeable vanity helmet. Mirrors vanilla leather's two-layer (dyeable base +
     * non-dyeable overlay) texture pattern; the layer textures already ship at
     * {@code textures/models/armor/eye_patch_layer_{1,2}{,_overlay}.png}.
     */
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EYE_PATCH = ARMOR_MATERIALS.register("eye_patch",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 2);
                        map.put(ArmorItem.Type.CHESTPLATE, 3);
                        map.put(ArmorItem.Type.HELMET, 1);
                    }),
                    15,
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.of(Items.IRON_INGOT),
                    List.of(
                            new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "eye_patch"), "", true),
                            new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "eye_patch"), "_overlay", false)
                    ),
                    0.0F,
                    0.0F));

    public static void register(IEventBus modEventBus) {
        ARMOR_MATERIALS.register(modEventBus);
    }
}
