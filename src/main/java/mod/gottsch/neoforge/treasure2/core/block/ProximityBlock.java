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
import mod.gottsch.neo.gottschcore.block.AbstractProximityBlock;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureProximitySpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * An invisible non-collision block (like AIR) that creates a TreasureProximitySpawnerBlockEntity.
 * @author Mark Gottschling on Jan 17, 2019
 *
 */
public class ProximityBlock extends AbstractProximityBlock implements ITreasureBlock {
	public static final MapCodec<ProximityBlock> CODEC = simpleCodec(ProximityBlock::new);

	/**
	 *
	 * @param properties
	 */
	public ProximityBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends AbstractProximityBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		Treasure.LOGGER.debug("created proximity spawner be");
		TreasureProximitySpawnerBlockEntity blockEntity = null;
		try {
			blockEntity = new TreasureProximitySpawnerBlockEntity(pos, state);
			blockEntity.setProximity(5.0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return blockEntity;
	}

	@Override
	protected boolean isAir(BlockState state) {
		return true;
	}
}
