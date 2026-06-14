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

import java.util.concurrent.CompletableFuture;

import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author Mark Gottschling on Nov 26, 2022
 *
 * Ported to NeoForge 1.21.1. Symbol-mapping notes vs the Forge source:
 *   - Forge SKULL_CROSSBONES -> SKULL_AND_CROSSBONES.
 *   - Forge WITHERWOOD_STICK / WITHERWOOD_ROOT items -> the port's WITHERWOOD_BRANCH_ITEM /
 *     WITHERWOOD_ROOT_ITEM block-items.
 *   - The legacy WITHER_LOG / WITHER_PLANKS blocks were not ported; their entries are dropped
 *     from the Ingredient.of(...) alternations, leaving the WITHERWOOD_* terms.
 */
public class Recipes extends RecipeProvider {

    public Recipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        /*
         * treasure tool
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.TREASURE_TOOL.get())
                .pattern("  i")
                .pattern(" s ")
                .pattern("x  ")
                .define('i', Items.IRON_INGOT)
                .define('s', Items.STICK)
                .define('x', Items.STONE)
                .unlockedBy("has_stick", has(Items.STICK))
                .save(output);

        // pilferer's lock pick
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.PILFERERS_LOCK_PICK.get())
                .pattern("xt")
                .pattern("x ")
                .define('x', Items.IRON_NUGGET)
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // thief's lock pick
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.THIEFS_LOCK_PICK.get())
                .pattern("xt")
                .pattern("x ")
                .define('x', Items.IRON_INGOT)
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // keyring
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.KEY_RING.get())
                .pattern("kx ")
                .pattern("x x")
                .pattern(" x ")
                .define('x', Items.IRON_INGOT)
                .define('k', Ingredient.of(TreasureItems.WOOD_KEY.get(),
                        TreasureItems.STONE_KEY.get(),
                        TreasureItems.IRON_KEY.get()))
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(output);

        // pouch
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.POUCH.get())
                .pattern(" ct")
                .pattern(" x ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('c', Ingredient.of(
                        TreasureItems.COPPER_COIN.get(),
                        TreasureItems.SILVER_COIN.get(),
                        TreasureItems.GOLD_COIN.get()))
                .define('x', Items.LEATHER)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // spider key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.SPIDER_KEY.get())
                .pattern("kt ")
                .pattern(" d ")
                .pattern(" e ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('k', TreasureItems.IRON_KEY.get())
                .define('d', Items.GLOWSTONE_DUST)
                .define('e', Items.SPIDER_EYE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // topaz key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.TOPAZ_KEY.get())
                .pattern("kt ")
                .pattern(" d ")
                .pattern(" g ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('k', TreasureItems.GOLD_KEY.get())
                .define('d', Items.GLOWSTONE_DUST)
                .define('g', TreasureItems.TOPAZ.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // onyx key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.ONYX_KEY.get())
                .pattern("kt ")
                .pattern(" d ")
                .pattern(" g ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('k', TreasureItems.GOLD_KEY.get())
                .define('d', Items.GLOWSTONE_DUST)
                .define('g', TreasureItems.ONYX.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // ruby key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.RUBY_KEY.get())
                .pattern("kt ")
                .pattern(" d ")
                .pattern(" r ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('k', TreasureItems.GOLD_KEY.get())
                .define('d', Items.GLOWSTONE_DUST)
                .define('r', TreasureItems.RUBY.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // sapphire key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.SAPPHIRE_KEY.get())
                .pattern("kt ")
                .pattern(" d ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('k', TreasureItems.GOLD_KEY.get())
                .define('d', Items.GLOWSTONE_DUST)
                .define('s', TreasureItems.SAPPHIRE.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // wither key
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TreasureItems.WITHER_KEY.get())
                .pattern(" bt")
                .pattern(" b ")
                .pattern(" br")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('b', TreasureItems.WITHERWOOD_BRANCH_ITEM.get())
                .define('r', TreasureItems.WITHERWOOD_ROOT_ITEM.get())
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // skull chest
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TreasureBlocks.SKULL_CHEST.get())
                .requires(TreasureItems.TREASURE_TOOL.get())
                .requires(Items.SKELETON_SKULL)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // gold skull chest
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TreasureBlocks.GOLD_SKULL_CHEST.get())
                .pattern("ggt")
                .pattern("gxg")
                .pattern("ggg")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('x', Items.SKELETON_SKULL)
                .define('g', Items.GOLD_INGOT)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // crystal skull chest
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TreasureBlocks.CRYSTAL_SKULL_CHEST.get())
                .pattern("ggt")
                .pattern("gxg")
                .pattern("ggg")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('x', Items.SKELETON_SKULL)
                .define('g', Items.DIAMOND)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // wither planks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_PLANKS.get(), 4)
                .requires(Ingredient.of(TreasureBlocks.WITHERWOOD_LOG.get(), TreasureBlocks.WITHERWOOD_WOOD.get()))
                .unlockedBy("has_log", has(TreasureBlocks.WITHERWOOD_LOG.get()))
                .save(output);

        // witherwood stairs
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_STAIRS.get(), 4)
                .pattern("  w")
                .pattern(" ww")
                .pattern("www")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood slab
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("www")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood wood
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_WOOD.get(), 4)
                .pattern("ww")
                .pattern("ww")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_LOG.get()))
                .unlockedBy("has_log", has(TreasureBlocks.WITHERWOOD_LOG.get()))
                .save(output);

        // witherwood fence
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_FENCE.get(), 3)
                .pattern("w#w")
                .pattern("w#w")
                .define('#', Items.STICK)
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood fence gate
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_FENCE_GATE.get(), 3)
                .pattern("#w#")
                .pattern("#w#")
                .define('#', Items.STICK)
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood door
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TreasureBlocks.WITHERWOOD_DOOR.get(), 3)
                .pattern("ww")
                .pattern("ww")
                .pattern("ww")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood trapdoor
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, TreasureBlocks.WITHERWOOD_TRAPDOOR.get(), 2)
                .pattern("www")
                .pattern("www")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood pressure plate
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, TreasureBlocks.WITHERWOOD_PRESSURE_PLATE.get())
                .pattern("ww")
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood button
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, TreasureBlocks.WITHERWOOD_BUTTON.get(), 4)
                .requires(Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood sign
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TreasureBlocks.WITHERWOOD_SIGN.get(), 3)
                .pattern("www")
                .pattern("www")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('w', Ingredient.of(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // witherwood hanging sign
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TreasureBlocks.WITHERWOOD_HANGING_SIGN.get(), 6)
                .pattern("# #")
                .pattern("www")
                .pattern("www")
                .define('#', Items.CHAIN)
                .define('w', Ingredient.of(TreasureBlocks.STRIPPED_WITHERWOOD_LOG.get()))
                .unlockedBy("has_planks", has(TreasureBlocks.WITHERWOOD_PLANKS.get()))
                .save(output);

        // gravestones
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.SKULL_AND_CROSSBONES.get())
                .pattern("  t")
                .pattern(" b ")
                .pattern("b b")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('b', Items.BONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_COBBLESTONE.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_MOSSY_COBBLESTONE.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.MOSSY_COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_OBSIDIAN.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.OBSIDIAN)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_POLISHED_GRANITE.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.POLISHED_GRANITE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_SMOOTH_QUARTZ.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.SMOOTH_QUARTZ)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE1_STONE.get())
                .pattern("  t")
                .pattern("   ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.STONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // gravestone 2
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_COBBLESTONE.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_MOSSY_COBBLESTONE.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.MOSSY_COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_OBSIDIAN.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.OBSIDIAN)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_POLISHED_GRANITE.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.POLISHED_GRANITE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_SMOOTH_QUARTZ.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.SMOOTH_QUARTZ)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE2_STONE.get())
                .pattern("  t")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.STONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // gravestone 3
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_COBBLESTONE.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_MOSSY_COBBLESTONE.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.MOSSY_COBBLESTONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_OBSIDIAN.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.OBSIDIAN)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_POLISHED_GRANITE.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.POLISHED_GRANITE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_SMOOTH_QUARTZ.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.SMOOTH_QUARTZ)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TreasureBlocks.GRAVESTONE3_STONE.get())
                .pattern(" st")
                .pattern(" s ")
                .pattern(" s ")
                .define('t', TreasureItems.TREASURE_TOOL.get())
                .define('s', Items.STONE)
                .unlockedBy("has_tool", has(TreasureItems.TREASURE_TOOL.get()))
                .save(output);

        // copper weapons smelting
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.CHIPPED_COPPER_SHORT_SWORD.get()), RecipeCategory.COMBAT,
                        Items.COPPER_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.CHIPPED_COPPER_SHORT_SWORD.get())))
                .save(output, "copper_ingot_from_chipped_short_sword");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.COPPER_SHORT_SWORD.get()), RecipeCategory.COMBAT,
                        Items.COPPER_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.COPPER_SHORT_SWORD.get())))
                .save(output, "copper_ingot_from_short_sword");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.COPPER_BROAD_AXE.get()), RecipeCategory.COMBAT,
                        Items.COPPER_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.COPPER_BROAD_AXE.get())))
                .save(output, "copper_ingot_from_broad_axe");

        // iron weapons smelting
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.CHIPPED_IRON_SHORT_SWORD.get()), RecipeCategory.COMBAT,
                        Items.IRON_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.CHIPPED_IRON_SHORT_SWORD.get())))
                .save(output, "iron_ingot_from_chipped_short_sword");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_SHORT_SWORD.get()), RecipeCategory.COMBAT,
                        Items.IRON_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_SHORT_SWORD.get())))
                .save(output, "iron_ingot_from_short_sword");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_BROAD_AXE.get()), RecipeCategory.COMBAT,
                        Items.IRON_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_BROAD_AXE.get())))
                .save(output, "iron_ingot_from_broad_axe");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_DWARVEN_AXE.get()), RecipeCategory.COMBAT,
                        Items.IRON_INGOT, 1.0f, 200)
                .unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_DWARVEN_AXE.get())))
                .save(output, "iron_ingot_from_dwarven_axe");
    }
}
