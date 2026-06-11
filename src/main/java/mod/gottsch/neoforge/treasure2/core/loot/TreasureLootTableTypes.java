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
package mod.gottsch.neoforge.treasure2.core.loot;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.registry.LootTableRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author by Mark Gottschling on 8/27/2025
 */
public class TreasureLootTableTypes {
    // registry identifier
    public static final ResourceKey<Registry<ILootTableTypes>> LOOT_TABLE_TYPES_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "loot_table_types"));

    // deferred registry
    public static final DeferredRegister<ILootTableTypes> LOOT_TABLE_TYPES_REGISTRY =
            DeferredRegister.create(LOOT_TABLE_TYPES_REGISTRY_KEY, Treasure.MODID);

    // actually create the custom registry (required for DeferredHolder.get() to resolve)
    public static final Registry<ILootTableTypes> REGISTRY = LOOT_TABLE_TYPES_REGISTRY.makeRegistry(builder -> {});

    public static DeferredHolder<ILootTableTypes, ILootTableTypes> UNKNOWN = LOOT_TABLE_TYPES_REGISTRY.register("unknown", () -> new LootTableType("unknown"));
    public static DeferredHolder<ILootTableTypes, ILootTableTypes> CHESTS = LOOT_TABLE_TYPES_REGISTRY.register("chests", () -> new LootTableType("chests"));
    public static DeferredHolder<ILootTableTypes, ILootTableTypes> WISHABLES = LOOT_TABLE_TYPES_REGISTRY.register("wishables", () -> new LootTableType("wishables"));
    public static DeferredHolder<ILootTableTypes, ILootTableTypes> INJECTS = LOOT_TABLE_TYPES_REGISTRY.register("injects", () -> new LootTableType("injects"));

    /*
     *
     */
    public static void register(IEventBus eventBus) {
        LOOT_TABLE_TYPES_REGISTRY.register(eventBus);
    }

    public static Optional<ILootTableTypes> getLootTableType(ResourceLocation name, ServerLevel level) {
        return getLootTableType(name, level.registryAccess());
    }

    public static Optional<ILootTableTypes> getLootTableType(ResourceLocation name, HolderLookup.Provider registries) {
        Optional<HolderLookup.RegistryLookup<ILootTableTypes>> lookup = registries.lookup(LOOT_TABLE_TYPES_REGISTRY_KEY);

        if (lookup.isEmpty()) {
            return Optional.empty();
        }

        ResourceKey<ILootTableTypes> entryKey = ResourceKey.create(LOOT_TABLE_TYPES_REGISTRY_KEY, name);
        Optional<Holder.Reference<ILootTableTypes>> lootTableType = lookup.get().get(entryKey);
        return lootTableType.map(Holder::value);
     }

    // NOTE use this only for interim, so the mod compiles until complete
    @Deprecated
    public static List<ResourceLocation> getLootTables(ILootTableTypes type, IRarity rarity) {
        List<ResourceLocation> lootTables = LootTableRegistry.getLootTableIds(type, rarity);
        return lootTables;
    }

    public static Optional<ResourceLocation> getKey(LootTableType type, HolderLookup.Provider registries) {
        Optional<HolderLookup.RegistryLookup<ILootTableTypes>> lookup = registries.lookup(LOOT_TABLE_TYPES_REGISTRY_KEY);

        if (lookup.isEmpty()) {
            return Optional.empty();
        }

        // 1. stream all registered elements (Holders).
        return lookup.get().listElements()
                // 2. filter to find the Holder whose value matches the input rarity object instance.
                .filter(holder -> holder.value().equals(type))
                // 3. extract the ResourceKey from the matching Holder
                .map(holder -> ((Holder.Reference<ILootTableTypes>) holder).getKey())
                .filter(Objects::nonNull)
                // 4. convert the ResourceKey to the ResourceLocation.
                .map(ResourceKey::location)
                // 5. take the first (and only) result.
                .findFirst();
    }
}
