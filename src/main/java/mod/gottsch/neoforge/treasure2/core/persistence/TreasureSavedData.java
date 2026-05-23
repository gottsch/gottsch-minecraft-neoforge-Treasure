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
package mod.gottsch.neoforge.treasure2.core.persistence;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.cache.TreasureChestCache;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityWeightsManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * @author Mark Gottschling on Jan 22, 2018
 */
public class TreasureSavedData extends SavedData {

	private static final String TREASURE = Treasure.MODID;

	public static TreasureSavedData create() {
		return new TreasureSavedData();
	}

	public static TreasureSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
		Treasure.LOGGER.debug("loading treasure2 persisted data...");

		if (tag.contains(TreasureChestCache.TAG_NAME)) {
			TreasureChestCache.load(tag);
		}

		if (tag.contains(RarityWeightsManager.RARITY_SELECTOR_TAG)) {
			RarityWeightsManager.load(tag, registries);
		}

		return create();
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		try {
			TreasureChestCache.save(tag, registries);
			Treasure.LOGGER.debug("saved chest cache to tag");

			RarityWeightsManager.save(tag, registries);
			Treasure.LOGGER.debug("saved rarity weight manager to tag");
		} catch (Exception e) {
			Treasure.LOGGER.error("an exception occurred saving treasure data:", e);
		}
		return tag;
	}

	public static TreasureSavedData get(ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(
				new SavedData.Factory<>(TreasureSavedData::create, TreasureSavedData::load, null),
				TREASURE);
	}
}
