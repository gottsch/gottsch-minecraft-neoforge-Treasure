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
import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.particle.CollidingParticleType;
import mod.gottsch.neoforge.treasure2.core.particle.TreasureParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author Mark Gottschling on Jul 25, 2018
 *
 * TODO: restore poison-fog/spanish-moss particle effects in animateTick once
 *   SpanishMossParticle.Provider and CollidingParticleType (+ POISON_MIST_PARTICLE) are ported.
 */
public class SpanishMossBlock extends BushBlock implements ITreasureBlock {
    public static final MapCodec<SpanishMossBlock> CODEC = simpleCodec(SpanishMossBlock::new);
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);

    public SpanishMossBlock(Block.Properties properties) {
        super(properties.strength(0.6F).noCollission().instabreak().sound(SoundType.WET_GRASS));
        registerDefaultState(stateDefinition.any().setValue(ACTIVATED, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {

        // NOTE moss emits poisonous fog when activated; gated by a server config option.
        if (!Config.SERVER.witherTree.enablePoisonFog.get()) {
            return;
        }

        if (!state.getValue(ACTIVATED)) {
            return;
        }

        if (RandomHelper.checkProbability(new java.util.Random(), 75D)) {
            return;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // initial positions - has a spread area of up to 1.5 blocks
        double xPos = (x + 0.5D);
        double yPos = y - 0.1D;
        double zPos = (z + 0.5D);
        // initial velocities
        double velocityX = 0;
        double velocityY = -0.1; //0
        double velocityZ = 0;

        try {
            if (world.random.nextInt(10) < 8) {
                world.addParticle(TreasureParticles.SPANISH_MOSS_PARTICLE.get(), false, xPos, yPos, zPos, velocityX, velocityY, velocityZ);
            } else {
                CollidingParticleType mistType = TreasureParticles.POISON_MIST_PARTICLE.get();
                world.addParticle(mistType, false, xPos, yPos, zPos, velocityX, velocityY, velocityZ);
            }
        }
        catch(Exception e) {
            Treasure.LOGGER.error("error with particle:", e);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.AIR;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
