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
import mod.gottsch.neoforge.treasure2.core.structure.grove.WitherBoulderPiece;
import mod.gottsch.neoforge.treasure2.core.structure.grove.WitherFallenLogPiece;
import mod.gottsch.neoforge.treasure2.core.structure.grove.WitherGroveAreaPiece;
import mod.gottsch.neoforge.treasure2.core.structure.grove.WitherGroveStructure;
import mod.gottsch.neoforge.treasure2.core.structure.grove.WitherTreePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
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

    public static final DeferredHolder<StructureType<?>, StructureType<WitherGroveStructure>> WITHER_GROVE =
            STRUCTURE_TYPES.register("wither_grove", () -> () -> WitherGroveStructure.CODEC);

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, Treasure.MODID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> WITHER_TREE_PIECE =
            STRUCTURE_PIECES.register("wither_tree", () -> (StructurePieceType.ContextlessType) WitherTreePiece::new);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> WITHER_GROVE_AREA_PIECE =
            STRUCTURE_PIECES.register("wither_grove_area", () -> (StructurePieceType.ContextlessType) WitherGroveAreaPiece::new);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> WITHER_BOULDER_PIECE =
            STRUCTURE_PIECES.register("wither_boulder", () -> (StructurePieceType.ContextlessType) WitherBoulderPiece::new);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> WITHER_FALLEN_LOG_PIECE =
            STRUCTURE_PIECES.register("wither_fallen_log", () -> (StructurePieceType.ContextlessType) WitherFallenLogPiece::new);

    public static void register(IEventBus modEventBus) {
        STRUCTURE_TYPES.register(modEventBus);
        STRUCTURE_PIECES.register(modEventBus);
    }
}
