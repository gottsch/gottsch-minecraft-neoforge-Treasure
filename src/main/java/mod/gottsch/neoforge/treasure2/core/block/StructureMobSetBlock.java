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
package mod.gottsch.neoforge.treasure2.core.block;

import com.mojang.serialization.MapCodec;
import mod.gottsch.neoforge.treasure2.core.block.entity.StructureMobSetBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Meant for Jigsaw Structures only. Acts as a marker and stores data for
 * ProximityMobSetSpawnerBlocks. A ProximityMobSetProcessor replaces this block with the
 * ProximityMobSetSpawnBlock, using the stored data to configure the new block.
 *
 * @author by Mark Gottschling on 8/14/2025
 */
public class StructureMobSetBlock extends BaseEntityBlock implements ITreasureBlock {
    public static final MapCodec<StructureMobSetBlock> CODEC = simpleCodec(StructureMobSetBlock::new);

    public StructureMobSetBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StructureMobSetBlockEntity(
                TreasureBlockEntities.STRUCTURE_MOB_SET.get(), pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
