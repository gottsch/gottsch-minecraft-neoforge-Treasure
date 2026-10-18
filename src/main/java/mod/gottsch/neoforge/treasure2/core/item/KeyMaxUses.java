/*
 * This file is part of Treasure2.
 * Copyright (c) 2026 Mark Gottschling (gottsch)
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
package mod.gottsch.neoforge.treasure2.core.item;

import com.google.common.collect.ImmutableMap;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Map;
import java.util.Optional;

/**
 * The configured max uses of each key and lock pick (the 1.20.1 {@code TreasureDataFixer.KEYS_CONFIG_MAP}).
 * It maps each item to its config value, not to the value itself, because the max uses are a SERVER config. That
 * is only loaded once a world is, and it is synced to joining clients, so reading it at use time gives the server,
 * a single player client and a dedicated server client the same number.
 *
 * @author Mark Gottschling on 10/17/2026
 */
public final class KeyMaxUses {

	private KeyMaxUses() {}

	/**
	 * Built on first use, which is always after item registration. The class initialiser is thread safe, so the
	 * client and server threads of a single player game can both reach it.
	 */
	private static final class Holder {
		private static final Map<Item, ModConfigSpec.ConfigValue<Integer>> MAX_USES = build();

		private static Map<Item, ModConfigSpec.ConfigValue<Integer>> build() {
			Config.ServerConfig.KeysAndLocks config = Config.SERVER.keysAndLocks;
			return ImmutableMap.<Item, ModConfigSpec.ConfigValue<Integer>>builder()
					.put(TreasureItems.WOOD_KEY.get(), config.woodKeyMaxUses)
					.put(TreasureItems.STONE_KEY.get(), config.stoneKeyMaxUses)
					.put(TreasureItems.LEAF_KEY.get(), config.leafKeyMaxUses)
					.put(TreasureItems.EMBER_KEY.get(), config.emberKeyMaxUses)
					.put(TreasureItems.LIGHTNING_KEY.get(), config.lightningKeyMaxUses)
					.put(TreasureItems.IRON_KEY.get(), config.ironKeyMaxUses)
					.put(TreasureItems.GOLD_KEY.get(), config.goldKeyMaxUses)
					.put(TreasureItems.METALLURGISTS_KEY.get(), config.metallurgistsKeyMaxUses)
					.put(TreasureItems.DIAMOND_KEY.get(), config.diamondKeyMaxUses)
					.put(TreasureItems.EMERALD_KEY.get(), config.emeraldKeyMaxUses)
					.put(TreasureItems.TOPAZ_KEY.get(), config.topazKeyMaxUses)
					.put(TreasureItems.ONYX_KEY.get(), config.onyxKeyMaxUses)
					.put(TreasureItems.RUBY_KEY.get(), config.rubyKeyMaxUses)
					.put(TreasureItems.SAPPHIRE_KEY.get(), config.sapphireKeyMaxUses)
					.put(TreasureItems.JEWELLED_KEY.get(), config.jewelledKeyMaxUses)
					.put(TreasureItems.SPIDER_KEY.get(), config.spiderKeyMaxUses)
					.put(TreasureItems.SKELETON_KEY.get(), config.skeletonKeyMaxUses)
					.put(TreasureItems.WITHER_KEY.get(), config.witherKeyMaxUses)
					.put(TreasureItems.PILFERERS_LOCK_PICK.get(), config.pilferersLockPickMaxUses)
					.put(TreasureItems.THIEFS_LOCK_PICK.get(), config.thiefsLockPickMaxUses)
					.build();
		}
	}

	/**
	 * @return the configured max uses of the item, or empty if it has no config value (Bone Key, One Key) or no
	 * world is loaded yet
	 */
	public static Optional<Integer> get(Item item) {
		if (!Config.SERVER_SPEC.isLoaded()) {
			return Optional.empty();
		}
		ModConfigSpec.ConfigValue<Integer> value = Holder.MAX_USES.get(item);
		return value == null ? Optional.empty() : Optional.of(value.get());
	}
}
