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
package mod.gottsch.neoforge.treasure2.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.Set;

/**
 * @author Mark Gottschling on Nov 6, 2022
 */
public class TreasureBlockLootTables extends BlockLootSubProvider {

    public TreasureBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // TODO: restore witherwood/clover block loot tables when those blocks are ported
        // e.g. this.add(TreasureBlocks.WITHERWOOD_LOG.get(), block -> createSingleItemTable(TreasureItems.WITHERWOOD_LOG.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Collections.emptyList();
    }
}
