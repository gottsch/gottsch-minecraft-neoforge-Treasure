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
package mod.gottsch.neoforge.treasure2.core.block.entity;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.TreasureBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 *
 * @author Mark Gottschling on Nov 14, 2022
 *
 */
public class TreasureBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Treasure.MODID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodChestBlockEntity>> WOOD_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("wood_chest_block_entity",
					() -> BlockEntityType.Builder.of(WoodChestBlockEntity::new, TreasureBlocks.WOOD_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrateChestBlockEntity>> CRATE_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("crate_chest_block_entity",
					() -> BlockEntityType.Builder.of(CrateChestBlockEntity::new, TreasureBlocks.CRATE_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoldyCrateChestBlockEntity>> MOLDY_CRATE_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("moldy_crate_chest_block_entity",
					() -> BlockEntityType.Builder.of(MoldyCrateChestBlockEntity::new, TreasureBlocks.MOLDY_CRATE_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronboundChestBlockEntity>> IRONBOUND_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("ironbound_chest_block_entity",
					() -> BlockEntityType.Builder.of(IronboundChestBlockEntity::new, TreasureBlocks.IRONBOUND_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PirateChestBlockEntity>> PIRATE_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("pirate_chest_block_entity",
					() -> BlockEntityType.Builder.of(PirateChestBlockEntity::new, TreasureBlocks.PIRATE_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SafeBlockEntity>> SAFE_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("safe_block_entity",
					() -> BlockEntityType.Builder.of(SafeBlockEntity::new, TreasureBlocks.SAFE.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronStrongboxBlockEntity>> IRON_STRONGBOX_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("iron_strongbox_block_entity",
					() -> BlockEntityType.Builder.of(IronStrongboxBlockEntity::new, TreasureBlocks.IRON_STRONGBOX.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldStrongboxBlockEntity>> GOLD_STRONGBOX_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("gold_strongbox_block_entity",
					() -> BlockEntityType.Builder.of(GoldStrongboxBlockEntity::new, TreasureBlocks.GOLD_STRONGBOX.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DreadPirateChestBlockEntity>> DREAD_PIRATE_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("dread_pirate_chest_block_entity",
					() -> BlockEntityType.Builder.of(DreadPirateChestBlockEntity::new, TreasureBlocks.DREAD_PIRATE_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CompressorChestBlockEntity>> COMPRESSOR_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("compressor_chest_block_entity",
					() -> BlockEntityType.Builder.of(CompressorChestBlockEntity::new, TreasureBlocks.COMPRESSOR_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkullChestBlockEntity>> SKULL_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("skull_chest_block_entity",
					() -> BlockEntityType.Builder.of(SkullChestBlockEntity::new, TreasureBlocks.SKULL_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldSkullChestBlockEntity>> GOLD_SKULL_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("gold_skull_chest_block_entity",
					() -> BlockEntityType.Builder.of(GoldSkullChestBlockEntity::new, TreasureBlocks.GOLD_SKULL_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalSkullChestBlockEntity>> CRYSTAL_SKULL_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("crystal_skull_chest_block_entity",
					() -> BlockEntityType.Builder.of(CrystalSkullChestBlockEntity::new, TreasureBlocks.CRYSTAL_SKULL_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CauldronChestBlockEntity>> CAULDRON_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("cauldron_chest_block_entity",
					() -> BlockEntityType.Builder.of(CauldronChestBlockEntity::new, TreasureBlocks.CAULDRON_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiderChestBlockEntity>> SPIDER_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("spider_chest_block_entity",
					() -> BlockEntityType.Builder.of(SpiderChestBlockEntity::new, TreasureBlocks.SPIDER_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VikingChestBlockEntity>> VIKING_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("viking_chest_block_entity",
					() -> BlockEntityType.Builder.of(VikingChestBlockEntity::new, TreasureBlocks.VIKING_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CardboardBoxBlockEntity>> CARDBOARD_BOX_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("cardboard_box_block_entity",
					() -> BlockEntityType.Builder.of(CardboardBoxBlockEntity::new, TreasureBlocks.CARDBOARD_BOX.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MilkCrateBlockEntity>> MILK_CRATE_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("milk_crate_block_entity",
					() -> BlockEntityType.Builder.of(MilkCrateBlockEntity::new, TreasureBlocks.MILK_CRATE.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BarrelChestBlockEntity>> BARREL_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("barrel_chest_block_entity",
					() -> BlockEntityType.Builder.of(BarrelChestBlockEntity::new, TreasureBlocks.BARREL_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VanillaChestBlockEntity>> VANILLA_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("vanilla_chest_block_entity",
					() -> BlockEntityType.Builder.of(VanillaChestBlockEntity::new, TreasureBlocks.VANILLA_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WitherChestBlockEntity>> WITHER_CHEST_BLOCK_ENTITY_TYPE =
			BLOCK_ENTITIES.register("wither_chest_block_entity",
					() -> BlockEntityType.Builder.of(WitherChestBlockEntity::new, TreasureBlocks.WITHER_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BoneChestBlockEntity>> BONE_CHEST =
			BLOCK_ENTITIES.register("bone_chest",
					() -> BlockEntityType.Builder.of(BoneChestBlockEntity::new, TreasureBlocks.BONE_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CelestialChestBlockEntity>> CELESTIAL_CHEST =
			BLOCK_ENTITIES.register("celestial_chest",
					() -> BlockEntityType.Builder.of(CelestialChestBlockEntity::new, TreasureBlocks.CELESTIAL_CHEST.get()).build(null));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfernalChestBlockEntity>> INFERNAL_CHEST =
			BLOCK_ENTITIES.register("infernal_chest",
					() -> BlockEntityType.Builder.of(InfernalChestBlockEntity::new, TreasureBlocks.INFERNAL_CHEST.get()).build(null));

//	public static final RegistryObject<BlockEntityType<TreasureProximitySpawnerBlockEntity>> TREASURE_PROXIMITY_SPAWNER_ENTITY_TYPE =
//			Registration.BLOCK_ENTITIES.register("treasure_proximity_spawner",
//			() -> BlockEntityType.Builder.of(TreasureProximitySpawnerBlockEntity::new,
//					TreasureBlocks.PROXIMITY_SPAWNER.get()
//				).build(null));
//
//	public static final RegistryObject<BlockEntityType<TreasureProximityMultiSpawnerBlockEntity>> TREASURE_PROXIMITY_MULTI_SPAWNER_ENTITY_TYPE =
//			Registration.BLOCK_ENTITIES.register("treasure_proximity_multi_spawner",
//					() -> BlockEntityType.Builder.of(TreasureProximityMultiSpawnerBlockEntity::new,
//							TreasureBlocks.PROXIMITY_MULTI_SPAWNER.get()
//					).build(null));
//
//	public static final RegistryObject<BlockEntityType<ProximityMobSetSpawnerBlockEntity>> PROXIMITY_MOBSET_SPAWNER_ENTITY_TYPE =
//			Registration.BLOCK_ENTITIES.register("proximity_mobset_spawner",
//					() -> BlockEntityType.Builder.of(ProximityMobSetSpawnerBlockEntity::new,
//							TreasureBlocks.PROXIMITY_MOBSET_SPAWNER.get()
//					).build(null));
//
//	public static final RegistryObject<BlockEntityType<GravestoneProximitySpawnerBlockEntity>> GRAVESTONE_PROXIMITY_SPAWNER_ENTITY_TYPE =
//			Registration.BLOCK_ENTITIES.register("gravestone_proximity_spawner",
//			() -> BlockEntityType.Builder.of(GravestoneProximitySpawnerBlockEntity::new,
//					TreasureBlocks.GRAVESTONE1_SPAWNER_STONE.get(),
//					TreasureBlocks.GRAVESTONE2_SPAWNER_COBBLESTONE.get(),
//					TreasureBlocks.GRAVESTONE3_SPAWNER_OBSIDIAN.get()
//				).build(null));
//
//	// signs
//	public static final RegistryObject<BlockEntityType<TreasureSignBlockEntity>> TREASURE_SIGN =
//			Registration.BLOCK_ENTITIES.register("treasure_sign",
//					() -> BlockEntityType.Builder.of(TreasureSignBlockEntity::new,
//					TreasureBlocks.WITHERWOOD_SIGN.get(), TreasureBlocks.WITHERWOOD_WALL_SIGN.get()).build(null));
//
//	public static final RegistryObject<BlockEntityType<TreasureHangingSignBlockEntity>> TREASURE_HANGING_SIGN =
//			Registration.BLOCK_ENTITIES.register("treasure_hanging_sign",
//					() -> BlockEntityType.Builder.of(TreasureHangingSignBlockEntity::new,
//							TreasureBlocks.WITHERWOOD_HANGING_SIGN.get(), TreasureBlocks.WITHERWOOD_WALL_HANGING_SIGN.get()).build(null));
//
//	public static final RegistryObject<BlockEntityType<StructureMobSetBlockEntity>> STRUCTURE_MOB_SET =
//			Registration.BLOCK_ENTITIES.register("structure_mob_set",
//					() -> BlockEntityType.Builder.of(StructureMobSetBlockEntity::new,
//							TreasureBlocks.STRUCTURE_MOB_SET.get()
//					).build(null));
//
//	public static final RegistryObject<BlockEntityType<StructureNeighborDependentStateMarkerBlockEntity>> STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER =
//			Registration.BLOCK_ENTITIES.register("structure_neighbor_dependent_state_marker",
//					() -> BlockEntityType.Builder.of(StructureNeighborDependentStateMarkerBlockEntity::new,
//							TreasureBlocks.STRUCTURE_NEIGHBOR_DEPENDENT_STATE_MARKER.get()
//					).build(null));

    public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}
}
