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
package mod.gottsch.neoforge.treasure2.core.random;

import mod.gottsch.neo.gottschcore.util.ModUtil;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Mark Gottschling 8/26/2025
 *
 */
public class RarityAdjustingWeightedCollection extends AdjustingWeightedCollection<IRarity> {

	private static final String COLLECTION = "collection";
	private static final String ORIGINAL = "original";

	public RarityAdjustingWeightedCollection() {
		super();
	}

	public RarityAdjustingWeightedCollection(AdjustingWeightedCollection<IRarity> col) {
		super();
		this.collection = col.collection;
		this.original = col.original;
	}

	public RarityAdjustingWeightedCollection(Random random) {
		super(random);
	}

	/**
	 * convenience casting
	 */
	public RarityAdjustingWeightedCollection add(Integer weight, IRarity item) {
		return (RarityAdjustingWeightedCollection) super.add(weight, item);
	}

	public RarityAdjustingWeightedCollection only(List<IRarity> rarities) {
		return (RarityAdjustingWeightedCollection) super.only(rarities);
	}

	public RarityAdjustingWeightedCollection only(IRarity... rarities) {
		return only(Arrays.stream(rarities).toList());
	}

	/**
	 * Convenience casting
	 */
	public RarityAdjustingWeightedCollection add(Pair<Integer, Integer> weightPair, IRarity item) {
		return (RarityAdjustingWeightedCollection) super.add(weightPair, item);
	}

	/**
	 * 
	 * @return
	 */
	public CompoundTag save(HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();

		ListTag originalList = new ListTag();
		original.forEach((key, value) -> {
			CompoundTag element = new CompoundTag();
			TreasureRarities.getKey(key, registries).ifPresent(rarityName -> {
				element.putString("key", rarityName.toString());
				element.putInt("right", value.getRight());
				originalList.add(element);
			});

		});
		tag.put("original", originalList);
		
		return tag;
	}
	
	/**
	 * load() will load and set the original.right collection, and then add() to the weighted collection
	 * so that the properly weights are used and built.
	 * @param tag
	 */
	public void load(CompoundTag tag, HolderLookup.Provider registries) {
		collection.clear();

		if (tag.contains("original")) {
			ListTag collectionList = tag.getList("original", Tag.TAG_COMPOUND);
			collectionList.forEach(element -> {
				CompoundTag e = (CompoundTag)element;
				if (e.contains("key") && e.contains("right")) {
					ModUtil.asLocation(e.getString("key")).ifPresent(rarityName -> {
						TreasureRarities.getRarityByName(rarityName, registries).ifPresent(rarity -> {
							Integer weight = e.getInt("right");

							Pair<Integer, Integer> persistedPair = original.get(rarity);
							super.add(Pair.of(persistedPair.getLeft(), weight), rarity);
						});
					});
				}
			});

		}
	}
}
