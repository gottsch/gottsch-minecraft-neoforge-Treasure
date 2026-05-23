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

import java.util.Arrays;
import java.util.List;

/**
 * NOTE should this contain all sort of data of a chest or is this class simply about locks? If about just locks it should change it's name.
 * @author Mark Gottschling on Jan 9, 2018
 *
 */
public class LockLayout {
	private int maxLocks;
	private LockSlot[] slots;

	public static final Codec<LockLayout> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("maxLocks").forGetter(LockLayout::getMaxLocks),
			LockSlot.CODEC.listOf().fieldOf("slots")
					// map the List<LockSlot> (Codec type) to the LockSLot[]
					.xmap(
							// DECODER (reading from data to LockSlot[]): convert List<LockSlot> to LockSlot[]
							list -> list.toArray(new LockSlot[0]),

							// ENCODER (writing from LockSlot[] to data): Convert LockSlot[] to List<LockSlot>
							List::of
					)
					// get the LockSlot[] from the LockLayout class instance
					.forGetter(LockLayout::getSlots)
	).apply(instance, LockLayout::new));

	/**
	 * 
	 */
	public LockLayout(int maxLocks) {
		setMaxLocks(maxLocks);
		setSlots(new LockSlot[maxLocks]);
	}

	public LockLayout(int maxLocks, LockSlot[] slots) {
		setMaxLocks(maxLocks);
		setSlots(slots);
	}

	/**
	 * @return the maxLocks
	 */
	public int getMaxLocks() {
		return maxLocks;
	}

	/**
	 * @param maxLocks the maxLocks to set
	 */
	private LockLayout setMaxLocks(int maxLocks) {
		this.maxLocks = maxLocks;
		return this;
	}

	/**
	 * @return the slots
	 */
	public LockSlot[] getSlots() {
		return slots;
	}

	/**
	 * 
	 * @param lockSlots
	 */
	public LockLayout addSlots(LockSlot...lockSlots ) {
		for (int i = 0; i < lockSlots.length; i++) {
			if (i < this.getMaxLocks()) {
				this.getSlots()[i] = lockSlots[i];
			}
		}
		return this;
	}
	
	/**
	 * @param slots the slots to set
	 */
	private LockLayout setSlots(LockSlot[] slots) {
		this.slots = slots;
		return this;
	}

	@Override
	public String toString() {
		return "LockLayout{" +
				"maxLocks=" + maxLocks +
				", slots=" + Arrays.toString(slots) +
				'}';
	}
}
