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
package mod.gottsch.neoforge.treasure2.datagen;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * @author Mark Gottschling on Sep 9, 2022
 */
public class ItemModelsProvider extends ItemModelProvider {

    public ItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Treasure.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // keys
        singleTexture(TreasureItems.WOOD_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/wood_key"));

        singleTexture(TreasureItems.STONE_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/stone_key"));

        singleTexture(TreasureItems.LEAF_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/leaf_key"));

        singleTexture(TreasureItems.EMBER_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/ember_key"));

        singleTexture(TreasureItems.LIGHTNING_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/lightning_key"));

        singleTexture(TreasureItems.IRON_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/iron_key"));

        singleTexture(TreasureItems.GOLD_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/gold_key"));

        singleTexture(TreasureItems.METALLURGISTS_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/metallurgists_key"));

        singleTexture(TreasureItems.DIAMOND_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/diamond_key"));

        singleTexture(TreasureItems.EMERALD_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/emerald_key"));

        singleTexture(TreasureItems.TOPAZ_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/topaz_key"));

        singleTexture(TreasureItems.ONYX_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/onyx_key"));

        singleTexture(TreasureItems.RUBY_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/ruby_key"));

        singleTexture(TreasureItems.SAPPHIRE_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/sapphire_key"));

        singleTexture(TreasureItems.JEWELLED_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/jewelled_key"));

        singleTexture(TreasureItems.SPIDER_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/spider_key"));

        singleTexture(TreasureItems.WITHER_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/wither_key"));

        singleTexture(TreasureItems.SKELETON_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/skeleton_key"));

        singleTexture(TreasureItems.BONE_KEY.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/bone_key"));

        singleTexture(TreasureItems.PILFERERS_LOCK_PICK.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/pilferers_lock_pick"));

        singleTexture(TreasureItems.THIEFS_LOCK_PICK.getId().getPath(),
                modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/thiefs_lock_pick"));

        // tab icon item
        singleTexture(TreasureItems.LOGO.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/treasure_tab"));

        // TODO: add ONE_KEY model when ported (uses vertical_left_key parent)

        // locks
        singleTexture(TreasureItems.WOOD_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/wood_lock"));

        singleTexture(TreasureItems.STONE_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/stone_lock"));

        singleTexture(TreasureItems.LEAF_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/leaf_lock"));

        singleTexture(TreasureItems.EMBER_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/ember_lock"));

        singleTexture(TreasureItems.IRON_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/iron_lock"));

        singleTexture(TreasureItems.GOLD_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/gold_lock"));

        singleTexture(TreasureItems.DIAMOND_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/diamond_lock"));

        singleTexture(TreasureItems.EMERALD_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/emerald_lock"));

        singleTexture(TreasureItems.TOPAZ_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/topaz_lock"));

        singleTexture(TreasureItems.ONYX_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/onyx_lock"));

        singleTexture(TreasureItems.RUBY_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/ruby_lock"));

        singleTexture(TreasureItems.SAPPHIRE_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/sapphire_lock"));

        singleTexture(TreasureItems.SPIDER_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/spider_lock"));

        singleTexture(TreasureItems.WITHER_LOCK.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/lock/wither_lock"));

        // TODO: add BONE_LOCK model when ported (not in tab but needs model to suppress warnings)

        // key ring
        singleTexture(TreasureItems.KEY_RING.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/key/key_ring"));

        // pouch
        singleTexture(TreasureItems.POUCH.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/pouch"));

        // wealth items — coins
        singleTexture(TreasureItems.COPPER_COIN.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/coin/copper_coin"));
        singleTexture(TreasureItems.SILVER_COIN.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/coin/silver_coin"));
        singleTexture(TreasureItems.GOLD_COIN.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/coin/gold_coin"));

        // wealth items — gems
        singleTexture(TreasureItems.TOPAZ.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/topaz"));
        singleTexture(TreasureItems.ONYX.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/onyx"));
        singleTexture(TreasureItems.RUBY.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/ruby"));
        singleTexture(TreasureItems.SAPPHIRE.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/sapphire"));
        singleTexture(TreasureItems.WHITE_PEARL.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/white_pearl"));
        singleTexture(TreasureItems.BLACK_PEARL.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/gem/black_pearl"));

        // clover (item)
        singleTexture(TreasureItems.CLOVER.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/clover"));

        // falling blocks (item models point at their block model)
        withExistingParent(TreasureItems.FALLING_GRASS.getId().getPath(), modLoc("block/falling_grass"));
        withExistingParent(TreasureItems.FALLING_SAND.getId().getPath(), modLoc("block/falling_sand"));
        withExistingParent(TreasureItems.FALLING_RED_SAND.getId().getPath(), modLoc("block/falling_red_sand"));

        // spanish moss item — cross-style block, item model needs the flat texture
        singleTexture(TreasureItems.SPANISH_MOSS_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("block/spanish_moss"));

        // strangle vines (head) — cross-style block, item uses the flat texture
        singleTexture(TreasureItems.STRANGLE_VINES.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("block/strangle_vines"));

        // gravestones — 19 block items, each points at its block model
        withExistingParent(TreasureItems.GRAVESTONE1_STONE_ITEM.getId().getPath(), modLoc("block/gravestone1_stone"));
        withExistingParent(TreasureItems.GRAVESTONE1_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone1_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE1_MOSSY_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone1_mossy_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE1_POLISHED_GRANITE_ITEM.getId().getPath(), modLoc("block/gravestone1_polished_granite"));
        withExistingParent(TreasureItems.GRAVESTONE1_OBSIDIAN_ITEM.getId().getPath(), modLoc("block/gravestone1_obsidian"));
        withExistingParent(TreasureItems.GRAVESTONE1_SMOOTH_QUARTZ_ITEM.getId().getPath(), modLoc("block/gravestone1_smooth_quartz"));
        withExistingParent(TreasureItems.GRAVESTONE2_STONE_ITEM.getId().getPath(), modLoc("block/gravestone2_stone"));
        withExistingParent(TreasureItems.GRAVESTONE2_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone2_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE2_MOSSY_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone2_mossy_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE2_POLISHED_GRANITE_ITEM.getId().getPath(), modLoc("block/gravestone2_polished_granite"));
        withExistingParent(TreasureItems.GRAVESTONE2_OBSIDIAN_ITEM.getId().getPath(), modLoc("block/gravestone2_obsidian"));
        withExistingParent(TreasureItems.GRAVESTONE2_SMOOTH_QUARTZ_ITEM.getId().getPath(), modLoc("block/gravestone2_smooth_quartz"));
        withExistingParent(TreasureItems.GRAVESTONE3_STONE_ITEM.getId().getPath(), modLoc("block/gravestone3_stone"));
        withExistingParent(TreasureItems.GRAVESTONE3_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone3_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE3_MOSSY_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone3_mossy_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE3_POLISHED_GRANITE_ITEM.getId().getPath(), modLoc("block/gravestone3_polished_granite"));
        withExistingParent(TreasureItems.GRAVESTONE3_OBSIDIAN_ITEM.getId().getPath(), modLoc("block/gravestone3_obsidian"));
        withExistingParent(TreasureItems.GRAVESTONE3_SMOOTH_QUARTZ_ITEM.getId().getPath(), modLoc("block/gravestone3_smooth_quartz"));
        withExistingParent(TreasureItems.SKULL_AND_CROSSBONES_ITEM.getId().getPath(), modLoc("block/skull_and_crossbones"));
        singleTexture(TreasureItems.SKELETON_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/skeleton_item"));

        // gravestone spawners — reuse the matching gravestone block models
        withExistingParent(TreasureItems.GRAVESTONE1_SPAWNER_STONE_ITEM.getId().getPath(), modLoc("block/gravestone1_stone"));
        withExistingParent(TreasureItems.GRAVESTONE2_SPAWNER_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/gravestone2_cobblestone"));
        withExistingParent(TreasureItems.GRAVESTONE3_SPAWNER_OBSIDIAN_ITEM.getId().getPath(), modLoc("block/gravestone3_obsidian"));

        // witherwood structure blocks
        withExistingParent(TreasureItems.WITHERWOOD_BROKEN_LOG_ITEM.getId().getPath(), modLoc("block/witherwood_broken_log"));
        withExistingParent(TreasureItems.WITHERWOOD_BRANCH_ITEM.getId().getPath(), modLoc("block/witherwood_branch1a"));
        singleTexture(TreasureItems.WITHERWOOD_ROOT_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/witherwood_root"));
        singleTexture(TreasureItems.WITHERWOOD_TWIG_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/witherwood_stick"));

        // witherwood woodset
        withExistingParent(TreasureItems.WITHERWOOD_LOG_ITEM.getId().getPath(), modLoc("block/witherwood_log"));
        withExistingParent(TreasureItems.WITHERWOOD_WOOD_ITEM.getId().getPath(), modLoc("block/witherwood_wood"));
        withExistingParent(TreasureItems.STRIPPED_WITHERWOOD_LOG_ITEM.getId().getPath(), modLoc("block/stripped_witherwood_log"));
        withExistingParent(TreasureItems.STRIPPED_WITHERWOOD_WOOD_ITEM.getId().getPath(), modLoc("block/stripped_witherwood_wood"));
        withExistingParent(TreasureItems.WITHERWOOD_PLANKS_ITEM.getId().getPath(), modLoc("block/witherwood_planks"));
        withExistingParent(TreasureItems.WITHERWOOD_SLAB_ITEM.getId().getPath(), modLoc("block/witherwood_slab"));
        withExistingParent(TreasureItems.WITHERWOOD_STAIRS_ITEM.getId().getPath(), modLoc("block/witherwood_stairs"));
        withExistingParent(TreasureItems.WITHERWOOD_FENCE_ITEM.getId().getPath(), modLoc("block/witherwood_fence_inventory"));
        withExistingParent(TreasureItems.WITHERWOOD_FENCE_GATE_ITEM.getId().getPath(), modLoc("block/witherwood_fence_gate"));
        withExistingParent(TreasureItems.WITHERWOOD_BUTTON_ITEM.getId().getPath(), modLoc("block/witherwood_button_inventory"));
        withExistingParent(TreasureItems.WITHERWOOD_PRESSURE_PLATE_ITEM.getId().getPath(), modLoc("block/witherwood_pressure_plate"));
        withExistingParent(TreasureItems.WITHERWOOD_TRAPDOOR_ITEM.getId().getPath(), modLoc("block/witherwood_trapdoor_bottom"));
        singleTexture(TreasureItems.WITHERWOOD_DOOR_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/witherwood_door"));

        // witherwood signs
        singleTexture(TreasureItems.WITHERWOOD_SIGN_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/witherwood_sign"));
        singleTexture(TreasureItems.WITHERWOOD_HANGING_SIGN_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/witherwood_hanging_sign"));

        // spawn eggs — all share vanilla's template_spawn_egg model (tints come from the item)
        withExistingParent(TreasureItems.BOUND_SOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.WITHERWOOD_GOLEM_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.WOOD_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.PIRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.VIKING_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.CAULDRON_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.CRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.MOLDY_CRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.CARDBOARD_BOX_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.MILK_CRATE_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.BARREL_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(TreasureItems.VANILLA_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        // TODO: restore TREASURE_TOOL, EYE_PATCH, weapons when ported
        // TODO: restore ore block items (TOPAZ_ORE_ITEM, etc.) when ported
        // TODO: restore witherwood block items when ported
        // TODO: restore wishing well block items when ported

        // chest block items
        withExistingParent(TreasureItems.WOOD_CHEST.getId().getPath(), modLoc("block/wood_chest"));
        withExistingParent(TreasureItems.CRATE_CHEST.getId().getPath(), modLoc("block/crate_chest"));
        withExistingParent(TreasureItems.MOLDY_CRATE_CHEST.getId().getPath(), modLoc("block/crate_chest_moldy"));
        withExistingParent(TreasureItems.IRONBOUND_CHEST.getId().getPath(), modLoc("block/ironbound_chest"));
        withExistingParent(TreasureItems.PIRATE_CHEST.getId().getPath(), modLoc("block/pirate_chest"));
        withExistingParent(TreasureItems.SAFE.getId().getPath(), modLoc("block/safe"));
        withExistingParent(TreasureItems.IRON_STRONGBOX.getId().getPath(), modLoc("block/iron_strongbox"));
        withExistingParent(TreasureItems.GOLD_STRONGBOX.getId().getPath(), modLoc("block/gold_strongbox"));
        withExistingParent(TreasureItems.DREAD_PIRATE_CHEST.getId().getPath(), modLoc("block/dread_pirate_chest"));
        withExistingParent(TreasureItems.COMPRESSOR_CHEST.getId().getPath(), modLoc("block/compressor_chest"));
        withExistingParent(TreasureItems.SKULL_CHEST.getId().getPath(), modLoc("block/skull_chest"));
        withExistingParent(TreasureItems.GOLD_SKULL_CHEST.getId().getPath(), modLoc("block/gold_skull_chest"));
        withExistingParent(TreasureItems.CRYSTAL_SKULL_CHEST.getId().getPath(), modLoc("block/crystal_skull_chest"));
        withExistingParent(TreasureItems.CAULDRON_CHEST.getId().getPath(), modLoc("block/cauldron_chest"));
        withExistingParent(TreasureItems.SPIDER_CHEST.getId().getPath(), modLoc("block/spider_chest"));
        withExistingParent(TreasureItems.VIKING_CHEST.getId().getPath(), modLoc("block/viking_chest"));
        withExistingParent(TreasureItems.CARDBOARD_BOX.getId().getPath(), modLoc("block/cardboard_box"));
        withExistingParent(TreasureItems.MILK_CRATE.getId().getPath(), modLoc("block/milk_crate"));
        withExistingParent(TreasureItems.BARREL_CHEST.getId().getPath(), modLoc("block/barrel_chest"));
        withExistingParent(TreasureItems.VANILLA_CHEST.getId().getPath(), modLoc("block/vanilla_chest"));
        withExistingParent(TreasureItems.WITHER_CHEST.getId().getPath(), modLoc("block/wither_chest"));
        withExistingParent(TreasureItems.BONE_CHEST.getId().getPath(), modLoc("block/bone_chest"));
        withExistingParent(TreasureItems.CELESTIAL_CHEST.getId().getPath(), modLoc("block/celestial_chest"));
        withExistingParent(TreasureItems.INFERNAL_CHEST.getId().getPath(), modLoc("block/infernal_chest"));

        // wishing wells
        withExistingParent(TreasureItems.WISHING_WELL_ITEM.getId().getPath(), modLoc("block/wishing_well_block"));
        withExistingParent(TreasureItems.WISHING_WELL_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/wishing_well_cobblestone_block"));
        withExistingParent(TreasureItems.WISHING_WELL_MOSSY_COBBLESTONE_ITEM.getId().getPath(), modLoc("block/wishing_well_mossy_cobblestone_block"));
        withExistingParent(TreasureItems.WISHING_WELL_STONE_BRICKS_ITEM.getId().getPath(), modLoc("block/wishing_well_stone_bricks_block"));
        withExistingParent(TreasureItems.WISHING_WELL_MOSSY_STONE_BRICKS_ITEM.getId().getPath(), modLoc("block/wishing_well_mossy_stone_bricks_block"));
        withExistingParent(TreasureItems.DESERT_WISHING_WELL_ITEM.getId().getPath(), modLoc("block/desert_wishing_well_block"));
    }
}
