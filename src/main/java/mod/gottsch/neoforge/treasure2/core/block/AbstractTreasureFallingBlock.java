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

import mod.gottsch.neo.gottschcore.world.WorldInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author Mark Gottschling on Feb 9, 2021
 */
public abstract class AbstractTreasureFallingBlock extends FallingBlock implements ITreasureBlock {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    protected static final VoxelShape BOUNDING_SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    protected static final VoxelShape COLLISION_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public AbstractTreasureFallingBlock(Block.Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(ACTIVATED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return BOUNDING_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (WorldInfo.isClientSide(level)) {
            return;
        }
        if (!(entity instanceof Player)) {
            return;
        }
        // activate, then trigger a fall tick
        level.setBlock(pos, defaultBlockState().setValue(ACTIVATED, Boolean.TRUE), 3);
        tick(level.getBlockState(pos), (ServerLevel) level, pos, level.getRandom());
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getBlockState(pos).getValue(ACTIVATED)) {
            return;
        }
        super.tick(state, level, pos, random);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean movedByPiston) {
        // do nothing — prevent vanilla FallingBlock auto-fall on neighbor change; we fall only when stepped on
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // do not spawn falling-dust particles
    }
}
