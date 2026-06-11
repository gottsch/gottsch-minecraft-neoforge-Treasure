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
package mod.gottsch.neoforge.treasure2.core.registry;

import com.google.common.collect.Maps;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.structure.templatesystem.data.ChestSubprocessorData;
import mod.gottsch.neoforge.treasure2.core.world.feature.IFeatureType;

import java.util.Map;
import java.util.Optional;

/**
 * In-memory registry mapping a (feature type, rarity) pair to its chest subprocessor data,
 * populated from data packs by {@code ChestSubprocessorDataHandler}.
 *
 * @author by Mark Gottschling on 8/28/2025
 */
public enum ChestSubprocessorDataRegistry {
    INSTANCE;

    private static final Map<IFeatureType, Map<IRarity, ChestSubprocessorData>> MAP = Maps.newHashMap();

    public static void clear() {
        MAP.clear();
    }

    public static void register(IFeatureType type, IRarity rarity, ChestSubprocessorData data) {
        MAP.computeIfAbsent(type, m -> Maps.newHashMap()).put(rarity, data);
    }

    public static Optional<ChestSubprocessorData> getAssociation(IFeatureType key, IRarity rarity) {
        return Optional.ofNullable(MAP.get(key))
                .map(innerMap -> innerMap.get(rarity));
    }
}
