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
package mod.gottsch.neoforge.treasure2.core.inventory;

import mod.gottsch.neoforge.treasure2.core.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

/**
 * 
 * @author Mark Gottschling on Dec 4, 2022
 *
 */
public class StandardChestContainerMenu extends AbstractTreasureContainerMenu {

	public StandardChestContainerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, extraData.readBlockPos(), inv, inv.player);
	}

	/**
	 *
	 * @param containerId
	 * @param pos
	 * @param playerInventory
	 * @param player
	 */
	public StandardChestContainerMenu(int containerId, BlockPos pos, Inventory playerInventory, Player player) {
		super(containerId, TreasureContainers.STANDARD_CHEST_CONTAINER.get(), pos, playerInventory, player);
		
		if (Config.CLIENT.gui.enableCustomChestInventoryGui.get()) {
			setMenuInventoryYPos(19);
			setPlayerInventoryYPos(85);
			setHotbarYPos(143);
		}
		buildContainer();
	}
}
