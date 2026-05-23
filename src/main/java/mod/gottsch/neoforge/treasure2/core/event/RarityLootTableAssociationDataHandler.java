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
package mod.gottsch.neoforge.treasure2.core.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.RarityLootTableAssociation;
import mod.gottsch.neoforge.treasure2.core.registry.RarityLootTableAssociationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;

/**
 * Handles loading custom rarity loot table association data from data packs.
 * @author by Mark Gottschling on 8/28/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RarityLootTableAssociationDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "rarity_associations/loot_table";

    public RarityLootTableAssociationDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new RarityLootTableAssociationDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading rarity loot table associations from data packs...");

        RarityLootTableAssociationRegistry.clear();

        jsonElementMap.forEach((location, jsonElement) ->
                RarityLootTableAssociation.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse rarity loot table association JSON for {}: {}", location, err))
                        .ifPresent(RarityLootTableAssociationRegistry::register)
        );
    }
}
