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
package mod.gottsch.neoforge.treasure2.core.structure.templatesystem.chest;

import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * In-memory registry of chest subprocessors, keyed by the {@code type} resource location used in the
 * chest subprocessor data JSONs (e.g. {@code treasure2:standard}).
 *
 * <p>NeoForge 1.21.1 port note: the Forge version used a custom {@code IForgeRegistry} (with unused
 * tag support) and mutated shared singleton subprocessors during structure generation. This port
 * uses a plain supplier map that hands out a <em>fresh</em> instance per lookup, which is simpler
 * and avoids the data race of mutating a shared subprocessor across multithreaded structure passes.
 *
 * @author by Mark Gottschling on 8/27/2025
 */
public class TreasureChestSubprocessors {

    private static final Map<ResourceLocation, Supplier<IChestSubprocessor>> REGISTRY = new HashMap<>();

    public static final ResourceLocation STANDARD = ModUtil.asLocation("standard");
    public static final ResourceLocation HIGH_TIER_RARITY = ModUtil.asLocation("high_tier_rarity");
    public static final ResourceLocation BONE_CHEST = ModUtil.asLocation("bone_chest");
    public static final ResourceLocation WITHER_CHEST = ModUtil.asLocation("wither_chest");
    public static final ResourceLocation LEGENDARY_CHEST = ModUtil.asLocation("legendary_chest");
    public static final ResourceLocation MYTHICAL_CHEST = ModUtil.asLocation("mythical_chest");

    static {
        REGISTRY.put(STANDARD, StandardChestSubprocessor::new);
        REGISTRY.put(HIGH_TIER_RARITY, HighTierRarityChestSubprocessor::new);
        REGISTRY.put(BONE_CHEST, BoneChestSubprocessor::new);
        REGISTRY.put(WITHER_CHEST, WitherChestSubprocessor::new);
        REGISTRY.put(LEGENDARY_CHEST, LegendaryChestSubprocessor::new);
        REGISTRY.put(MYTHICAL_CHEST, MythicalChestSubprocessor::new);
    }

    private TreasureChestSubprocessors() {
    }

    /**
     * @return a fresh subprocessor instance for the given type, or empty if the type is unregistered.
     */
    public static Optional<IChestSubprocessor> getChestSubprocessor(ResourceLocation name) {
        return Optional.ofNullable(REGISTRY.get(name)).map(Supplier::get);
    }

    /** @return a fresh standard subprocessor (used as the generic default/fallback). */
    public static IChestSubprocessor standard() {
        return new StandardChestSubprocessor();
    }

    /** @return a fresh wither subprocessor. */
    public static IChestSubprocessor wither() {
        return new WitherChestSubprocessor();
    }
}
