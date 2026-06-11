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

import com.mojang.blaze3d.vertex.PoseStack;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.blockentity.CelestialChestModel;
import mod.gottsch.neoforge.treasure2.client.model.blockentity.ITreasureChestModel;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CelestialChestRenderer extends AbstractChestBlockEntityRenderer {
	public static final ResourceLocation CELESTIAL_CHEST_RENDERER_ATLAS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "entity/chest/celestial_chest");

	public CelestialChestRenderer(BlockEntityRendererProvider.Context context) {
		setModel(new CelestialChestModel(context.bakeLayer(CelestialChestModel.LAYER_LOCATION)));
		setMaterial(new Material(TextureAtlas.LOCATION_BLOCKS, CELESTIAL_CHEST_RENDERER_ATLAS_TEXTURE));
	}

	@Override
	public void updateAdditionalRotation(PoseStack poseStack, ITreasureChestModel model, AbstractTreasureChestBlockEntity blockEntity, float partialTicks) {
		super.updateAdditionalRotation(poseStack, model, blockEntity, partialTicks);

		// flap wings
		long worldTime = blockEntity.getLevel().getGameTime();
		float ageInTicks = (float)worldTime + partialTicks;

		float amplitude = 7.0F;
		float frequency = 0.25F;

		float rotationDegrees = amplitude * Mth.sin(ageInTicks * frequency);
		float rotationRadians = (float)Math.toRadians(rotationDegrees);

		ModelPart rightWing = getChestModel().getRightWing();
		ModelPart leftWing = getChestModel().getLeftWing();

		rightWing.yRot = rotationRadians;
		leftWing.yRot = -rotationRadians;
	}

	public CelestialChestModel getChestModel() {
		return (CelestialChestModel) getModel();
	}
}
