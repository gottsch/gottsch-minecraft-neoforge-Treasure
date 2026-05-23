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
package mod.gottsch.neoforge.treasure2.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.entity.BoundSoulModel;
import mod.gottsch.neoforge.treasure2.client.renderer.entity.layer.BoundSoulLayer;
import mod.gottsch.neoforge.treasure2.core.entity.monster.BoundSoul;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BoundSoulRenderer extends MobRenderer<BoundSoul, BoundSoulModel<BoundSoul>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "textures/entity/mob/bound_soul.png");

    public BoundSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new BoundSoulModel<>(context.bakeLayer(BoundSoulModel.LAYER_LOCATION)), 0.0F);
        this.addLayer(new BoundSoulLayer<>(this));
    }

    @Override
    public void render(BoundSoul entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BoundSoul entity) {
        return TEXTURE;
    }
}
