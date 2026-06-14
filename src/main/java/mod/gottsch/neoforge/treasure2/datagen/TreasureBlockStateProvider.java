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
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        // gem ores — blockstate + item model reference the existing static block models
        oreBlock(TreasureBlocks.TOPAZ_ORE.get(), "topaz_ore");
        oreBlock(TreasureBlocks.DEEPSLATE_TOPAZ_ORE.get(), "deepslate_topaz_ore");
        oreBlock(TreasureBlocks.ONYX_ORE.get(), "onyx_ore");
        oreBlock(TreasureBlocks.DEEPSLATE_ONYX_ORE.get(), "deepslate_onyx_ore");
        oreBlock(TreasureBlocks.RUBY_ORE.get(), "ruby_ore");
        oreBlock(TreasureBlocks.DEEPSLATE_RUBY_ORE.get(), "deepslate_ruby_ore");
        oreBlock(TreasureBlocks.SAPPHIRE_ORE.get(), "sapphire_ore");
        oreBlock(TreasureBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), "deepslate_sapphire_ore");

        // TODO: restore witherwood sign and hanging sign blockstates when witherwood blocks are ported:
        //   signBlock(witherwood_sign, witherwood_wall_sign, blockTexture(witherwood_planks))
        //   hangingSignBlock(witherwood_hanging_sign, witherwood_wall_hanging_sign, blockTexture(witherwood_planks))
    }

    /**
     * Wires a block's blockstate + item model to an already-present static block model.
     */
    private void oreBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }
}
