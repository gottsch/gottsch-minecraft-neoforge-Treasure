package mod.gottsch.neoforge.treasure2.core.component;

import com.mojang.serialization.Codec;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author by Mark Gottschling on 11/27/2025
 */
@Deprecated
public record LockStateComponent(LockState lockState) {

    // 1. CODEC: links the component wrapper to the LockState's Codec for disk I/O.
    public static final Codec<LockStateComponent> CODEC = LockState.CODEC
            // xmap maps between the LockStateComponent record and the LockState object
            .xmap(LockStateComponent::new, LockStateComponent::lockState);

    // 2. STREAM_CODEC: Links the component wrapper to the LockState's StreamCodec for network sync.
    // NOTE: the base type MUST match the LockState.STREAM_CODEC base type (RegistryFriendlyByteBuf).
    public static final StreamCodec<RegistryFriendlyByteBuf, LockStateComponent> STREAM_CODEC =
            StreamCodec.composite(
                    // the StreamCodec for the underlying LockState object
                    LockState.STREAM_CODEC,
                    // getter: Get LockState from LockStateComponent
                    LockStateComponent::lockState,
                    // constructor: create LockStateComponent from LockState
                    LockStateComponent::new
            );

    // helper method for convenience
    public LockState getLockState() {
        return this.lockState;
    }
}
