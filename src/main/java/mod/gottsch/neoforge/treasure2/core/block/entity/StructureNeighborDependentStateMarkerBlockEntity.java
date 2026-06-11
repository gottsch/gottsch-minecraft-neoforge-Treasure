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
package mod.gottsch.neoforge.treasure2.core.block.entity;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.SyntheticBlockPlaceContext;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Meant to be used in Structure NBTs only as a marker for where blocks that contain
 * neighbor-dependent state will be placed (e.g. Walls). On its first server tick it
 * resolves the target block and, if it is a wall, recomputes its connected state.
 *
 * @author by Mark Gottschling on 8/15/2025
 */
public class StructureNeighborDependentStateMarkerBlockEntity extends BlockEntity {
    public static final String TARGET_BLOCK = "targetBlock";

    private ResourceLocation targetBlock;
    private boolean processed = false;

    public StructureNeighborDependentStateMarkerBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER.get(), pos, state);
    }

    public StructureNeighborDependentStateMarkerBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public void tickServer() {
        if (level.isClientSide() || this.processed) {
            return;
        }
        this.processed = true;

        Optional.ofNullable(this.targetBlock)
                .flatMap(BuiltInRegistries.BLOCK::getOptional)
                .map(Block::defaultBlockState)
                .filter(state -> state.is(BlockTags.WALLS))
                .map(state -> {
                    SyntheticBlockPlaceContext context = new SyntheticBlockPlaceContext(level, getBlockPos());
                    return state.getBlock().getStateForPlacement(context);
                })
                .ifPresent(updatedState -> level.setBlock(getBlockPos(), updatedState, 3));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        try {
            if (tag.contains(TARGET_BLOCK)) {
                this.targetBlock = ModUtil.asLocation(tag.getString(TARGET_BLOCK));
            }
        } catch (Exception e) {
            Treasure.LOGGER.error("error reading StructureNeighborDependentStateMarkerBlockEntity properties from tag:", e);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        Optional.ofNullable(getTargetBlock())
                .ifPresent(block -> tag.putString(TARGET_BLOCK, block.toString()));
    }

    public ResourceLocation getTargetBlock() {
        return targetBlock;
    }

    public void setTargetBlock(ResourceLocation targetBlock) {
        this.targetBlock = targetBlock;
    }
}
