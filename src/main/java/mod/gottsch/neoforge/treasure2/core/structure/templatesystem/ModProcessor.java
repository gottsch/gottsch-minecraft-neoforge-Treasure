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
package mod.gottsch.neoforge.treasure2.core.structure.templatesystem;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Custom base StructureProcessor class. Provides thread-safe single-pass protection
 * properties so a structure piece is only processed/finalized once across passes.
 *
 * @author by Mark Gottschling on 9/7/2025
 */
public abstract class ModProcessor extends StructureProcessor {
    public static final String TREASURE_CHEST = "treasure_chest";
    public static final String VANILLA_CHEST  = "vanilla_chest";
    public static final String DECAY = "decay";

    // a thread-safe map of sets to track process runs
    protected static final Map<String, Set<BlockPos>> singlePassProcessGuard =
            Collections.synchronizedMap(new ConcurrentHashMap<>());

    // a thread-safe set to track finalized runs.
    protected static final Map<String, Set<BlockPos>> singlePassFinalizeGuard =
            Collections.synchronizedMap(new ConcurrentHashMap<>());

    public static boolean addProcessGuard(String key, BlockPos value) {
        return singlePassProcessGuard.computeIfAbsent(key, k -> Collections.synchronizedSet(new HashSet<>())).add(value);
    }

    public static boolean addFinalizeGuard(String key, BlockPos value) {
        return singlePassFinalizeGuard.computeIfAbsent(key, k -> Collections.synchronizedSet(new HashSet<>())).add(value);
    }

    public static void removeFromGuards(BlockPos pos) {
        singlePassProcessGuard.forEach((k, v) -> v.remove(pos));
        singlePassFinalizeGuard.forEach((k, v) -> v.remove(pos));
    }

    public static boolean hasProcessGuard(String key, BlockPos pos) {
        return singlePassProcessGuard.containsKey(key) && singlePassProcessGuard.get(key).contains(pos);
    }

    public static boolean hasFinalizeGuard(String key, BlockPos pos) {
        Treasure.LOGGER.debug("has finalize guard for key {}, {} -> {}", key, pos,
                singlePassFinalizeGuard.containsKey(key) && singlePassFinalizeGuard.get(key).contains(pos));
        return singlePassFinalizeGuard.containsKey(key) && singlePassFinalizeGuard.get(key).contains(pos);
    }
}
