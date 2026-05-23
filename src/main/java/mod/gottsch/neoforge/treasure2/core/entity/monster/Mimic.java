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
package mod.gottsch.neoforge.treasure2.core.entity.monster;

import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.core.sound.TreasureSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Mark Gottschling on Jun 6, 2023
 */
public abstract class Mimic extends Monster {
    private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(Mimic.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_AMOUNT = SynchedEntityData.defineId(Mimic.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> TARGET = SynchedEntityData.defineId(Mimic.class, EntityDataSerializers.BOOLEAN);

    private float amount;
    public boolean isOpening = true;

    protected Mimic(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        if (!WorldInfo.isClientSide(level())) {
            if (this.getTarget() != null) {
                setHasTarget(true);
            } else {
                setHasTarget(false);
            }
        }
        super.aiStep();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("active", this.entityData.get(ACTIVE));
        tag.putFloat("amount", getAmount());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("active")) {
            this.entityData.set(ACTIVE, tag.getBoolean("active"));
        }
        if (tag.contains("amount")) {
            this.setAmount(tag.getFloat("amount"));
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOODEN_DOOR_OPEN;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHEST_CLOSE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TreasureSounds.AMBIENT_MIMIC.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.CHEST_LOCKED, 0.15F, 1.0F);
    }

    @Override
    public void playAmbientSound() {
        this.playSound(getAmbientSound(), 0.10F, 0.80F);
    }

    // TODO: restore setLootTable when loot key API (Optional<ResourceKey<LootTable>>) is ported
    // Forge ObfuscationReflectionHelper approach does not apply in NeoForge 1.21.1

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ACTIVE, false);
        builder.define(DATA_AMOUNT, 0.0F);
        builder.define(TARGET, false);
    }

    public boolean hasTarget() {
        return this.entityData.get(TARGET);
    }

    public void setHasTarget(boolean target) {
        this.entityData.set(TARGET, target);
    }

    public boolean isActive() {
        return this.entityData.get(ACTIVE);
    }

    public void setActive(boolean active) {
        this.entityData.set(ACTIVE, active);
    }

    public float getAmount() {
        return this.entityData.get(DATA_AMOUNT);
    }

    public void setAmount(float amount) {
        this.entityData.set(DATA_AMOUNT, amount);
    }
}
