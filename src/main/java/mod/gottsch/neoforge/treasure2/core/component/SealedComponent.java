package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 11/28/2025
 */
public record SealedComponent(boolean sealed) {

    public static final Codec<SealedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.BOOL.fieldOf("sealed").forGetter(SealedComponent::sealed)
    ).apply(instance, SealedComponent::new));

    public static final StreamCodec<ByteBuf, SealedComponent> STREAM_CODEC =
            // 1. Use the correct codec for the boolean field (ByteBufCodecs.BOOL)
            ByteBufCodecs.BOOL.map(
                    // Decoder: boolean -> SealedComponent
                    SealedComponent::new,
                    // Encoder: SealedComponent -> boolean
                    SealedComponent::sealed
            );
}