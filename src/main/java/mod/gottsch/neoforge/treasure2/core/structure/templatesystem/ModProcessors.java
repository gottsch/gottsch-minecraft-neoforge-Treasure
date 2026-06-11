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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for Treasure2's custom {@link StructureProcessorType}s. The registry names here must
 * match the ids used in the worldgen {@code processor_list} JSONs. Chest-chain processor types
 * are added in a later sub-phase.
 *
 * @author by Mark Gottschling on 8/13/2025
 */
public class ModProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Treasure.MODID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SpawnerProcessor>> SPAWNER_PROCESSOR =
            PROCESSOR_TYPES.register("spawner_processor", () -> () -> SpawnerProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<GravestoneProcessor>> GRAVESTONE_PROCESSOR =
            PROCESSOR_TYPES.register("gravestone_processor", () -> () -> GravestoneProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<AgedProcessor>> AGED_PROCESSOR =
            PROCESSOR_TYPES.register("aged_processor", () -> () -> AgedProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<DecayProcessor>> DECAY_PROCESSOR =
            PROCESSOR_TYPES.register("decay_processor", () -> () -> DecayProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<WellFlowerProcessor>> WELL_FLOWER_PROCESSOR =
            PROCESSOR_TYPES.register("well_flower_processor", () -> () -> WellFlowerProcessor.CODEC);

    // chest-chain processor types
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<VanillaChestProcessor>> VANILLA_CHEST_PROCESSOR =
            PROCESSOR_TYPES.register("vanilla_chest_processor", () -> () -> VanillaChestProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<TreasureChestProcessor>> CHEST_PROCESSOR =
            PROCESSOR_TYPES.register("chest_processor", () -> () -> TreasureChestProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<WitherChestProcessor>> WITHER_CHEST_PROCESSOR =
            PROCESSOR_TYPES.register("wither_chest_processor", () -> () -> WitherChestProcessor.CODEC);

    public static void register(IEventBus eventBus) {
        PROCESSOR_TYPES.register(eventBus);
    }
}
