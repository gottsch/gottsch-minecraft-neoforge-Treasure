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
import com.mojang.math.Axis;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.client.model.entity.WitherwoodGolemModel;
import mod.gottsch.neoforge.treasure2.client.renderer.entity.layer.WitherwoodGolemLayer;
import mod.gottsch.neoforge.treasure2.core.entity.monster.WitherwoodGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WitherwoodGolemRenderer extends MobRenderer<WitherwoodGolem, WitherwoodGolemModel<WitherwoodGolem>> {
    private static final ResourceLocation GOLEM_LOCATION = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "textures/entity/mob/witherwood_golem.png");

    public WitherwoodGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherwoodGolemModel<>(context.bakeLayer(WitherwoodGolemModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new WitherwoodGolemLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(WitherwoodGolem golem) {
        return GOLEM_LOCATION;
    }

    @Override
    protected void setupRotations(WitherwoodGolem golem, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(golem, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        if (!((double)golem.walkAnimation.speed() < 0.01D)) {
            float f1 = golem.walkAnimation.position(partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(6.5F * f2));
        }
    }
}
