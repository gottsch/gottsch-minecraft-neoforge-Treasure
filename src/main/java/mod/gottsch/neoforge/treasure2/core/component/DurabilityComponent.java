package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 5/20/2026
 */
public record DurabilityComponent(int uses, int maxUses) {
    public static final Codec<DurabilityComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("uses").forGetter(DurabilityComponent::uses),
            Codec.INT.fieldOf("maxUses").forGetter(DurabilityComponent::maxUses)
    ).apply(instance, DurabilityComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DurabilityComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            DurabilityComponent::uses,
            ByteBufCodecs.VAR_INT,
            DurabilityComponent::maxUses,
            DurabilityComponent::new
    );
}
