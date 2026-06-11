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
import mod.gottsch.neoforge.treasure2.core.block.entity.StructureNeighborDependentStateMarkerBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Meant for Structure NBTs only as a marker for where blocks that contain
 * neighbor-dependent state will be placed (e.g. Walls). On its first server tick the
 * paired block entity resolves the target block and re-applies its connected state.
 *
 * @author by Mark Gottschling on 8/15/2025
 */
public class StructureNeighborDependentStateMarkerBlock extends BaseEntityBlock implements ITreasureBlock {
    public static final MapCodec<StructureNeighborDependentStateMarkerBlock> CODEC =
            simpleCodec(StructureNeighborDependentStateMarkerBlock::new);

    public StructureNeighborDependentStateMarkerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StructureNeighborDependentStateMarkerBlockEntity(
                TreasureBlockEntities.STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER.get(), pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof StructureNeighborDependentStateMarkerBlockEntity entity) {
                entity.tickServer();
            }
        };
    }
}
