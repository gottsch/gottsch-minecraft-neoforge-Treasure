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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;


/**
 * @author by Mark Gottschling on 10/5/2025
 */
@EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // world gen provider must come first — its registries() future includes the custom treasure2:rarities registry
        TreasureWorldGenProvider worldGen = new TreasureWorldGenProvider(packOutput, event.getLookupProvider());
        generator.addProvider(event.includeServer(), worldGen);

        TreasureBlockTagsProvider blockTags = new TreasureBlockTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new TreasureItemTagsProvider(packOutput, event.getLookupProvider(), blockTags.contentsGetter(), existingFileHelper));
        // TreasureRarityTagsProvider omitted — treasure2:rarities needs DataPackRegistryEvent.NewRegistry + IRarity codec first
        // generator.addProvider(event.includeServer(), new TreasureRarityTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), TreasureLootTableProvider.create(packOutput, event.getLookupProvider()));

        generator.addProvider(event.includeServer(), new TreasureBiomeTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), new Recipes(packOutput, event.getLookupProvider()));

        generator.addProvider(event.includeClient(), new TreasureBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModelsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new LanguageGen(packOutput, "en_us"));
        generator.addProvider(event.includeClient(), new JapaneseLanguageGen(packOutput, "ja_jp"));
    }
}
