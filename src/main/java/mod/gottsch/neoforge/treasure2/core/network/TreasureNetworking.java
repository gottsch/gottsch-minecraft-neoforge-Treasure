package mod.gottsch.neoforge.treasure2.core.network;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
public class TreasureNetworking {
    public static final ResourceLocation CHANNEL_ID = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "main_channel");

    // The modern approach uses an instance of the registrar (PayloadRegistrar)
    // passed during the registration event, not a static ChannelBuilder instance.
    // The previous static INSTANCE definition is removed as it's no longer necessary.

    // --- Registration Method ---
    public static void register(final RegisterPayloadHandlersEvent event) {
        // The correct way to get the registrar instance in the registration event:
        final PayloadRegistrar payloadRegistrar = event.registrar(Treasure.MODID);

        // Register our InventorySyncPacket handler for client-bound packets (Server -> Client)
        payloadRegistrar.playToClient(
                InventorySyncPacket.TYPE,
                InventorySyncPacket.STREAM_CODEC,
                InventorySyncPacket.Handler::handle
        );

        // Client -> server: wither-tree mist particles ask the server to apply poison/wither effects.
        payloadRegistrar.playToServer(
                PoisonMistMessageToServer.TYPE,
                PoisonMistMessageToServer.STREAM_CODEC,
                PoisonMistMessageToServer::handle
        );
        payloadRegistrar.playToServer(
                WitherMistMessageToServer.TYPE,
                WitherMistMessageToServer.STREAM_CODEC,
                WitherMistMessageToServer::handle
        );
    }

    // --- Helper function for sending the synchronization packet ---
    /**
     * Sends the current ItemContainerContents of a BlockEntity to all tracking players.
     * Must be called on the logical server.
     */
    public static void syncInventoryToClients(BlockEntity blockEntity, InventorySyncPacket packet) {
        // Distribute the packet to all players who are currently tracking (in range of) the BlockEntity.
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel)blockEntity.getLevel(), blockEntity.getLevel().getChunkAt(blockEntity.getBlockPos()).getPos(), packet);
    }
}
