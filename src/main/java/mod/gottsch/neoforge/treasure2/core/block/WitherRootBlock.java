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
import mod.gottsch.neo.gottschcore.block.FacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 *
 * @author Mark Gottschling on Mar 26, 2018
 *
 * TODO: restore animateTick wither mist particles once WITHER_MIST_PARTICLE
 *   (CollidingParticleType) sprite provider is ported. Gated by ACTIVATED +
 *   Config.SERVER.witherTree.enableWitherFog + IMistSupport.isMistAllowed.
 */
public class WitherRootBlock extends FacingBlock implements ITreasureBlock, IMistSupport {
	public static final MapCodec<WitherRootBlock> CODEC = simpleCodec(WitherRootBlock::new);

	public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

	/*
	 * An array of VoxelShape shapes for the bounding box
	 */
	private VoxelShape[] shapes = new VoxelShape[4];

	/**
	 *
	 * @param properties
	 */
	public WitherRootBlock(Block.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(3.0F));
		registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(ACTIVATED, Boolean.FALSE));
		setShapes(
			new VoxelShape[] {
					Block.box(0.01, 0, 0.01, 15.99, 12, 15.99),	// S
					Block.box(0.01, 0, 0.01, 15.99, 12, 15.99),	// W
					Block.box(0.01, 0, 0.01, 15.99, 12, 15.99),	// N
					Block.box(0.01, 0, 0.01, 15.99, 12, 15.99)	// E
			});
	}

	@Override
	protected MapCodec<? extends WitherRootBlock> codec() {
		return CODEC;
	}

	/**
	 *
	 */
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED, FACING);
	}

	/**
	 * TODO: particles stubbed until WITHER_MIST_PARTICLE is ported. See class javadoc.
	 */
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		// no-op (mist particles not yet ported)
	}

	/**
	 *
	 */
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(FACING)) {
		default:
		case NORTH:
			return shapes[0];
		case EAST:
			return shapes[1];
		case SOUTH:
			return shapes[2];
		case WEST:
			return shapes[3];
		}
	}

	/**
	 *
	 * @return
	 */
	public VoxelShape[] getShapes() {
		return shapes;
	}

	public WitherRootBlock setShapes(VoxelShape[] shapes) {
		this.shapes = shapes;
		return this;
	}
}
