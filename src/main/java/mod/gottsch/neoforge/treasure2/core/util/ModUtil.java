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
package mod.gottsch.neoforge.treasure2.core.util;

import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neo.gottschcore.spatial.ICoords;
import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Minimal NeoForge 1.21.1 port of the Forge {@code ModUtil} helpers needed by the spawner
 * block entities. Reflection-based stack-size/durability helpers and datapack-walking helpers
 * from the Forge version are intentionally omitted until a phase actually needs them.
 *
 * @author Mark Gottschling on Jul 22, 2021
 */
public class ModUtil {

	/**
	 * Resolves a string to a ResourceLocation, defaulting to the Treasure2 namespace when the
	 * string has no explicit domain.
	 */
	public static ResourceLocation asLocation(String name) {
		return hasDomain(name)
				? ResourceLocation.parse(name)
				: ResourceLocation.fromNamespaceAndPath(Treasure.MODID, name);
	}

	public static boolean hasDomain(String name) {
		return name.indexOf(":") >= 0;
	}

	/**
	 * @return the registry id of the given block.
	 */
	public static ResourceLocation getName(net.minecraft.world.level.block.Block block) {
		return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block);
	}

	/**
	 * @return the registry id of the given item.
	 */
	public static ResourceLocation getName(net.minecraft.world.item.Item item) {
		return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);
	}

	/**
	 * @author Mark Gottschling on Jul 25, 2021
	 */
	public static class SpawnEntityHelper {

		/**
		 * Attempts (up to 20 tries) to place an already-created mob at a valid empty spawn position
		 * near the supplied coords.
		 */
		public static Entity spawn(ServerLevel level, RandomSource random, EntityType<?> entityType, Entity mob, ICoords coords) {
			for (int i = 0; i < 20; i++) { // 20 tries
				int spawnX = coords.getX() + Mth.nextInt(random, 1, 2) * Mth.nextInt(random, -1, 1);
				int spawnY = coords.getY() + Mth.nextInt(random, 1, 2) * Mth.nextInt(random, -1, 1);
				int spawnZ = coords.getZ() + Mth.nextInt(random, 1, 2) * Mth.nextInt(random, -1, 1);
				ICoords spawnCoords = new Coords(spawnX, spawnY, spawnZ);

				if (!WorldInfo.isClientSide(level)) {
					BlockState state = level.getBlockState(spawnCoords.toPos());
					if (NaturalSpawner.isValidEmptySpawnBlock(level, spawnCoords.toPos(), state, state.getFluidState(), entityType)) {
						mob.setPos((double) spawnX, (double) spawnY, (double) spawnZ);
						level.addFreshEntityWithPassengers(mob);
						break;
					}
				}
			}
			return mob;
		}
	}
}
