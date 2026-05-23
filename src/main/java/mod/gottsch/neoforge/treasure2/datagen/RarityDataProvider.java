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
package mod.gottsch.neoforge.treasure2.datagen;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.Rarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author by Mark Gottschling on 10/5/2025
 */
public class RarityDataProvider extends DatapackBuiltinEntriesProvider {

    public static RegistrySetBuilder getBuiltinEntries() {
        // 1. Create a new builder.
        RegistrySetBuilder builder = new RegistrySetBuilder();

        // 2. Add our custom rarity registry to the builder with all its entries.
        builder.add(TreasureRarities.RARITIES_REGISTRY_KEY, context -> {
        // these entries mirror the hardcoded defaults from ModRarities.java
        context.register(
                ResourceKey.create(TreasureRarities.RARITIES_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "common")),
                new Rarity("common", 0)
        );

    });
        return builder;
    }

    // The constructor sets up the provider.
    public RarityDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        // Corrected for NeoForge 1.21.1:
        // We now use the constructor overload that accepts the RegistrySetBuilder directly.
        // This Builder defines the "built-in" entries that will be generated and synchronized.
        super(output, registries, getBuiltinEntries(), Set.of(Treasure.MODID));
    }
}
