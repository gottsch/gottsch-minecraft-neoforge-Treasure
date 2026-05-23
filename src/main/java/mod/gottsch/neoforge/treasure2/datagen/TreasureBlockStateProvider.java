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
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * @author Mark Gottschling on Aug 26, 2024
 */
public class TreasureBlockStateProvider extends BlockStateProvider {

    public TreasureBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Treasure.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // TODO: restore witherwood sign and hanging sign blockstates when witherwood blocks are ported:
        //   signBlock(witherwood_sign, witherwood_wall_sign, blockTexture(witherwood_planks))
        //   hangingSignBlock(witherwood_hanging_sign, witherwood_wall_hanging_sign, blockTexture(witherwood_planks))
    }
}
