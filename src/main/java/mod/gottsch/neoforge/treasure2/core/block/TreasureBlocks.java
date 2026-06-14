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
package mod.gottsch.neoforge.treasure2.core.block;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.entity.TreasureBlockEntities;
import mod.gottsch.neoforge.treasure2.core.block.state.properties.TreasureWoodTypes;
import mod.gottsch.neoforge.treasure2.core.chest.ChestInventorySize;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayouts;
import net.minecraft.core.Direction;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 *
 * @author Mark Gottschling on Nov 14, 2022
 *
 */
public class TreasureBlocks {

	// functional interfaces
	static ToIntFunction<BlockState> light = (state) -> {
		if (
			//				Config.SERVER.effects.enableUndiscoveredEffects.get() &&
				!state.getValue(AbstractTreasureChestBlock.DISCOVERED)
		) {
			return 14;
		}
		return 0;
	};

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Treasure.MODID);

	// chests
	public static final DeferredBlock<Block> WOOD_CHEST = BLOCKS.register("wood_chest",
			() -> new StandardChestBlock(
					() -> TreasureBlockEntities.WOOD_CHEST_BLOCK_ENTITY_TYPE.get(),
					LockLayouts.STANDARD,
					Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light))
	);

	public static final DeferredBlock<Block> CRATE_CHEST = BLOCKS.register("crate_chest",
			() -> new StandardChestBlock(() -> TreasureBlockEntities.CRATE_CHEST_BLOCK_ENTITY_TYPE.get(),
					LockLayouts.CRATE, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> MOLDY_CRATE_CHEST = BLOCKS.register("crate_chest_moldy",
			() -> new StandardChestBlock(TreasureBlockEntities.MOLDY_CRATE_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.CRATE, Properties.of().mapColor(MapColor.WOOD).strength(2.0F).lightLevel(light)));

	public static final DeferredBlock<Block> IRONBOUND_CHEST = BLOCKS.register("ironbound_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.IRONBOUND_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildIronboundChest()));

	public static final DeferredBlock<Block> PIRATE_CHEST = BLOCKS.register("pirate_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.PIRATE_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> SAFE = BLOCKS.register("safe",
			() -> new StandardChestBlock(TreasureBlockEntities.SAFE_BLOCK_ENTITY_TYPE::get,
					LockLayouts.SAFE, Properties.of().mapColor(MapColor.METAL).strength(4.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildSafe()));

	public static final DeferredBlock<Block> IRON_STRONGBOX = BLOCKS.register("iron_strongbox",
			() -> new StandardChestBlock(TreasureBlockEntities.IRON_STRONGBOX_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.STRONGBOX.getSize(), LockLayouts.STRONGBOX,
					Properties.of().mapColor(MapColor.METAL).strength(4.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildStrongbox()));

	public static final DeferredBlock<Block> GOLD_STRONGBOX = BLOCKS.register("gold_strongbox",
			() -> new StandardChestBlock(TreasureBlockEntities.GOLD_STRONGBOX_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.STRONGBOX.getSize(), LockLayouts.STRONGBOX,
					Properties.of().mapColor(MapColor.WOOD).strength(4.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildStrongbox()));

	public static final DeferredBlock<Block> DREAD_PIRATE_CHEST = BLOCKS.register("dread_pirate_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.DREAD_PIRATE_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(4.0F).lightLevel(light)));

	public static final DeferredBlock<Block> COMPRESSOR_CHEST = BLOCKS.register("compressor_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.COMPRESSOR_CHEST_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.COMPRESOR.getSize(), LockLayouts.COMPRESSOR,
					Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildCompressorChest()));

	public static final DeferredBlock<Block> SKULL_CHEST = BLOCKS.register("skull_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.SKULL_CHEST_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.SKULL.getSize(), LockLayouts.SKULL,
					Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildSkull()));

	public static final DeferredBlock<Block> GOLD_SKULL_CHEST = BLOCKS.register("gold_skull_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.GOLD_SKULL_CHEST_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.SKULL.getSize(), LockLayouts.SKULL,
					Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildSkull()));

	public static final DeferredBlock<Block> CRYSTAL_SKULL_CHEST = BLOCKS.register("crystal_skull_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.CRYSTAL_SKULL_CHEST_BLOCK_ENTITY_TYPE::get,
					ChestInventorySize.SKULL.getSize(), LockLayouts.SKULL,
					Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildSkull()));

	public static final DeferredBlock<Block> CAULDRON_CHEST = BLOCKS.register("cauldron_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.CAULDRON_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.TOP_SPLIT, Properties.of().mapColor(MapColor.METAL).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildCauldronChest()));

	public static final DeferredBlock<Block> SPIDER_CHEST = BLOCKS.register("spider_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.SPIDER_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.SINGLE_STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildSpiderChest()));

	public static final DeferredBlock<Block> VIKING_CHEST = BLOCKS.register("viking_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.VIKING_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.VIKING, Properties.of().mapColor(MapColor.WOOD).strength(3.0F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildVikingChest()));

	public static final DeferredBlock<Block> CARDBOARD_BOX = BLOCKS.register("cardboard_box",
			() -> new StandardChestBlock(TreasureBlockEntities.CARDBOARD_BOX_BLOCK_ENTITY_TYPE::get,
					LockLayouts.TOP_SPLIT, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> MILK_CRATE = BLOCKS.register("milk_crate",
			() -> new StandardChestBlock(TreasureBlockEntities.MILK_CRATE_BLOCK_ENTITY_TYPE::get,
					LockLayouts.MILK_CRATE, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildMilkCrate()));

	public static final DeferredBlock<Block> BARREL_CHEST = BLOCKS.register("barrel_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.BARREL_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.SINGLE_FULL, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildFullBlock()));

	public static final DeferredBlock<Block> VANILLA_CHEST = BLOCKS.register("vanilla_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.VANILLA_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.SINGLE_STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> WITHER_CHEST = BLOCKS.register("wither_chest",
			() -> new WitherChestBlock(TreasureBlockEntities.WITHER_CHEST_BLOCK_ENTITY_TYPE::get,
					LockLayouts.ARMOIRE, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> WITHER_CHEST_TOP = BLOCKS.register("wither_chest_top",
			() -> new WitherChestTopBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.5F).noLootTable()));

	public static final DeferredBlock<Block> BONE_CHEST = BLOCKS.register("bone_chest",
			() -> new BoneChestBlock(TreasureBlockEntities.BONE_CHEST::get,
					LockLayouts.SINGLE_STANDARD, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).lightLevel(light)));

	public static final DeferredBlock<Block> CELESTIAL_CHEST = BLOCKS.register("celestial_chest",
			() -> new StandardChestBlock(TreasureBlockEntities.CELESTIAL_CHEST::get,
					LockLayouts.CELESTIAL, Properties.of().mapColor(MapColor.METAL).strength(2.5F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildCelestialChest()));

	public static final DeferredBlock<Block> INFERNAL_CHEST = BLOCKS.register("infernal_chest",
			() -> new StandardChestBlock(() -> TreasureBlockEntities.INFERNAL_CHEST.get(),
					LockLayouts.CELESTIAL, Properties.of().mapColor(MapColor.METAL).strength(2.5F).lightLevel(light))
					.setBounds(TreasureShapeBuilder.buildCelestialChest()));

	// ore
	public static final Supplier<Properties> ORE_PROPS = () -> Properties.of().mapColor(MapColor.STONE).strength(3.0F, 5.0F);
	public static final Supplier<Properties> DEEPSLATE_ORE_PROPS = () -> Properties.of().mapColor(MapColor.STONE).strength(3.0F, 6.0F);

	public static final DeferredBlock<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Block(ORE_PROPS.get()));
	public static final DeferredBlock<Block> DEEPSLATE_TOPAZ_ORE = BLOCKS.register("deepslate_topaz_ore", () -> new Block(DEEPSLATE_ORE_PROPS.get()));

	public static final DeferredBlock<Block> ONYX_ORE = BLOCKS.register("onyx_ore", () -> new Block(ORE_PROPS.get()));
	public static final DeferredBlock<Block> DEEPSLATE_ONYX_ORE = BLOCKS.register("deepslate_onyx_ore", () -> new Block(DEEPSLATE_ORE_PROPS.get()));

	public static final DeferredBlock<Block> RUBY_ORE = BLOCKS.register("ruby_ore", () -> new Block(ORE_PROPS.get()));
	public static final DeferredBlock<Block> DEEPSLATE_RUBY_ORE = BLOCKS.register("deepslate_ruby_ore", () -> new Block(DEEPSLATE_ORE_PROPS.get()));

	public static final DeferredBlock<Block> SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", () -> new Block(ORE_PROPS.get()));
	public static final DeferredBlock<Block> DEEPSLATE_SAPPHIRE_ORE = BLOCKS.register("deepslate_sapphire_ore", () -> new Block(DEEPSLATE_ORE_PROPS.get()));

	// wishing wells
	public static final Supplier<Properties> WISHING_WELL_PROPS =
			() -> Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE);

	public static final DeferredBlock<Block> WISHING_WELL = BLOCKS.register("wishing_well_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));
	public static final DeferredBlock<Block> WISHING_WELL_COBBLESTONE = BLOCKS.register("wishing_well_cobblestone_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));
	public static final DeferredBlock<Block> WISHING_WELL_MOSSY_COBBLESTONE = BLOCKS.register("wishing_well_mossy_cobblestone_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));
	public static final DeferredBlock<Block> WISHING_WELL_STONE_BRICKS = BLOCKS.register("wishing_well_stone_bricks_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));
	public static final DeferredBlock<Block> WISHING_WELL_MOSSY_STONE_BRICKS = BLOCKS.register("wishing_well_mossy_stone_bricks_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));
	public static final DeferredBlock<Block> DESERT_WISHING_WELL = BLOCKS.register("desert_wishing_well_block",
			() -> new WishingWellBlock(WISHING_WELL_PROPS.get()));

	// clover (decorative block placed by world gen near wells; the CloverItem transforms vanilla blocks into wells)
	public static final DeferredBlock<Block> CLOVER = BLOCKS.register("clover_block",
			() -> new Block(Properties.ofFullCopy(Blocks.TALL_GRASS).noLootTable()));

	// falling blocks — look like normal terrain but collapse when stepped on by a player
	public static final DeferredBlock<Block> FALLING_GRASS = BLOCKS.register("falling_grass",
			() -> new FallingGrassBlock(Properties.of().mapColor(MapColor.DIRT).strength(0.6F).sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> FALLING_SAND = BLOCKS.register("falling_sand",
			() -> new FallingSandBlock(Properties.of().mapColor(MapColor.SAND).strength(0.6F).sound(SoundType.SAND)));
	public static final DeferredBlock<Block> FALLING_RED_SAND = BLOCKS.register("falling_red_sand",
			() -> new FallingRedSandBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.SAND)));

	// spanish moss — hanging plant
	public static final DeferredBlock<Block> SPANISH_MOSS = BLOCKS.register("spanish_moss",
			() -> new SpanishMossBlock(Properties.of().mapColor(MapColor.WOOD)));

	// strangle vines — growing-plant pair (head + body), like twisting/weeping vines
	public static final DeferredBlock<Block> STRANGLE_VINES = BLOCKS.register("strangle_vines",
			() -> new StrangleVinesBlock(Properties.ofFullCopy(Blocks.TWISTING_VINES).noLootTable()));
	public static final DeferredBlock<Block> STRANGLE_VINES_PLANT = BLOCKS.register("strangle_vines_plant",
			() -> new StrangleVinesPlantBlock(Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).noLootTable()));

	// gravestones — 3 styles × 6 materials + skull/crossbones (19 total)
	public static final Supplier<Properties> GRAVESTONE_PROPS =
			() -> Properties.of().mapColor(MapColor.STONE).strength(3.0F).sound(SoundType.STONE);

	public static final DeferredBlock<Block> GRAVESTONE1_STONE = BLOCKS.register("gravestone1_stone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE1_COBBLESTONE = BLOCKS.register("gravestone1_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE1_MOSSY_COBBLESTONE = BLOCKS.register("gravestone1_mossy_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE1_POLISHED_GRANITE = BLOCKS.register("gravestone1_polished_granite",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE1_OBSIDIAN = BLOCKS.register("gravestone1_obsidian",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE1_SMOOTH_QUARTZ = BLOCKS.register("gravestone1_smooth_quartz",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));

	public static final DeferredBlock<Block> GRAVESTONE2_STONE = BLOCKS.register("gravestone2_stone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE2_COBBLESTONE = BLOCKS.register("gravestone2_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE2_MOSSY_COBBLESTONE = BLOCKS.register("gravestone2_mossy_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE2_POLISHED_GRANITE = BLOCKS.register("gravestone2_polished_granite",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE2_OBSIDIAN = BLOCKS.register("gravestone2_obsidian",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE2_SMOOTH_QUARTZ = BLOCKS.register("gravestone2_smooth_quartz",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));

	public static final DeferredBlock<Block> GRAVESTONE3_STONE = BLOCKS.register("gravestone3_stone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));
	public static final DeferredBlock<Block> GRAVESTONE3_COBBLESTONE = BLOCKS.register("gravestone3_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));
	public static final DeferredBlock<Block> GRAVESTONE3_MOSSY_COBBLESTONE = BLOCKS.register("gravestone3_mossy_cobblestone",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));
	public static final DeferredBlock<Block> GRAVESTONE3_POLISHED_GRANITE = BLOCKS.register("gravestone3_polished_granite",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));
	public static final DeferredBlock<Block> GRAVESTONE3_OBSIDIAN = BLOCKS.register("gravestone3_obsidian",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));
	public static final DeferredBlock<Block> GRAVESTONE3_SMOOTH_QUARTZ = BLOCKS.register("gravestone3_smooth_quartz",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));

	public static final DeferredBlock<Block> SKULL_AND_CROSSBONES = BLOCKS.register("skull_and_crossbones",
			() -> new GravestoneBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildSkullCrossbones()));

	// skeleton — two-part gravestone (bottom/feet + top/skull)
	public static final DeferredBlock<Block> SKELETON = BLOCKS.register("skeleton",
			() -> new SkeletonBlock(GRAVESTONE_PROPS.get()));

	// proximity spawners (invisible, air-like — placed by world gen / structures)
	public static final DeferredBlock<Block> PROXIMITY_SPAWNER = BLOCKS.register("proximity_spawner",
			() -> new ProximityBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
	public static final DeferredBlock<Block> PROXIMITY_MULTI_SPAWNER = BLOCKS.register("proximity_multi_spawner",
			() -> new ProximityMultiSpawnerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
	public static final DeferredBlock<Block> PROXIMITY_MOBSET_SPAWNER = BLOCKS.register("proximity_mobset_spawner",
			() -> new ProximityMobSetSpawnerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));

	// structure markers (jigsaw-only — replaced by structure processors at place time)
	public static final DeferredBlock<Block> STRUCTURE_MOB_SET = BLOCKS.register("structure_mob_set",
			() -> new StructureMobSetBlock(Properties.of().noLootTable()));
	public static final DeferredBlock<Block> STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER = BLOCKS.register("structure_neighbor_dependent_state_marker",
			() -> new StructureNeighborDependentStateMarkerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));

	// gravestone spawners (functional gravestones that spawn a bound soul on proximity)
	public static final DeferredBlock<Block> GRAVESTONE1_SPAWNER_STONE = BLOCKS.register("gravestone1_spawner_stone",
			() -> new GravestoneSpawnerBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone1()));
	public static final DeferredBlock<Block> GRAVESTONE2_SPAWNER_COBBLESTONE = BLOCKS.register("gravestone2_spawner_cobblestone",
			() -> new GravestoneSpawnerBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone2()));
	public static final DeferredBlock<Block> GRAVESTONE3_SPAWNER_OBSIDIAN = BLOCKS.register("gravestone3_spawner_obsidian",
			() -> new GravestoneSpawnerBlock(GRAVESTONE_PROPS.get()).setBounds(TreasureShapeBuilder.buildGravestone3()));

	// witherwood structure blocks (decorative wither-tree pieces placed by world gen / structures)
	public static final DeferredBlock<Block> WITHERWOOD_BROKEN_LOG = BLOCKS.register("witherwood_broken_log",
			() -> new WitherBrokenLogBlock(Properties.of().mapColor(MapColor.WOOD)));
	public static final DeferredBlock<Block> WITHERWOOD_BRANCH = BLOCKS.register("witherwood_branch",
			() -> new WitherBranchBlock(Properties.of().mapColor(MapColor.WOOD)));
	public static final DeferredBlock<Block> WITHERWOOD_ROOT = BLOCKS.register("witherwood_root",
			() -> new WitherRootBlock(Properties.of().mapColor(MapColor.WOOD)));
	public static final DeferredBlock<Block> WITHERWOOD_TWIG = BLOCKS.register("witherwood_twig",
			() -> new WitherTwigBlock(Properties.of()));

	// witherwood woodset — logs / wood / stripped (axis-aware)
	public static final DeferredBlock<Block> WITHERWOOD_LOG = BLOCKS.register("witherwood_log",
			() -> witherwoodLog(MapColor.WOOD, MapColor.PODZOL));
	public static final DeferredBlock<Block> WITHERWOOD_WOOD = BLOCKS.register("witherwood_wood",
			() -> witherwoodLog(MapColor.WOOD, MapColor.PODZOL));
	public static final DeferredBlock<Block> STRIPPED_WITHERWOOD_LOG = BLOCKS.register("stripped_witherwood_log",
			() -> log(MapColor.PODZOL, MapColor.PODZOL));
	public static final DeferredBlock<Block> STRIPPED_WITHERWOOD_WOOD = BLOCKS.register("stripped_witherwood_wood",
			() -> log(MapColor.PODZOL, MapColor.PODZOL));

	// witherwood woodset — planks + processed blocks
	public static final DeferredBlock<Block> WITHERWOOD_PLANKS = BLOCKS.register("witherwood_planks",
			() -> new WitherPlanksBlock(Properties.ofFullCopy(Blocks.OAK_PLANKS)));
	public static final DeferredBlock<Block> WITHERWOOD_SLAB = BLOCKS.register("witherwood_slab",
			() -> new SlabBlock(Properties.ofFullCopy(Blocks.OAK_SLAB)));
	public static final DeferredBlock<Block> WITHERWOOD_STAIRS = BLOCKS.register("witherwood_stairs",
			() -> new StairBlock(WITHERWOOD_PLANKS.get().defaultBlockState(), Properties.ofFullCopy(Blocks.OAK_STAIRS)));
	public static final DeferredBlock<Block> WITHERWOOD_FENCE = BLOCKS.register("witherwood_fence",
			() -> new FenceBlock(Properties.ofFullCopy(Blocks.OAK_FENCE)));
	public static final DeferredBlock<Block> WITHERWOOD_FENCE_GATE = BLOCKS.register("witherwood_fence_gate",
			() -> new FenceGateBlock(TreasureWoodTypes.WITHERWOOD_TYPE, Properties.ofFullCopy(Blocks.OAK_PLANKS)));
	public static final DeferredBlock<Block> WITHERWOOD_BUTTON = BLOCKS.register("witherwood_button",
			() -> woodenButton(BlockSetType.OAK));
	public static final DeferredBlock<Block> WITHERWOOD_PRESSURE_PLATE = BLOCKS.register("witherwood_pressure_plate",
			() -> new PressurePlateBlock(BlockSetType.MANGROVE, Properties.ofFullCopy(Blocks.MANGROVE_PRESSURE_PLATE)));
	public static final DeferredBlock<Block> WITHERWOOD_DOOR = BLOCKS.register("witherwood_door",
			() -> new DoorBlock(BlockSetType.CRIMSON, Properties.ofFullCopy(Blocks.CRIMSON_DOOR)));
	public static final DeferredBlock<Block> WITHERWOOD_TRAPDOOR = BLOCKS.register("witherwood_trapdoor",
			() -> new TrapDoorBlock(BlockSetType.SPRUCE, Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

	// witherwood woodset — signs
	public static final DeferredBlock<Block> WITHERWOOD_SIGN = BLOCKS.register("witherwood_sign",
			() -> new TreasureStandingSignBlock(TreasureWoodTypes.WITHERWOOD_TYPE, Properties.ofFullCopy(Blocks.OAK_SIGN)));
	public static final DeferredBlock<Block> WITHERWOOD_WALL_SIGN = BLOCKS.register("witherwood_wall_sign",
			() -> new TreasureWallSignBlock(TreasureWoodTypes.WITHERWOOD_TYPE, Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));
	public static final DeferredBlock<Block> WITHERWOOD_HANGING_SIGN = BLOCKS.register("witherwood_hanging_sign",
			() -> new TreasureHangingSignBlock(TreasureWoodTypes.WITHERWOOD_TYPE, Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
	public static final DeferredBlock<Block> WITHERWOOD_WALL_HANGING_SIGN = BLOCKS.register("witherwood_wall_hanging_sign",
			() -> new TreasureWallHangingSignBlock(TreasureWoodTypes.WITHERWOOD_TYPE, Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));
//
//	// (legacy commented-out registrations below)
//	public static final RegistryObject<Block> GRAVESTONE1_STONE = Registration.BLOCKS.register("gravestone1_stone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE1_COBBLESTONE = Registration.BLOCKS.register("gravestone1_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE1_MOSSY_COBBLESTONE = Registration.BLOCKS.register("gravestone1_mossy_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE1_POLISHED_GRANITE = Registration.BLOCKS.register("gravestone1_polished_granite", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE1_OBSIDIAN = Registration.BLOCKS.register("gravestone1_obsidian", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE1_SMOOTH_QUARTZ = Registration.BLOCKS.register("gravestone1_smooth_quartz", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//
//	public static final RegistryObject<Block> GRAVESTONE2_STONE = Registration.BLOCKS.register("gravestone2_stone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE2_COBBLESTONE = Registration.BLOCKS.register("gravestone2_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE2_MOSSY_COBBLESTONE = Registration.BLOCKS.register("gravestone2_mossy_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE2_POLISHED_GRANITE = Registration.BLOCKS.register("gravestone2_polished_granite", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE2_OBSIDIAN = Registration.BLOCKS.register("gravestone2_obsidian", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE2_SMOOTH_QUARTZ = Registration.BLOCKS.register("gravestone2_smooth_quartz", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//
//	public static final RegistryObject<Block> GRAVESTONE3_STONE = Registration.BLOCKS.register("gravestone3_stone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//	public static final RegistryObject<Block> GRAVESTONE3_COBBLESTONE = Registration.BLOCKS.register("gravestone3_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//	public static final RegistryObject<Block> GRAVESTONE3_MOSSY_COBBLESTONE = Registration.BLOCKS.register("gravestone3_mossy_cobblestone", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//	public static final RegistryObject<Block> GRAVESTONE3_POLISHED_GRANITE = Registration.BLOCKS.register("gravestone3_polished_granite", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//	public static final RegistryObject<Block> GRAVESTONE3_OBSIDIAN = Registration.BLOCKS.register("gravestone3_obsidian", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//	public static final RegistryObject<Block> GRAVESTONE3_SMOOTH_QUARTZ = Registration.BLOCKS.register("gravestone3_smooth_quartz", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//
//	public static final RegistryObject<Block> SKULL_CROSSBONES = Registration.BLOCKS.register("skull_and_crossbones", () -> new GravestoneBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildSkullCrossbones()));
//
//	public static final RegistryObject<Block> SKELETON = Registration.BLOCKS.register("skeleton", () -> new SkeletonBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)));
//
//	// proximity spawners
//	public static final RegistryObject<Block> PROXIMITY_SPAWNER = Registration.BLOCKS.register("proximity_spawner", () -> new ProximityBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
//	public static final RegistryObject<Block> PROXIMITY_MULTI_SPAWNER = Registration.BLOCKS.register("proximity_multi_spawner", () -> new ProximityMultiSpawnerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
//	public static final RegistryObject<Block> PROXIMITY_MOBSET_SPAWNER = Registration.BLOCKS.register("proximity_mobset_spawner", () -> new ProximityMobSetSpawnerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
//
//	// gravestone spawners
//	public static final RegistryObject<Block> GRAVESTONE1_SPAWNER_STONE = Registration.BLOCKS.register("gravestone1_spawner_stone", () -> new GravestoneSpawnerBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone1()));
//	public static final RegistryObject<Block> GRAVESTONE2_SPAWNER_COBBLESTONE = Registration.BLOCKS.register("gravestone2_spawner_cobblestone", () -> new GravestoneSpawnerBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone2()));
//	public static final RegistryObject<Block> GRAVESTONE3_SPAWNER_OBSIDIAN = Registration.BLOCKS.register("gravestone3_spawner_obsidian", () -> new GravestoneSpawnerBlock(Properties.of().mapColor(MapColor.STONE)
//			.strength(3.0F).sound(SoundType.STONE)).setBounds(TreasureShapeBuilder.buildGravestone3()));
//
////	public static final RegistryObject<Block> DEFERRED_RANDOM_VANILLA_SPAWNER = Registration.BLOCKS.register("deferred_random_vanilla_spawner", () -> new DeferredRandomVanillaSpawnerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
////			.strength(3.0F).sound(SoundType.STONE)));
////	public static final RegistryObject<Block> DEFERRED_WITHER_TREE_GENERATOR = Registration.BLOCKS.register("deferred_wither_tree_generator", () -> new DeferredGeneratorBlock(DeferredWitherTreeGeneratorBlockEntity.class, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
////			.strength(3.0F).sound(SoundType.STONE)));
////	public static final RegistryObject<Block> DEFERRED_SURFACE_GENERATOR = Registration.BLOCKS.register("deferred_surface_generator", () -> new DeferredGeneratorBlock(DeferredSurfaceGeneratorBlockEntity.class, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
////			.strength(3.0F).sound(SoundType.STONE)));
////	public static final RegistryObject<Block> DEFERRED_SUBAQUATIC_GENERATOR = Registration.BLOCKS.register("deferred_subaquatic_generator", () -> new DeferredGeneratorBlock(DeferredSubaquaticGeneratorBlockEntity.class, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
////			.strength(3.0F).sound(SoundType.STONE)));
////	public static final RegistryObject<Block> DEFERRED_PIT_GENERATOR = Registration.BLOCKS.register("deferred_pit_generator", () -> new DeferredGeneratorBlock(DeferredPitGeneratorBlockEntity.class, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
////			.strength(3.0F).sound(SoundType.STONE)));
//
//	// falling blocks
//	public static final RegistryObject<Block> FALLING_GRASS = Registration.BLOCKS.register("falling_grass", () -> new FallingGrassBlock(Properties.of().mapColor(MapColor.DIRT)
//			.strength(0.6F).sound(SoundType.GRASS)));
//	public static final RegistryObject<Block> FALLING_SAND = Registration.BLOCKS.register("falling_sand", () -> new FallingSandBlock(Properties.of().mapColor(MapColor.SAND)
//			.strength(0.6F).sound(SoundType.SAND)));
//	public static final RegistryObject<Block> FALLING_RED_SAND = Registration.BLOCKS.register("falling_red_sand", () -> new FallingRedSandBlock(Properties.of().mapColor(MapColor.SAND)
//			.strength(0.6F).sound(SoundType.SAND)));
//
//	// wither
//
//	// legacy
//	public static final RegistryObject<Block> WITHER_LOG = Registration.BLOCKS.register("wither_log", () -> witherwoodLog(MapColor.WOOD, MapColor.PODZOL));
//	public static final RegistryObject<Block> WITHER_BROKEN_LOG = Registration.BLOCKS.register("wither_broken_log", () -> log(MapColor.WOOD, MapColor.PODZOL));
////	public static final RegistryObject<Block> WITHER_SOUL_LOG = Registration.BLOCKS.register("wither_soul_log", () -> new WitherSoulLog(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHER_BRANCH = Registration.BLOCKS.register("wither_branch", () -> new WitherBranchBlock(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHER_ROOT = Registration.BLOCKS.register("wither_root", () -> new WitherRootBlock(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHER_PLANKS = Registration.BLOCKS.register("wither_planks", () -> new WitherPlanksBlock(Properties.copy(Blocks.OAK_PLANKS)));
//
//	// current
//	public static final RegistryObject<Block> WITHERWOOD_BROKEN_LOG = Registration.BLOCKS.register("witherwood_broken_log", () -> new WitherBrokenLogBlock(Properties.of().mapColor(MapColor.WOOD)));
////	public static final RegistryObject<Block> WITHERWOOD_SOUL_LOG = Registration.BLOCKS.register("witherwood_soul_log", () -> new WitherSoulLog(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHERWOOD_BRANCH = Registration.BLOCKS.register("witherwood_branch", () -> new WitherBranchBlock(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHERWOOD_ROOT = Registration.BLOCKS.register("witherwood_root", () -> new WitherRootBlock(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WITHERWOOD_TWIG = Registration.BLOCKS.register("witherwood_twig", () -> new WitherTwigBlock(Properties.of()));
//
//	public static final RegistryObject<Block> WITHERWOOD_LOG = Registration.BLOCKS.register("witherwood_log", () -> witherwoodLog(MapColor.WOOD, MapColor.PODZOL));
//	public static final RegistryObject<Block> WITHERWOOD_WOOD = Registration.BLOCKS.register("witherwood_wood", () -> witherwoodLog(MapColor.WOOD, MapColor.PODZOL));
//	public static final RegistryObject<Block> STRIPPED_WITHERWOOD_LOG = Registration.BLOCKS.register("stripped_witherwood_log", () -> log(MapColor.PODZOL, MapColor.PODZOL));
//	public static final RegistryObject<Block> STRIPPED_WITHERWOOD_WOOD = Registration.BLOCKS.register("stripped_witherwood_wood", () -> log(MapColor.PODZOL, MapColor.PODZOL));
//
//	public static final RegistryObject<Block> WITHERWOOD_PLANKS = Registration.BLOCKS.register("witherwood_planks", () -> new WitherPlanksBlock(Properties.copy(Blocks.OAK_PLANKS)));
//	public static final RegistryObject<Block> WITHERWOOD_SLAB = Registration.BLOCKS.register("witherwood_slab", () -> new SlabBlock(Properties.copy(Blocks.OAK_SLAB)));
//	public static final RegistryObject<Block> WITHERWOOD_STAIRS = Registration.BLOCKS.register("witherwood_stairs", () -> new StairBlock(() -> WITHERWOOD_PLANKS.get().defaultBlockState(), Properties.copy(Blocks.OAK_STAIRS)));
//	public static final RegistryObject<Block> WITHERWOOD_FENCE = Registration.BLOCKS.register("witherwood_fence", () -> new FenceBlock(Properties.copy(Blocks.OAK_FENCE)));
//	public static final RegistryObject<Block> WITHERWOOD_FENCE_GATE = Registration.BLOCKS.register("witherwood_fence_gate", () -> new FenceGateBlock(Properties.copy(Blocks.OAK_PLANKS), TreasureWoodTypes.WITHERWOOD_TYPE));
//	public static final RegistryObject<Block> WITHERWOOD_BUTTON = Registration.BLOCKS.register("witherwood_button", () -> woodenButton(BlockSetType.OAK));
//	public static final RegistryObject<Block> WITHERWOOD_PRESSURE_PLATE = Registration.BLOCKS.register("witherwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
//			Properties.copy(Blocks.MANGROVE_PRESSURE_PLATE), BlockSetType.MANGROVE));
//	public static final RegistryObject<Block> WITHERWOOD_DOOR = Registration.BLOCKS.register("witherwood_door", () -> new DoorBlock(Properties.copy(Blocks.CRIMSON_DOOR), BlockSetType.CRIMSON));
//	public static final RegistryObject<Block> WITHERWOOD_TRAPDOOR = Registration.BLOCKS.register("witherwood_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.SPRUCE));
//
//	public static final RegistryObject<Block> WITHERWOOD_SIGN = Registration.BLOCKS.register("witherwood_sign", () -> new TreasureStandingSignBlock(Properties.copy(Blocks.OAK_SIGN), TreasureWoodTypes.WITHERWOOD_TYPE));
//	public static final RegistryObject<Block> WITHERWOOD_WALL_SIGN = Registration.BLOCKS.register("witherwood_wall_sign", () -> new TreasureWallSignBlock(Properties.copy(Blocks.OAK_WALL_SIGN), TreasureWoodTypes.WITHERWOOD_TYPE));
//	public static final RegistryObject<Block> WITHERWOOD_HANGING_SIGN = Registration.BLOCKS.register("witherwood_hanging_sign", () -> new TreasureHangingSignBlock(Properties.copy(Blocks.OAK_HANGING_SIGN), TreasureWoodTypes.WITHERWOOD_TYPE));
//	public static final RegistryObject<Block> WITHERWOOD_WALL_HANGING_SIGN = Registration.BLOCKS.register("witherwood_wall_hanging_sign", () -> new TreasureWallHangingSignBlock(Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), TreasureWoodTypes.WITHERWOOD_TYPE));
//
//	public static final RegistryObject<Block> STRANGLE_VINES = Registration.BLOCKS.register("strangle_vines", () -> new StrangleVinesBlock(Properties.copy(Blocks.TWISTING_VINES)));
//	public static final RegistryObject<Block> STRANGLE_VINES_PLANT = Registration.BLOCKS.register("strangle_vines_plant", () -> new StrangleVinesPlantBlock(Properties.copy(Blocks.TWISTING_VINES_PLANT)));
//
//	// other
//	public static final RegistryObject<Block> SPANISH_MOSS = Registration.BLOCKS.register("spanish_moss", () -> new SpanishMossBlock(Properties.of().mapColor(MapColor.WOOD)));
//	public static final RegistryObject<Block> WISHING_WELL = Registration.BLOCKS.register("wishing_well_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//
//	public static final RegistryObject<Block> WISHING_WELL_COBBLESTONE = Registration.BLOCKS.register("wishing_well_cobblestone_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//	public static final RegistryObject<Block> WISHING_WELL_MOSSY_COBBLESTONE = Registration.BLOCKS.register("wishing_well_mossy_cobblestone_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//
//	public static final RegistryObject<Block> WISHING_WELL_STONE_BRICKS = Registration.BLOCKS.register("wishing_well_stone_bricks_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//	public static final RegistryObject<Block> WISHING_WELL_MOSSY_STONE_BRICKS = Registration.BLOCKS.register("wishing_well_mossy_stone_bricks_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//
//	public static final RegistryObject<Block> DESERT_WISHING_WELL = Registration.BLOCKS.register("desert_wishing_well_block", () -> new WishingWellBlock(
//			Properties.of().mapColor(MapColor.STONE).strength(2.0F).sound(SoundType.STONE)));
//
//	public static final RegistryObject<Block> CLOVER = Registration.BLOCKS.register("clover_block", () -> new Block(Properties.copy(Blocks.TALL_GRASS)));
//
//	public static final RegistryObject<Block> STRUCTURE_MOB_SET = Registration.BLOCKS.register("structure_mob_set", () -> new StructureMobSetBlock(Properties.of().noLootTable()));
//	public static final RegistryObject<Block> STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER = Registration.BLOCKS.register("structure_neighbor_dependent_state_marker", () -> new StructureNeighborDependentStateMarkerBlock(Properties.of().replaceable().noCollission().noLootTable().air()));
//
//	// collections
//	public static final List<RegistryObject<Block>> CHESTS = new ArrayList<>(25);
//	public static final List<RegistryObject<Block>> GRAVESTONES = new ArrayList<>(25);
//	public static final List<RegistryObject<Block>> GRAVESTONE_SPAWNERS = new ArrayList<>(3);
//
//	static {
//		CHESTS.add(WOOD_CHEST);
//		CHESTS.add(CRATE_CHEST);
//		CHESTS.add(MOLDY_CRATE_CHEST);
//		CHESTS.add(IRONBOUND_CHEST);
//		CHESTS.add(PIRATE_CHEST);
//		CHESTS.add(SAFE);
//		CHESTS.add(IRON_STRONGBOX);
//		CHESTS.add(GOLD_STRONGBOX);
//		CHESTS.add(DREAD_PIRATE_CHEST);
//		CHESTS.add(COMPRESSOR_CHEST);
//		CHESTS.add(SKULL_CHEST);
//		CHESTS.add(GOLD_SKULL_CHEST);
//		CHESTS.add(CRYSTAL_SKULL_CHEST);
//		CHESTS.add(CAULDRON_CHEST);
//		CHESTS.add(SPIDER_CHEST);
//		CHESTS.add(VIKING_CHEST);
//		CHESTS.add(CARDBOARD_BOX);
//		CHESTS.add(MILK_CRATE);
//		CHESTS.add(WITHER_CHEST);
//		CHESTS.add(BARREL_CHEST);
//		CHESTS.add(VANILLA_CHEST);
//		CHESTS.add(BONE_CHEST);
//		CHESTS.add(CELESTIAL_CHEST);
//		CHESTS.add(INFERNAL_CHEST);
//
//		GRAVESTONES.add(GRAVESTONE1_STONE);
//		GRAVESTONES.add(GRAVESTONE1_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE1_MOSSY_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE1_POLISHED_GRANITE);
//		GRAVESTONES.add(GRAVESTONE1_OBSIDIAN);
//		GRAVESTONES.add(GRAVESTONE1_SMOOTH_QUARTZ);
//
//		GRAVESTONES.add(GRAVESTONE2_STONE);
//		GRAVESTONES.add(GRAVESTONE2_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE2_MOSSY_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE2_POLISHED_GRANITE);
//		GRAVESTONES.add(GRAVESTONE2_OBSIDIAN);
//		GRAVESTONES.add(GRAVESTONE2_SMOOTH_QUARTZ);
//
//		GRAVESTONES.add(GRAVESTONE3_STONE);
//		GRAVESTONES.add(GRAVESTONE3_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE3_MOSSY_COBBLESTONE);
//		GRAVESTONES.add(GRAVESTONE3_POLISHED_GRANITE);
//		GRAVESTONES.add(GRAVESTONE3_OBSIDIAN);
//		GRAVESTONES.add(GRAVESTONE3_SMOOTH_QUARTZ);
//		GRAVESTONES.add(SKULL_CROSSBONES);
//
//		GRAVESTONE_SPAWNERS.add(GRAVESTONE1_SPAWNER_STONE);
//		GRAVESTONE_SPAWNERS.add(GRAVESTONE2_SPAWNER_COBBLESTONE);
//		GRAVESTONE_SPAWNERS.add(GRAVESTONE3_SPAWNER_OBSIDIAN);
//	}

	/**
	 *
	 */
	public static void register(IEventBus bus) {
		// cycle through all block and create items
		BLOCKS.register(bus);
	}

	private static RotatedPillarBlock witherwoodLog(MapColor topColor, MapColor sideColor) {
		return new WitherwoodRotatedPillarBlock(Properties.of().mapColor((state) ->
				state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
				.instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
	}

	private static RotatedPillarBlock log(MapColor topColor, MapColor sideColor) {
		return new RotatedPillarBlock(Properties.of().mapColor((state) ->
				state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
				.instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
	}

	private static ButtonBlock woodenButton(BlockSetType blockSetType) {
		return new ButtonBlock(blockSetType, 30,
				Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY));
	}

//	private static RotatedPillarBlock witherwoodLog(MapColor mapColor, MapColor mapColor1) {
//		return new WitherwoodRotatedPillarBlock(Properties.of().mapColor((p_152624_) -> {
//			return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? mapColor : mapColor1;
//		}).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
//	}
//
//	private static RotatedPillarBlock log(MapColor mapColor, MapColor mapColor1) {
//		return new RotatedPillarBlock(Properties.of().mapColor((p_152624_) -> {
//			return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? mapColor : mapColor1;
//		}).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
//	}
//
//	private static ButtonBlock woodenButton(BlockSetType blockSetType, FeatureFlag... featureFlags) {
//		Properties blockbehaviour$properties = Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
//		if (featureFlags.length > 0) {
//			blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(featureFlags);
//		}
//		return new ButtonBlock(blockbehaviour$properties, blockSetType, 30, true);
//	}
}
