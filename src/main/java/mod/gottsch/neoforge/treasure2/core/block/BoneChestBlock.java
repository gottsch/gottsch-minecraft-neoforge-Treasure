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

import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.item.LockItem;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

/**
 * @author by Mark Gottschling on 9/16/2025
 */
public class BoneChestBlock extends StandardChestBlock {

	public BoneChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, LockLayout type) {
		super(blockEntityType, type, Properties.of().mapColor(MapColor.WOOD));
	}

	public BoneChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, LockLayout type, Properties properties) {
		super(blockEntityType, type, properties);
	}

	public BoneChestBlock(Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType, LockLayout type, Properties properties) {
		super(blockEntityType, type, properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		AbstractTreasureChestBlockEntity entity = (AbstractTreasureChestBlockEntity) super.newBlockEntity(pos, state);
		entity.getLockStates().get(0).setLock((LockItem) TreasureItems.BONE_LOCK.get());
		return entity;
	}
}
