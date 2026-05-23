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
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.chest.ChestInventorySize;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import java.util.function.Supplier;

/**
 * @author Mark Gottschling on Jan 9, 2018
 *
 */
public class StandardChestBlock extends AbstractTreasureChestBlock {
	public static final MapCodec<StandardChestBlock> CODEC = makeCodec(StandardChestBlock::new);

	public StandardChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, LockLayout type) {
		this(blockEntityType, type, Properties.of().mapColor(MapColor.WOOD));
	}

	public StandardChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, LockLayout type, Properties properties) {
		super(blockEntityType, ChestInventorySize.STANDARD.getSize(), type, properties);
	}

	public StandardChestBlock(BlockEntityType<?> blockEntityType, int inventorySize, LockLayout type, Properties properties) {
		super((BlockEntityType<? extends AbstractTreasureChestBlockEntity>) blockEntityType, inventorySize, type, properties);
	}

	public StandardChestBlock(Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType, LockLayout type, Properties properties) {
		super(blockEntityType, ChestInventorySize.STANDARD.getSize(), type, properties);
	}

	public StandardChestBlock(Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType, int inventorySize, LockLayout type, Properties properties) {
		super(blockEntityType, inventorySize, type, properties);
	}


	//	private void dump(CompoundNBT tag, ICoords coords, String title) {
		//		ChestNBTPrettyPrinter printer = new ChestNBTPrettyPrinter();
		//		SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");
		//
		//		String filename = String.format("chest-nbt-%s-%s.txt", formatter.format(new Date()),
		//				coords.toShortString().replaceAll(" ", "-"));
		//
		//		Path path = Paths.get(TreasureConfig.LOGGING.folder, "dumps").toAbsolutePath();
		//		try {
		//			Files.createDirectories(path);
		//		} catch (IOException e) {
		//			Treasure.LOGGER.error("Couldn't create directories for dump files:", e);
		//			return;
		//		}
		//		String s = printer.print(tag, Paths.get(path.toString(), filename), title);
		//		Treasure.LOGGER.debug(s);
//	}


	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}
}
