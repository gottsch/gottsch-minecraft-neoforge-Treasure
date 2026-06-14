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

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

/**
 * @author Mark Gottschling
 */
public class WitherwoodGolem extends Monster {
    private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(WitherwoodGolem.class, EntityDataSerializers.BLOCK_POS);
    private static final String HOME_POS_KEY = "home_pos";

    public WitherwoodGolem(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.2D));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 75.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HOME_POS, BlockPos.ZERO);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(HOME_POS_KEY + "X", this.getHomePos().getX());
        compound.putInt(HOME_POS_KEY + "Y", this.getHomePos().getY());
        compound.putInt(HOME_POS_KEY + "Z", this.getHomePos().getZ());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        int i = compound.getInt(HOME_POS_KEY + "X");
        int j = compound.getInt(HOME_POS_KEY + "Y");
        int k = compound.getInt(HOME_POS_KEY + "Z");
        this.setHomePos(new BlockPos(i, j, k));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void checkDespawn() {
        // does NOT despawn
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0;
    }

    private float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        float attackDamage = this.getAttackDamage();
        float calculatedAttackDamage = (int) attackDamage > 0 ? attackDamage / 2.0F + (float) this.random.nextInt((int) attackDamage) : attackDamage;
        DamageSource damageSource = this.damageSources().mobAttack(this);
        boolean isTargetEntityHurt = entity.hurt(damageSource, calculatedAttackDamage);
        if (isTargetEntityHurt) {
            double targetEntityKnockbackResistence;
            if (entity instanceof LivingEntity livingentity) {
                targetEntityKnockbackResistence = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                targetEntityKnockbackResistence = 0.0D;
            }
            double d1 = Math.max(0.0D, 1.0D - targetEntityKnockbackResistence);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, (double) 0.4F * d1, 0.0D));
            // apply post-attack enchantment effects (e.g. the target's Thorns) — the 1.21 replacement
            // for Forge's LivingEntity#doEnchantDamageEffects; mirrors vanilla Mob#doHurtTarget
            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
            }
        }
        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return isTargetEntityHurt;
    }

    @Override
    public void handleEntityEvent(byte eventFlag) {
        if (eventFlag == 4) {
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(eventFlag);
        }
    }

    public boolean hasHome() {
        return getHomePos() != null && !getHomePos().equals(BlockPos.ZERO);
    }

    public void setHomePos(BlockPos pos) {
        Treasure.LOGGER.debug("setting wither golem home pos -> {}", pos.toString());
        this.entityData.set(HOME_POS, pos);
    }

    private BlockPos getHomePos() {
        return this.entityData.get(HOME_POS);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        BlockPos pos = this.blockPosition();
        BlockPos belowPos = pos.below();
        BlockState blockstate = levelReader.getBlockState(belowPos);
        if (!blockstate.entityCanStandOn(levelReader, belowPos, this)) {
            return false;
        } else {
            for (int i = 1; i < 3; ++i) {
                BlockPos blockpos2 = pos.above(i);
                BlockState blockstate1 = levelReader.getBlockState(blockpos2);
                if (!NaturalSpawner.isValidEmptySpawnBlock(levelReader, blockpos2, blockstate1, blockstate1.getFluidState(), EntityType.IRON_GOLEM)) {
                    return false;
                }
            }
            return NaturalSpawner.isValidEmptySpawnBlock(levelReader, pos, levelReader.getBlockState(pos), Fluids.EMPTY.defaultFluidState(), EntityType.IRON_GOLEM)
                    && levelReader.isUnobstructed(this);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }
}
