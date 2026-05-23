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
import mod.gottsch.neoforge.treasure2.core.rarity.RarityTagAssociation;
import mod.gottsch.neoforge.treasure2.core.registry.RarityTagAssociationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Handles loading custom rarity item association data from data packs.
 * @author by Mark Gottschling on 8/25/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RarityItemAssociationDataHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final String DATA_DIRECTORY = "rarity_associations/item";

    public RarityItemAssociationDataHandler() {
        super(GSON, DATA_DIRECTORY);
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new RarityItemAssociationDataHandler());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Treasure.LOGGER.info("loading rarity item associations from data packs...");

        RarityTagAssociationRegistry.Items.clear();

        jsonElementMap.forEach((location, jsonElement) ->
                RarityTagAssociation.CODEC
                        .parse(JsonOps.INSTANCE, jsonElement)
                        .resultOrPartial(err -> Treasure.LOGGER.error("failed to parse rarity item association JSON for {}: {}", location, err))
                        .ifPresent(association -> {
                            Path path = Paths.get(location.getPath());
                            if (path.getNameCount() > 1) {
                                String typeKey = path.subpath(0, path.getNameCount() - 1).toString();
                                RarityTagAssociationRegistry.Items.register(typeKey, association);
                            }
                        })
        );
    }
}
