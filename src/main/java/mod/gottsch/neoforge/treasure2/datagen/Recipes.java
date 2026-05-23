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

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

/**
 * @author Mark Gottschling on Nov 26, 2022
 */
public class Recipes extends RecipeProvider {

    public Recipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // TODO: all recipes require TREASURE_TOOL which is not yet ported.
        // Restore recipes here once TreasureItems.TREASURE_TOOL is un-commented and registered.
        // Recipes to restore:
        //   - treasure_tool (shaped: iron ingot + stick + stone)
        //   - pilferers_lock_pick, thiefs_lock_pick (shaped: iron + TREASURE_TOOL)
        //   - key_ring (shaped: iron + key + TREASURE_TOOL)
        //   - spider_key, gem keys (topaz, onyx, ruby, sapphire) (shaped: key + gem + TREASURE_TOOL)
        //   - wither_key (shaped: witherwood stick/root + TREASURE_TOOL — needs witherwood items)
        //   - skull_chest, gold_skull_chest, crystal_skull_chest (shapeless/shaped: skull + TREASURE_TOOL)
        //   - pouch (shaped: coin + leather + TREASURE_TOOL — needs coins)
        //   - witherwood building blocks (needs witherwood blocks ported)
        //   - gravestones (shaped: stone variants + TREASURE_TOOL — needs gravestone blocks)
    }
}
