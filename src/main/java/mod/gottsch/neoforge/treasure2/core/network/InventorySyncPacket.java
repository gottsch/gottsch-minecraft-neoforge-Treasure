package mod.gottsch.neoforge.treasure2.core.network;

import mod.gottsch.neoforge.treasure2.core.attachment.TreasureAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
public record InventorySyncPacket(BlockPos pos, ItemContainerContents contents) implements CustomPacketPayload {

    // 1. TYPE: Defines the unique ID for the packet, referencing the ModPackets channel.
    public static final CustomPacketPayload.Type<InventorySyncPacket> TYPE = new CustomPacketPayload.Type<>(TreasureNetworking.CHANNEL_ID);

    // 2. STREAM_CODEC: Defines how the packet is serialized/deserialized over the network.
    // Note: We use the vanilla ItemContainerContents.STREAM_CODEC directly.
    public static final StreamCodec<RegistryFriendlyByteBuf, InventorySyncPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, InventorySyncPacket::pos,
            ItemContainerContents.STREAM_CODEC, InventorySyncPacket::contents,
            InventorySyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // 3. Handler: The logic that runs when the client receives the packet.
    public static class Handler {
        public static void handle(InventorySyncPacket payload, IPayloadContext context) {
            // Ensure this runs on the client thread for world interaction
            context.enqueueWork(() -> {
                // We use the static class 'Attachments' (which you have been working on)
                // to get the INVENTORY AttachmentType.
                BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(payload.pos());

                if (blockEntity != null) {
                    // Attach the new (synced) data to the client-side BlockEntity
                    blockEntity.setData(TreasureAttachments.INVENTORY, payload.contents());

                    // Rerender the block for immediate GUI update
                    Minecraft.getInstance().level.sendBlockUpdated(
                            payload.pos(),
                            blockEntity.getBlockState(),
                            blockEntity.getBlockState(),
                            3 // Flag to notify client and rerender
                    );
                }
            });
        }
    }
}
