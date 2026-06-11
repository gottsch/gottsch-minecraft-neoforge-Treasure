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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Head (growing tip) block for Strangle Vines.
 *
 * @author Mark Gottschling on Aug 27, 2024
 *
 * TODO: restore animateTick spore-blossom particles once particle suite is ported.
 * TODO: re-fire CommonHooks.onCropsGrowPre/Post around growth so other mods can intercept.
 */
public class StrangleVinesBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<StrangleVinesBlock> CODEC = simpleCodec(StrangleVinesBlock::new);

    private static final int MAX_HEIGHT = 10;
    public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    private static final double GROW_PER_TICK_PROBABILITY = 0.1D;

    public StrangleVinesBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, false, GROW_PER_TICK_PROBABILITY);
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < 25 && random.nextDouble() < GROW_PER_TICK_PROBABILITY) {
            int height = this.getHeightBelowUpToMax(level, pos) + 1;
            if (height >= MAX_HEIGHT) {
                state.setValue(AGE, MAX_AGE);
                return;
            }
            BlockPos belowPos = pos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(belowPos))) {
                level.setBlockAndUpdate(belowPos, this.getGrowIntoState(state, level.random));
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        int height = this.getHeightBelowUpToMax(level, pos) + 1;
        if (height >= MAX_HEIGHT) {
            state.setValue(AGE, MAX_AGE);
        }
        return height < MAX_HEIGHT;
    }

    protected int getHeightBelowUpToMax(BlockGetter getter, BlockPos pos) {
        int i;
        for (i = 0; i < MAX_HEIGHT && getter.getBlockState(pos.below(i + 1)).is(getBodyBlock()); ++i) {
        }
        return i;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }

    @Override
    protected Block getBodyBlock() {
        return TreasureBlocks.STRANGLE_VINES_PLANT.get();
    }
}
