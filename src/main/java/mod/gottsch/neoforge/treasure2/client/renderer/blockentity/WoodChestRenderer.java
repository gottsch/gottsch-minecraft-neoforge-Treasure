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
package mod.gottsch.neoforge.treasure2.client.renderer.blockentity;


import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.blockentity.StandardChestModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


/**
 * 
 * @author Mark Gottschling on 2018
 *
 */
// NOTE @OnlyIn extremely important! add to all Renderers
@OnlyIn(Dist.CLIENT)
public class WoodChestRenderer extends AbstractChestBlockEntityRenderer {//implements BlockEntityRenderer<WoodChestBlockEntity>/*extends AbstractChestTileEntityRenderer*/ {
	/*
	 * NOTE when defining a resource location for the Atlas, you don't need to specify the /textures/ parent folder nor, the .png extension
	 */
	public static final ResourceLocation WOOD_CHEST_RENDERER_ATLAS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "entity/chest/wood_chest");


	/**
	 * 
	 * @param context
	 */
	public WoodChestRenderer(BlockEntityRendererProvider.Context context) {
		setModel(new StandardChestModel(context.bakeLayer(StandardChestModel.LAYER_LOCATION)));
		setMaterial(new Material(TextureAtlas.LOCATION_BLOCKS, WOOD_CHEST_RENDERER_ATLAS_TEXTURE));
	}
}
