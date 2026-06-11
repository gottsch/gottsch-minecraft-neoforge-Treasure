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
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureProximityMultiSpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * An invisible non-collision block (like AIR) that creates a TreasureProximityMultiSpawnerBlockEntity
 * @author Mark Gottschling on Aug 16, 2024
 *
 */
public class ProximityMultiSpawnerBlock extends AbstractProximityBlock implements ITreasureBlock {
	public static final MapCodec<ProximityMultiSpawnerBlock> CODEC = simpleCodec(ProximityMultiSpawnerBlock::new);

	/**
	 *
	 * @param properties
	 */
	public ProximityMultiSpawnerBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends AbstractProximityBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		Treasure.LOGGER.debug("created proximity spawner be");
		TreasureProximityMultiSpawnerBlockEntity blockEntity = null;
		try {
			blockEntity = new TreasureProximityMultiSpawnerBlockEntity(pos, state);
			blockEntity.setProximity(5.0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return blockEntity;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return !level.isClientSide() ? (lvl, pos, blockState, t) -> {
			if (t instanceof TreasureProximityMultiSpawnerBlockEntity entity) {
				entity.tickServer();
			}
		} : null;
	}

	@Override
	protected boolean isAir(BlockState state) {
		return true;
	}
}
