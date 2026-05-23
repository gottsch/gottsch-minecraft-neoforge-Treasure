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

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Placeholder block occupying the space above the wither chest. Not in any creative tab.
 *
 * @author Mark Gottschling on Jun 26, 2018
 */
public class WitherChestTopBlock extends Block implements ITreasureChestBlockProxy, ITreasureBlock {
	protected static final VoxelShape AABB = Block.box(1, 0, 1, 15, 10, 15);

	public WitherChestTopBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockPos getChestPos(BlockPos pos) {
		return pos.below();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		BlockPos chestPos = pos.below();
		BlockState chestState = world.getBlockState(chestPos);
		BlockHitResult chestHit = new BlockHitResult(hit.getLocation(), hit.getDirection(), chestPos, hit.isInside());
		return chestState.useWithoutItem(world, player, chestHit);
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		BlockPos downPos = pos.below();
		Block downBlock = level.getBlockState(downPos).getBlock();
		if (downBlock == TreasureBlocks.WITHER_CHEST.get()) {
			downBlock.onDestroyedByPlayer(level.getBlockState(downPos), level, downPos, player, willHarvest, fluid);
		}
		return true;
	}

	@Override
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		BlockPos downPos = pos.below();
		Block downBlock = level.getBlockState(downPos).getBlock();
		if (downBlock == TreasureBlocks.WITHER_CHEST.get()) {
			downBlock.onBlockExploded(level.getBlockState(downPos), level, downPos, explosion);
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}
}
