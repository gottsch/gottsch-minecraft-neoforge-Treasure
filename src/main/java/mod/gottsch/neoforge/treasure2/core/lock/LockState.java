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
    private LockItem lockItem;

    public static final Codec<LockState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LockSlot.CODEC.fieldOf("slot").forGetter(LockState::getSlot),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("lockItem")
                    .forGetter(ls -> Optional.ofNullable((Item) ls.getLock()))
    ).apply(instance, (slot, lockItemOpt) -> new LockState(slot, (LockItem) lockItemOpt.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, LockState> STREAM_CODEC = StreamCodec.composite(

            // 1. LockSlot (Assumed StreamCodec<ByteBuf, LockSlot> is available)
            LockSlot.STREAM_CODEC,
            LockState::getSlot,

            // 2. LockItem (Uses the native registry StreamCodec, which returns Item)
            ByteBufCodecs.registry(Registries.ITEM),
            LockState::getLock,

            // Factory method is clean, cast-free, and correctly uses the field types.
            LockState::create
    );

    // private factory method to satisfy the Codec/StreamCodec
    private static LockState create(LockSlot slot, Item lockItem) {
        // the Item registry lookup guarantees the Item is a LockItem
        // if the JSON/NBT was valid.
        return new LockState(slot, (LockItem) lockItem);
    }

    /**
     *
     */
    public LockState() {

    }

    public LockState(LockSlot slot, LockItem lockItem) {
        this.slot = slot;
        this.lockItem = lockItem;
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
     * @return the lockItem
     */
    public LockItem getLock() {
        return lockItem;
    }

    /**
     * @param lockItem the lockItem to set
     */
    public void setLock(LockItem lockItem) {
        this.lockItem = lockItem;
    }

    /**
     * convenience method to set lock to null.
     */
    public void removeLock() {
        setLock(null);
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
