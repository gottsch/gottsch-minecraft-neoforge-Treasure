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
package mod.gottsch.neoforge.treasure2.core.chest;

import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;

/**
 * @author Mark Gottschling on Mar 3, 2018
 *
 */
public enum ChestInventorySize implements IChestInventorySize{
	STANDARD(27),
	STRONGBOX(15),
	SKULL(9),
	COMPRESOR(52),
	WITHER(42);
	
	private int size;
	
	ChestInventorySize(int size) {
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
