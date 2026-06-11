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

import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import net.minecraft.util.RandomSource;

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

    // NOTE the Forge version overrode buildLocks() to force a single BONE_LOCK. The bone lock item is
    // not yet ported (see TreasureItems#BONE_LOCK, currently commented out), so the bone chest falls
    // back to the default rarity-based lock selection (1 lock, per randomizedNumberOfLocks above).
    // TODO restore the forced single bone lock once TreasureItems.BONE_LOCK is ported.
}
