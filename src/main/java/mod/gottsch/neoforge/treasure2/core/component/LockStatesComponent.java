package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
public record LockStatesComponent(List<LockState> lockStates) {
    public static final Codec<LockStatesComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LockState.CODEC.listOf().fieldOf("lockStates").forGetter(LockStatesComponent::lockStates)
    ).apply(instance, LockStatesComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, LockStatesComponent> STREAM_CODEC = StreamCodec.composite(
            // 1. get the list stream codec by applying ByteBufCodecs.list()
            LockState.STREAM_CODEC.apply(ByteBufCodecs.list()),
            // 2. the getter for the list field
            LockStatesComponent::lockStates,
            // 3. the constructor reference
            LockStatesComponent::new
    );
}
