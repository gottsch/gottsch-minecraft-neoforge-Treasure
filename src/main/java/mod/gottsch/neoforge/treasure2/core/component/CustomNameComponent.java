package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
public record CustomNameComponent(String name) {
    public static final Codec<CustomNameComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(CustomNameComponent::name)
    ).apply(instance, CustomNameComponent::new));

    public static final StreamCodec<ByteBuf, CustomNameComponent> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(
                    CustomNameComponent::new,
                    CustomNameComponent::name
            );
}
