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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.loot.ILootTableTypes;
import mod.gottsch.neoforge.treasure2.core.loot.TreasureLootTableTypes;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author by Mark Gottschling on 8/31/2025
 */
@EventBusSubscriber(modid = Treasure.MODID)
public enum LootTableRegistry {
    INSTANCE;

    /*
     * Guava Table of LootTables by Top-Level(Type) ex chests | wishables | injects, IRarity, List<ResourceLocation>
     */
    private final static Table<ILootTableTypes, IRarity, List<ResourceLocation>> REGISTRY = HashBasedTable.create();

    public static synchronized void register(ILootTableTypes type, IRarity rarity, ResourceLocation lootTable) {
        if(!REGISTRY.contains(type, rarity)) {
            REGISTRY.put(type, rarity, new ArrayList<>());
        }
        REGISTRY.get(type, rarity).add(lootTable);
    }

    public static synchronized List<ResourceLocation> getLootTableIds(ILootTableTypes type, IRarity rarity) {
        return Optional.ofNullable(REGISTRY.get(type, rarity)).orElse(new ArrayList<>());
    }

    public static LootTable getLootTable(ServerLevel level, ResourceLocation lootTableName) {
        ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, lootTableName);
        return level.getServer().reloadableRegistries().getLootTable(lootTableKey);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        // The event gives you the MinecraftServer instance directly.
        MinecraftServer server = event.getServer();

        Collection<ResourceLocation> allLootTables = getAllLootTableLocations(event.getServer());
        Treasure.LOGGER.debug("found " + allLootTables.size() + " loot tables on server start.");

        // step 1: filter the initial list of loot tables once to create a smaller subset
        List<ResourceLocation> treasureLootTables = allLootTables.stream()
                .filter(location -> location.getNamespace().equals(Treasure.MODID))
                .toList();

        Treasure.LOGGER.debug("found " + treasureLootTables.size() + " treasure loot tables.");

        // step 2: iterate through associations and use the filtered subset
        RarityLootTableAssociationRegistry.getAssociations().forEach(association -> {
            treasureLootTables.stream()
                    .filter(lootTable -> Paths.get(lootTable.getPath()).getParent() != null)
                    .filter(lootTable -> {
                        ResourceLocation parentPath = ResourceLocation.fromNamespaceAndPath(lootTable.getNamespace(), Paths.get(lootTable.getPath()).getParent().toString().replace("\\", "/"));
                        return association.lootTableId().equals(parentPath);
                    })
                    .forEach(matchingLootTable -> {
                        Optional<ILootTableTypes> type = TreasureLootTableTypes.getLootTableType(association.typeId(), event.getServer().registryAccess());
                        Optional<IRarity> rarity = TreasureRarities.getRarityByName(association.rarityId(), server.registryAccess());

                        type.ifPresent(lootTableType -> rarity.ifPresent(rarityEntry -> {
                            register(lootTableType, rarityEntry, matchingLootTable);
                        }));
                    });
        });
    }

    public static Collection<ResourceLocation> getAllLootTableLocations(MinecraftServer server) {
        Collection<ResourceLocation> allLootTableIds = server.reloadableRegistries()
                .getKeys(Registries.LOOT_TABLE);

        return allLootTableIds;
    }
}
