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
package mod.gottsch.neoforge.treasure2.core.mobset;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;

/**
 * @author by Mark Gottschling on 9/18/2025
 */
public enum MobSetDataRegistry {
    INSTANCE;

    private static Map<ResourceLocation, MobSetData> REGISTRY = Maps.newHashMap();

    public static Optional<MobSetData> register(MobSetData data) {
        return Optional.ofNullable(REGISTRY.put(data.getId(), data));
    }

    public static Optional<MobSetData> get(ResourceLocation id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    public static void clear() {
        REGISTRY.clear();
    }
}
