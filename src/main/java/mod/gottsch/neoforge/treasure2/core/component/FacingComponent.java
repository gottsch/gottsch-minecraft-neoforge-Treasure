package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 11/28/2025
 */
@Deprecated
public record FacingComponent(int facing) {

    public static final Codec<FacingComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("facing").forGetter(FacingComponent::facing)
    ).apply(instance, FacingComponent::new));

    public static final StreamCodec<ByteBuf, FacingComponent> STREAM_CODEC =
            ByteBufCodecs.INT.map(
                    FacingComponent::new,
                    FacingComponent::facing
            );

    // Unit stream codec if nothing should be sent across the network
//    public static final StreamCodec<ByteBuf, FacingComponent> UNIT_STREAM_CODEC = StreamCodec.unit(new FacingComponent(0));

}