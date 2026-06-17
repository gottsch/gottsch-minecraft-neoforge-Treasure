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
package mod.gottsch.neoforge.treasure2.core.lock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import mod.gottsch.neoforge.treasure2.core.item.LockItem;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

import java.util.Optional;

/**
 * @author Mark Gottschling onJan 10, 2018
 */
public class LockState {
    private LockSlot slot;
    private Optional<LockItem> lockItem = Optional.empty();

    public static final Codec<LockState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LockSlot.CODEC.fieldOf("slot").forGetter(LockState::getSlot),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("lockItem")
                    .forGetter(ls -> ls.getLock().map(lock -> (Item) lock))
    ).apply(instance, (slot, lockItemOpt) -> new LockState(slot, (LockItem) lockItemOpt.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, LockState> STREAM_CODEC = StreamCodec.composite(
            LockSlot.STREAM_CODEC,
            LockState::getSlot,
            ByteBufCodecs.optional(ByteBufCodecs.registry(Registries.ITEM)),
            ls -> ls.getLock().map(lock -> (Item) lock),
            (slot, lockItemOpt) -> new LockState(slot, (LockItem) lockItemOpt.orElse(null))
    );

    /**
     *
     */
    public LockState() {

    }

    public LockState(LockSlot slot, LockItem lockItem) {
        this.slot = slot;
        this.lockItem = Optional.ofNullable(lockItem);
    }

    /**
     * @return the slot
     */
    public LockSlot getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(LockSlot slot) {
        this.slot = slot;
    }

    /**
     * @return the lockItem, or empty if this slot has no lock
     */
    public Optional<LockItem> getLock() {
        return lockItem;
    }

    /**
     * @param lockItem the lockItem to set; null is treated as absent
     */
    public void setLock(LockItem lockItem) {
        this.lockItem = Optional.ofNullable(lockItem);
    }

    /**
     * Clears the lock from this slot.
     */
    public void removeLock() {
        this.lockItem = Optional.empty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LockState [slot=" + slot + ", lockItem=" + lockItem + "]";
    }
}
