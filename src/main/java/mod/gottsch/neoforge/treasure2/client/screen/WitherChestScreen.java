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
package mod.gottsch.neoforge.treasure2.client.screen;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.inventory.WitherChestContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WitherChestScreen extends AbstractChestScreen<WitherChestContainerMenu> {

	private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "textures/gui/screen/wither_chest.png");

	public WitherChestScreen(WitherChestContainerMenu screenContainer, Inventory playerInventory, Component title) {
		super(screenContainer, playerInventory, title);
		imageWidth = 176;
		imageHeight = 222;
		this.inventoryLabelY = this.imageHeight - 93;
		setBgTexture(BG_TEXTURE);
	}
}
