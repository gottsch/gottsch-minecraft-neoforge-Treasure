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
package mod.gottsch.neoforge.treasure2.core.network;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Server -> client: when a chest "becomes" a mimic, correct the freshly-spawned mimic's body
 * rotation on the client (vanilla spawn packets don't sync yBodyRot reliably until the first AI
 * tick). 1.21 port of the Forge SimpleChannel MimicSpawnS2C message.
 *
 * @author Mark Gottschling on Jun 8, 2023
 */
public record MimicSpawnS2C(int entityId, float bodyYRot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MimicSpawnS2C> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "mimic_spawn_to_client"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MimicSpawnS2C> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, MimicSpawnS2C::entityId,
                    ByteBufCodecs.FLOAT, MimicSpawnS2C::bodyYRot,
                    MimicSpawnS2C::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MimicSpawnS2C payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level == null) {
                return;
            }
            Entity entity = Minecraft.getInstance().level.getEntity(payload.entityId());
            if (entity != null) {
                entity.setYBodyRot(payload.bodyYRot());
            }
        });
    }
}
