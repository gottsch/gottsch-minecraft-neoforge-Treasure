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

        // TODO: restore TREASURE_TOOL, coins, gems, EYE_PATCH, CLOVER, weapons, spawn eggs when ported
        // TODO: restore ore block items (TOPAZ_ORE_ITEM, etc.) when ported
        // TODO: restore witherwood block items when ported
        // TODO: restore wishing well block items when ported

        // chest block items — only WOOD_CHEST has a registered block item so far
        withExistingParent(TreasureItems.WOOD_CHEST.getId().getPath(), modLoc("block/wood_chest"));
        // TODO: add remaining chest block items once their block items are registered in TreasureItems
    }
}
