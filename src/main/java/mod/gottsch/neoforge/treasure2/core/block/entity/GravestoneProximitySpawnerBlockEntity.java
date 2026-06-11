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

import mod.gottsch.neo.gottschcore.block.entity.ProximitySpawnerBlockEntity;
import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neo.gottschcore.size.DoubleRange;
import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neo.gottschcore.spatial.ICoords;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.entity.TreasureEntities;
import mod.gottsch.neoforge.treasure2.core.entity.monster.BoundSoul;
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * @author Mark Gottschling on Jan 17, 2019
 *
 */
public class GravestoneProximitySpawnerBlockEntity extends ProximitySpawnerBlockEntity {
	private boolean hasEntity;

	/**
	 *
	 * @param pos
	 * @param state
	 */
	public GravestoneProximitySpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TreasureBlockEntities.GRAVESTONE_PROXIMITY_SPAWNER_ENTITY_TYPE.get(), pos, state);
		setProximity(10D);
		setMobName(ResourceLocation.fromNamespaceAndPath(Treasure.MODID, "bound_soul"));
		setMobNum(new DoubleRange(1, 1));
		setHasEntity(true);
	}

	/**
	 * NOTE this was not working when calling the super.update()
	 */
	public void tickServer() {
		boolean hasEntity = hasEntity();
		if (hasEntity && Config.SERVER.markers.enableSpawner.get()) {

			// this is copied from the abstract
			if (this.level.isClientSide()) {
				return;
			}

			boolean isTriggered = false;
			double proximitySq = getProximity() * getProximity();
			if (proximitySq < 1) {
				proximitySq = 1;
			}

			// for each player
			for (Player player : getLevel().players()) {
				// get the distance
				double distanceSq = player.distanceToSqr(this.getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
				if (!isTriggered && !this.isDead() && (distanceSq < proximitySq)) {
					Treasure.LOGGER.debug("proximity @ -> {} was met.", new Coords(getBlockPos()).toShortString());
					isTriggered = true;
					// execute action
					Treasure.LOGGER.debug("proximity pos -> {}", this.getBlockPos());
					execute(level, level.getRandom(), new Coords(this.getBlockPos()), new Coords(player.blockPosition()));
					// NOTE: does not self-destruct that is up to the execute action to perform
				}
				if (this.isDead()) {
					break;
				}
			}
		}
	}

	/**
	 *
	 */
	@Override
	public void execute(Level world, RandomSource random, ICoords blockCoords, ICoords playerCoords) {
		Treasure.LOGGER.debug("executing...");
		Treasure.LOGGER.debug("blockCoords -> {}, playerCoords -> {}", blockCoords.toShortString(), playerCoords.toShortString());
		int mobCount = RandomHelper.randomInt(random, getMobNum().getMinInt(), getMobNum().getMaxInt());

		for (int i = 0; i < mobCount; i++) {
			// create the mob entity
			BoundSoul mob = (BoundSoul) TreasureEntities.BOUND_SOUL_ENTITY_TYPE.get().create(level);

			if (mob != null) {
				// set specific properties of the mob entity
				mob.restrictTo(blockCoords.toPos(), 16);
				Treasure.LOGGER.debug("attempted location for bound soul spawn -> {}", blockCoords.toShortString());
				ModUtil.SpawnEntityHelper.spawn((ServerLevel) world, random, TreasureEntities.BOUND_SOUL_ENTITY_TYPE.get(), mob, blockCoords);
			}
		}
		// self destruct
		selfDestruct();
	}

	/**
	 *
	 */
	private void selfDestruct() {
		Treasure.LOGGER.debug("self destructing.");
		this.setDead(true);
		this.setHasEntity(false);
	}

	@Override
	public boolean isDead() {
		return super.isDead() || !hasEntity();
	}

	/**
	 *
	 */
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		try {
			if (tag.contains("hasEntity")) {
				this.hasEntity = tag.getBoolean("hasEntity");
			}
		} catch (Exception e) {
			Treasure.LOGGER.error("error reading GravestoneProximityBlockEntity properties from tag:", e);
		}
	}

	/**
	 *
	 */
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("hasEntity", hasEntity());
	}

	/**
	 * Sync client and server states
	 */
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveCustomOnly(registries);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public boolean hasEntity() {
		return this.hasEntity;
	}

	public void setHasEntity(boolean hasEntity) {
		this.hasEntity = hasEntity;
	}
}
