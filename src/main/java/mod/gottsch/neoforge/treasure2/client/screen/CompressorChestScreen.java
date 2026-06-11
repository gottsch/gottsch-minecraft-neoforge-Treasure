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
import mod.gottsch.neoforge.treasure2.core.inventory.CompressorChestContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CompressorChestScreen extends AbstractChestScreen<CompressorChestContainerMenu> {

	private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "textures/gui/screen/compressor_chest.png");

	public CompressorChestScreen(CompressorChestContainerMenu screenContainer, Inventory playerInventory, Component title) {
		super(screenContainer, playerInventory, title);
		imageWidth = 247;
		imageHeight = 184;
		this.inventoryLabelY = this.imageHeight - 93;
		setBgTexture(BG_TEXTURE);
	}
}
