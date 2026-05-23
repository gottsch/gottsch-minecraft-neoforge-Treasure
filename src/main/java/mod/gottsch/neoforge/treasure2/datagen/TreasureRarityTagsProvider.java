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
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.tag.TreasureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 * @author by Mark Gottschling on 8/23/2025
 */
public class TreasureRarityTagsProvider extends TagsProvider<IRarity> {

    public TreasureRarityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,
                                      ExistingFileHelper existingFileHelper) {
        super(output, TreasureRarities.RARITIES_REGISTRY_KEY, provider, Treasure.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TreasureTags.Rarities.ALL_RARITIES)
                .add(TreasureRarities.COMMON.getKey())
                .add(TreasureRarities.UNCOMMON.getKey())
                .add(TreasureRarities.SCARCE.getKey())
                .add(TreasureRarities.RARE.getKey())
                .add(TreasureRarities.EPIC.getKey())
                .add(TreasureRarities.LEGENDARY.getKey())
                .add(TreasureRarities.MYTHICAL.getKey());

        tag(TreasureTags.Rarities.SURFACE_CHEST_RARITIES)
                .add(TreasureRarities.COMMON.getKey())
                .add(TreasureRarities.UNCOMMON.getKey())
                .add(TreasureRarities.SCARCE.getKey());
    }
}
