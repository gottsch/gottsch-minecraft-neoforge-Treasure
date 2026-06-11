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
package mod.gottsch.neoforge.treasure2.core.structure;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for Treasure2's custom {@link StructureType}s. The registry names here must match the
 * ids referenced in the worldgen {@code structure} JSONs ({@code treasure2:terranean_structure},
 * {@code treasure2:ruins_structure}).
 *
 * @author by Mark Gottschling on 8/16/2025
 */
public class TreasureStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, Treasure.MODID);

    public static final DeferredHolder<StructureType<?>, StructureType<ModJigsawStructure>> TERRANEAN_STRUCTURE =
            STRUCTURE_TYPES.register("terranean_structure", () -> () -> ModJigsawStructure.CODEC);

    public static final DeferredHolder<StructureType<?>, StructureType<RuinsStructure>> RUINS_STRUCTURE =
            STRUCTURE_TYPES.register("ruins_structure", () -> () -> RuinsStructure.CODEC);

    public static void register(IEventBus modEventBus) {
        STRUCTURE_TYPES.register(modEventBus);
    }
}
