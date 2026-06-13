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

import java.util.UUID;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Client -> server: a wither-tree poison mist particle detected the given player in proximity and asks
 * the server to apply the poison effect. 1.21 port of the Forge SimpleChannel message to a payload.
 *
 * @author Mark Gottschling on Aug 7, 2021
 */
public record PoisonMistMessageToServer(String playerUUID) implements CustomPacketPayload {

	public static final CustomPacketPayload.Type<PoisonMistMessageToServer> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "poison_mist_to_server"));

	public static final StreamCodec<RegistryFriendlyByteBuf, PoisonMistMessageToServer> STREAM_CODEC =
			StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8, PoisonMistMessageToServer::playerUUID,
					PoisonMistMessageToServer::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PoisonMistMessageToServer payload, IPayloadContext context) {
		context.enqueueWork(() -> {
			try {
				Player sender = context.player();
				if (sender == null || sender.getServer() == null) {
					return;
				}
				ServerPlayer player = sender.getServer().getPlayerList().getPlayer(UUID.fromString(payload.playerUUID()));
				if (player != null && !player.hasEffect(MobEffects.POISON)) {
					player.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 0));
				}
			}
			catch (Exception e) {
				Treasure.LOGGER.error("Unexpected error applying poison mist effect ->", e);
			}
		});
	}
}
