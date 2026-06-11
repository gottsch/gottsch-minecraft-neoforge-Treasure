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
package mod.gottsch.neoforge.treasure2.core.creativetab;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author Mark Gottschling on May 22, 2025
 */
public class TreasureCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Treasure.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TREASURE_TAB =
            CREATIVE_MODE_TABS.register("treasure2", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Treasure.MODID))
                    .icon(() -> new ItemStack(TreasureItems.LOGO.get()))
                    .displayItems((params, output) -> {
                        // keys
                        output.accept(TreasureItems.WOOD_KEY.get());
                        output.accept(TreasureItems.STONE_KEY.get());
                        output.accept(TreasureItems.LEAF_KEY.get());
                        output.accept(TreasureItems.EMBER_KEY.get());
                        output.accept(TreasureItems.LIGHTNING_KEY.get());
                        output.accept(TreasureItems.IRON_KEY.get());
                        output.accept(TreasureItems.GOLD_KEY.get());
                        output.accept(TreasureItems.METALLURGISTS_KEY.get());
                        output.accept(TreasureItems.DIAMOND_KEY.get());
                        output.accept(TreasureItems.EMERALD_KEY.get());
                        output.accept(TreasureItems.TOPAZ_KEY.get());
                        output.accept(TreasureItems.ONYX_KEY.get());
                        output.accept(TreasureItems.RUBY_KEY.get());
                        output.accept(TreasureItems.SAPPHIRE_KEY.get());
                        output.accept(TreasureItems.JEWELLED_KEY.get());
                        output.accept(TreasureItems.SPIDER_KEY.get());
                        output.accept(TreasureItems.WITHER_KEY.get());
                        output.accept(TreasureItems.SKELETON_KEY.get());
                        output.accept(TreasureItems.PILFERERS_LOCK_PICK.get());
                        output.accept(TreasureItems.THIEFS_LOCK_PICK.get());
                        output.accept(TreasureItems.BONE_KEY.get());
                        // TODO: ONE_KEY when ported

                        // locks
                        output.accept(TreasureItems.WOOD_LOCK.get());
                        output.accept(TreasureItems.STONE_LOCK.get());
                        output.accept(TreasureItems.LEAF_LOCK.get());
                        output.accept(TreasureItems.EMBER_LOCK.get());
                        output.accept(TreasureItems.IRON_LOCK.get());
                        output.accept(TreasureItems.GOLD_LOCK.get());
                        output.accept(TreasureItems.DIAMOND_LOCK.get());
                        output.accept(TreasureItems.EMERALD_LOCK.get());
                        output.accept(TreasureItems.TOPAZ_LOCK.get());
                        output.accept(TreasureItems.ONYX_LOCK.get());
                        output.accept(TreasureItems.RUBY_LOCK.get());
                        output.accept(TreasureItems.SAPPHIRE_LOCK.get());
                        output.accept(TreasureItems.SPIDER_LOCK.get());
                        output.accept(TreasureItems.WITHER_LOCK.get());
                        // TODO: BONE_LOCK when ported

                        // utility
                        output.accept(TreasureItems.KEY_RING.get());
                        output.accept(TreasureItems.POUCH.get());
                        // TODO: TREASURE_TOOL, EYE_PATCH, CLOVER when ported

                        // wealth items
                        output.accept(TreasureItems.COPPER_COIN.get());
                        output.accept(TreasureItems.SILVER_COIN.get());
                        output.accept(TreasureItems.GOLD_COIN.get());
                        output.accept(TreasureItems.TOPAZ.get());
                        output.accept(TreasureItems.ONYX.get());
                        output.accept(TreasureItems.RUBY.get());
                        output.accept(TreasureItems.SAPPHIRE.get());
                        output.accept(TreasureItems.WHITE_PEARL.get());
                        output.accept(TreasureItems.BLACK_PEARL.get());

                        // clover
                        output.accept(TreasureItems.CLOVER.get());

                        // decorative — falling blocks + spanish moss
                        output.accept(TreasureItems.FALLING_GRASS.get());
                        output.accept(TreasureItems.FALLING_SAND.get());
                        output.accept(TreasureItems.FALLING_RED_SAND.get());
                        output.accept(TreasureItems.SPANISH_MOSS_ITEM.get());
                        output.accept(TreasureItems.STRANGLE_VINES.get());

                        // gravestones
                        output.accept(TreasureItems.GRAVESTONE1_STONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE1_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE1_MOSSY_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE1_POLISHED_GRANITE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE1_OBSIDIAN_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE1_SMOOTH_QUARTZ_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_STONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_MOSSY_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_POLISHED_GRANITE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_OBSIDIAN_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_SMOOTH_QUARTZ_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_STONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_MOSSY_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_POLISHED_GRANITE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_OBSIDIAN_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_SMOOTH_QUARTZ_ITEM.get());
                        output.accept(TreasureItems.SKULL_AND_CROSSBONES_ITEM.get());
                        output.accept(TreasureItems.SKELETON_ITEM.get());
                        // gravestone spawners
                        output.accept(TreasureItems.GRAVESTONE1_SPAWNER_STONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE2_SPAWNER_COBBLESTONE_ITEM.get());
                        output.accept(TreasureItems.GRAVESTONE3_SPAWNER_OBSIDIAN_ITEM.get());
                        // witherwood structure blocks
                        output.accept(TreasureItems.WITHERWOOD_BROKEN_LOG_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_BRANCH_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_ROOT_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_TWIG_ITEM.get());
                        // witherwood woodset
                        output.accept(TreasureItems.WITHERWOOD_LOG_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_WOOD_ITEM.get());
                        output.accept(TreasureItems.STRIPPED_WITHERWOOD_LOG_ITEM.get());
                        output.accept(TreasureItems.STRIPPED_WITHERWOOD_WOOD_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_PLANKS_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_SLAB_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_STAIRS_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_FENCE_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_FENCE_GATE_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_BUTTON_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_PRESSURE_PLATE_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_DOOR_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_TRAPDOOR_ITEM.get());
                        // witherwood signs
                        output.accept(TreasureItems.WITHERWOOD_SIGN_ITEM.get());
                        output.accept(TreasureItems.WITHERWOOD_HANGING_SIGN_ITEM.get());

                        // chests
                        output.accept(TreasureItems.WOOD_CHEST.get());
                        output.accept(TreasureItems.CRATE_CHEST.get());
                        output.accept(TreasureItems.MOLDY_CRATE_CHEST.get());
                        output.accept(TreasureItems.IRONBOUND_CHEST.get());
                        output.accept(TreasureItems.PIRATE_CHEST.get());
                        output.accept(TreasureItems.SAFE.get());
                        output.accept(TreasureItems.IRON_STRONGBOX.get());
                        output.accept(TreasureItems.GOLD_STRONGBOX.get());
                        output.accept(TreasureItems.DREAD_PIRATE_CHEST.get());
                        output.accept(TreasureItems.COMPRESSOR_CHEST.get());
                        output.accept(TreasureItems.SKULL_CHEST.get());
                        output.accept(TreasureItems.GOLD_SKULL_CHEST.get());
                        output.accept(TreasureItems.CRYSTAL_SKULL_CHEST.get());
                        output.accept(TreasureItems.CAULDRON_CHEST.get());
                        output.accept(TreasureItems.SPIDER_CHEST.get());
                        output.accept(TreasureItems.VIKING_CHEST.get());
                        output.accept(TreasureItems.CARDBOARD_BOX.get());
                        output.accept(TreasureItems.MILK_CRATE.get());
                        output.accept(TreasureItems.BARREL_CHEST.get());
                        output.accept(TreasureItems.VANILLA_CHEST.get());
                        output.accept(TreasureItems.WITHER_CHEST.get());
                        output.accept(TreasureItems.BONE_CHEST.get());
                        output.accept(TreasureItems.CELESTIAL_CHEST.get());
                        output.accept(TreasureItems.INFERNAL_CHEST.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
