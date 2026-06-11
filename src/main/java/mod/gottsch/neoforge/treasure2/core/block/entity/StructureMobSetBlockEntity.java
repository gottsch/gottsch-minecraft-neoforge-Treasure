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
import mod.gottsch.neoforge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Meant to be used in Structure NBTs only as a marker for where a ProximityMobSetSpawnBlock
 * will be placed. Stores the mob-set id and proximity used to configure the spawner.
 *
 * @author by Mark Gottschling on 8/14/2025
 */
public class StructureMobSetBlockEntity extends BlockEntity {
    public static final String MOBSET = "mobSet";
    public static final String MOBSETS = "mobSets";
    public static final String PROXIMITY = "proximity";

    private ResourceLocation mobSet;
    private int proximity;

    public StructureMobSetBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.STRUCTURE_MOB_SET.get(), pos, state);
    }

    public StructureMobSetBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        try {
            if (tag.contains(MOBSET)) {
                this.mobSet = ModUtil.asLocation(tag.getString(MOBSET));
            }
            if (tag.contains(PROXIMITY)) {
                this.proximity = tag.getInt(PROXIMITY);
            }
        } catch (Exception e) {
            Treasure.LOGGER.error("error reading StructureMobSetBlockEntity properties from tag:", e);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        Optional.ofNullable(getMobSet())
                .ifPresent(mobSet -> tag.putString(MOBSET, mobSet.toString()));
        tag.putInt(PROXIMITY, this.proximity);
    }

    public ResourceLocation getMobSet() {
        return mobSet;
    }

    public void setMobSet(ResourceLocation mobSet) {
        this.mobSet = mobSet;
    }

    public int getProximity() {
        return proximity;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }
}
