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
package mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest;

import mod.gottsch.neo.gottschcore.spatial.Rotate;
import mod.gottsch.neoforge.treasure2.core.item.LockItem;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.RandomSource;

import java.util.List;

/**
 * @author by Mark Gottschling on 9/17/2025
 */
public class BoneChestSubprocessor extends ChestSubprocessor {

    public BoneChestSubprocessor() {
    }

    public BoneChestSubprocessor(ChestSubprocessorData data) {
        super(data);
    }

    @Override
    public int randomizedNumberOfLocks(RandomSource random, LockLayout lockLayout) {
        return 1;
    }

    @Override
    public List<LockState> buildLocks(RandomSource random, LockLayout layout, IRarity defaultRarity, Rotate rotate, HolderLookup.Provider provider) {
        // determine the number of locks to use
        int numLocks = randomizedNumberOfLocks(random, layout);
        // force chest to use a single bone lock
        return buildLocks(random, layout, List.<LockItem>of(TreasureItems.BONE_LOCK.get()), numLocks, rotate);
    }
}
