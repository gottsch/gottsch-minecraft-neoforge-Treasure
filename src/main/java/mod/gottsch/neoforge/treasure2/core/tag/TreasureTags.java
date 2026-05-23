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
package mod.gottsch.neoforge.treasure2.core.tag;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * @author Mark Gottschling on Nov 11, 2022
 */
public class TreasureTags {

	public static class Items {
		// keys
		public static final TagKey<Item> COMMON_KEY = mod(Treasure.MODID, "key/common");
		public static final TagKey<Item> UNCOMMON_KEY = mod(Treasure.MODID, "key/uncommon");
		public static final TagKey<Item> SCARCE_KEY = mod(Treasure.MODID, "key/scarce");
		public static final TagKey<Item> RARE_KEY = mod(Treasure.MODID, "key/rare");
		public static final TagKey<Item> EPIC_KEY = mod(Treasure.MODID, "key/epic");
		public static final TagKey<Item> LEGENDARY_KEYS = mod(Treasure.MODID, "key/legendary");
		public static final TagKey<Item> MYTHICAL_KEY = mod(Treasure.MODID, "key/mythical");
		public static final TagKey<Item> KEYS = mod(Treasure.MODID, "key/keys");

		// locks
		public static final TagKey<Item> COMMON_LOCKS = mod(Treasure.MODID, "lock/common");
		public static final TagKey<Item> UNCOMMON_LOCKS = mod(Treasure.MODID, "lock/uncommon");
		public static final TagKey<Item> SCARCE_LOCKS = mod(Treasure.MODID, "lock/scarce");
		public static final TagKey<Item> RARE_LOCKS = mod(Treasure.MODID, "lock/rare");
		public static final TagKey<Item> EPIC_LOCKS = mod(Treasure.MODID, "lock/epic");
		public static final TagKey<Item> LEGENDARY_LOCKS = mod(Treasure.MODID, "lock/legendary");
		public static final TagKey<Item> MYTHICAL_LOCKS = mod(Treasure.MODID, "lock/mythical");
		public static final TagKey<Item> LOCKS = mod(Treasure.MODID, "lock/locks");

		// wishables
		public static final TagKey<Item> COMMON_WISHABLE = mod(Treasure.MODID, "wishable/common");
		public static final TagKey<Item> UNCOMMON_WISHABLE = mod(Treasure.MODID, "wishable/uncommon");
		public static final TagKey<Item> SCARCE_WISHABLE = mod(Treasure.MODID, "wishable/scarce");
		public static final TagKey<Item> RARE_WISHABLE = mod(Treasure.MODID, "wishable/rare");
		public static final TagKey<Item> EPIC_WISHABLE = mod(Treasure.MODID, "wishable/epic");
		public static final TagKey<Item> LEGENDARY_WISHABLE = mod(Treasure.MODID, "wishable/legendary");
		public static final TagKey<Item> MYTHICAL_WISHABLE = mod(Treasure.MODID, "wishable/mythical");
		public static final TagKey<Item> WISHABLES = mod(Treasure.MODID, "wishable/wishables");

		// other
		public static final TagKey<Item> POUCH = mod(Treasure.MODID, "pouch");

		public static TagKey<Item> mod(String domain, String path) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath(domain, path));
		}
	}

	public static class Blocks {
		// chests by rarity
		public static final TagKey<Block> COMMON_CHESTS = mod(Treasure.MODID, "chests/rarity/core/common");
		public static final TagKey<Block> UNCOMMON_CHESTS = mod(Treasure.MODID, "chests/rarity/core/uncommon");
		public static final TagKey<Block> SCARCE_CHESTS = mod(Treasure.MODID, "chests/rarity/core/scarce");
		public static final TagKey<Block> RARE_CHESTS = mod(Treasure.MODID, "chests/rarity/core/rare");
		public static final TagKey<Block> EPIC_CHESTS = mod(Treasure.MODID, "chests/rarity/core/epic");
		public static final TagKey<Block> LEGENDARY_CHESTS = mod(Treasure.MODID, "chests/rarity/core/legendary");
		public static final TagKey<Block> MYTHICAL_CHESTS = mod(Treasure.MODID, "chests/rarity/core/mythical");

		// special chests
		public static final TagKey<Block> SKULL_CHESTS = mod(Treasure.MODID, "chests/rarity/special/skull");
		public static final TagKey<Block> GOLD_SKULL_CHESTS = mod(Treasure.MODID, "chests/rarity/special/gold_skull");
		public static final TagKey<Block> CRYSTAL_SKULL_CHESTS = mod(Treasure.MODID, "chests/rarity/special/crystal_skull");
		public static final TagKey<Block> WITHER_CHESTS = mod(Treasure.MODID, "chests/rarity/special/wither");
		public static final TagKey<Block> CAULDRON_CHESTS = mod(Treasure.MODID, "chests/rarity/special/cauldron");
		public static final TagKey<Block> BONE_CHESTS = mod(Treasure.MODID, "chests/rarity/special/bone");

		// wishing well candidates
		public static final TagKey<Block> WISHING_WELL_CANDIDATES = mod(Treasure.MODID, "wells/candidates");

		// gravestone base
		public static final TagKey<Block> GRAVESTONE_BASE = mod(Treasure.MODID, "structures/gravestone_base");

		public static TagKey<Block> mod(String domain, String path) {
			return BlockTags.create(ResourceLocation.fromNamespaceAndPath(domain, path));
		}
	}

	public static class Biomes {
		public static final TagKey<Biome> ALL_OVERWORLD = mod(Treasure.MODID, "all_overworld");
		public static final TagKey<Biome> TERRANEAN = mod(Treasure.MODID, "terranean");
		public static final TagKey<Biome> AQUATIC = mod(Treasure.MODID, "aquatic");

		// well biomes
		public static final TagKey<Biome> WELLS_GENERAL = mod(Treasure.MODID, "wells_general");
		public static final TagKey<Biome> WELLS_FOREST = mod(Treasure.MODID, "wells_forest");
		public static final TagKey<Biome> WELLS_JUNGLE = mod(Treasure.MODID, "wells_jungle");
		public static final TagKey<Biome> WELLS_DESERT = mod(Treasure.MODID, "wells_desert");

		// surface biomes
		public static final TagKey<Biome> TEMPERATE = mod(Treasure.MODID, "temperate");

		// integration tags
		public static final TagKey<Biome> BOP_OVERWORLD = mod(Treasure.MODID, "bop_overworld");
		public static final TagKey<Biome> BOP_FOREST = mod(Treasure.MODID, "bop_forest");
		public static final TagKey<Biome> BOP_JUNGLE = mod(Treasure.MODID, "bop_jungle");
		public static final TagKey<Biome> BOP_DESERT = mod(Treasure.MODID, "bop_desert");
		public static final TagKey<Biome> BOP_IS_DRY = mod(Treasure.MODID, "bop_is_dry");
		public static final TagKey<Biome> BWG_FOREST = mod(Treasure.MODID, "bwg_forest");
		public static final TagKey<Biome> BWG_JUNGLE = mod(Treasure.MODID, "bwg_jungle");
		public static final TagKey<Biome> BWG_DESERT = mod(Treasure.MODID, "bwg_desert");
		public static final TagKey<Biome> BWG_IS_LAND = mod(Treasure.MODID, "bwg_is_land");
		public static final TagKey<Biome> BWG_IS_DRY = mod(Treasure.MODID, "bwg_is_dry");
		public static final TagKey<Biome> BWG_IS_WET = mod(Treasure.MODID, "bwg_is_wet");
		public static final TagKey<Biome> BWG_IS_OCEAN = mod(Treasure.MODID, "bwg_is_ocean");

		// config/generator biome filters
		public static final TagKey<Biome> WITHER_BIOME_WHITELIST = mod(Treasure.MODID, "config/generators/rarities/wither/whitelist");
		public static final TagKey<Biome> WITHER_BIOME_BLACKLIST = mod(Treasure.MODID, "config/generators/rarities/wither/blacklist");

		public static final TagKey<Biome> TERRANEAN_RARE_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/rare");
		public static final TagKey<Biome> TERRANEAN_EPIC_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/epic");
		public static final TagKey<Biome> TERRANEAN_LEGANDARY_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/legendary");
		public static final TagKey<Biome> TERRANEAN_MYTHICAL_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/mythical");
		public static final TagKey<Biome> TERRANEAN_SKULL_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/skull");
		public static final TagKey<Biome> TERRANEAN_GOLD_SKULL_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/gold_skull");
		public static final TagKey<Biome> TERRANEAN_CRYSTAL_SKULL_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/crystal_skull");
		public static final TagKey<Biome> TERRANEAN_CAULDRON_BIOME_FILTER = mod(Treasure.MODID, "biome/filter/chests/rarity/terranean/cauldron");

		public static TagKey<Biome> mod(String domain, String path) {
			return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(domain, path));
		}
	}

	public static class Rarities {
		public static final TagKey<IRarity> ALL_RARITIES = mod("all_rarities");
		public static final TagKey<IRarity> SURFACE_CHEST_RARITIES = mod("structure/surface_chest/allowable_rarities");

		public static TagKey<IRarity> mod(String path) {
			return mod(Treasure.MODID, path);
		}

		public static TagKey<IRarity> mod(String domain, String path) {
			return TagKey.create(TreasureRarities.RARITIES_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(domain, path));
		}

		/**
		 * Checks whether a rarity holder is in the given tag.
		 * TODO: restore full implementation when RARITIES_REGISTRY_SUPPLIER is re-enabled in TreasureRarities.
		 */
		public static boolean isInTag(DeferredHolder<IRarity, IRarity> holder, TagKey<IRarity> tagKey) {
			return holder.getKey() != null && holder.is(tagKey);
		}
	}
}
