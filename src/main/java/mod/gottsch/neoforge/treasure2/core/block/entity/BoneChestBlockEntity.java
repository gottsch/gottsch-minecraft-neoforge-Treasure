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
import mod.gottsch.neoforge.treasure2.core.chest.ChestInventorySize;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class BoneChestBlockEntity extends AbstractTreasureChestBlockEntity {

    private static final double PROXIMITY_SQUARED = 36;

    private boolean locked;

    public float skullYPosition;
    public float prevSkullYPosition;
    public float lockAngle;
    public float prevLockAngle;

    public boolean isSkullOpen = false;
    public boolean isSkullClosed = true;
    public boolean isLockOpen = true;
    public boolean isLockClosed = false;
    public boolean isLidOpen = false;
    public boolean isLidClosed = false;

    public BoneChestBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.BONE_CHEST.get(), pos, state);
        setLocked(false);
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        try {
            tag.putBoolean("locked", isLocked());
        } catch (Exception e) {
            Treasure.LOGGER.error("error writing BoneChest properties to NBT:", e);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        try {
            if (tag.contains("locked")) {
                setLocked(tag.getBoolean("locked"));
            }
        } catch (Exception e) {
            Treasure.LOGGER.error("error reading BoneChest properties from NBT:", e);
        }
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable(LangUtil.screen("bone_chest.name"));
    }

    @Override
    public int getInventorySize() {
        return ChestInventorySize.STANDARD.getSize();
    }

    @Override
    public void tickClient() {
        this.prevLidAngle = this.lidAngle;
        this.prevSkullYPosition = this.skullYPosition;
        this.prevLockAngle = this.lockAngle;

        boolean isProximityMet = false;
        for (Player player : getLevel().players()) {
            double distanceSq = player.distanceToSqr(this.getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
            if (distanceSq < PROXIMITY_SQUARED) {
                isProximityMet = true;
                break;
            }
        }

        if (isProximityMet) {
            if (this.skullYPosition > -1.0F) {
                isSkullOpen = false;
                this.skullYPosition -= 0.1F;
                isSkullClosed = false;
                if (this.skullYPosition <= -1.0F) {
                    this.skullYPosition = -1.0F;
                    isSkullOpen = true;
                }
            } else {
                isSkullOpen = true;
            }
        } else {
            if (this.skullYPosition < 0.0F) {
                isSkullClosed = false;
                this.skullYPosition += 0.1F;
                isSkullOpen = false;
                if (this.skullYPosition >= 0.0F) {
                    this.skullYPosition = 0.0F;
                    isSkullClosed = true;
                }
            } else {
                isSkullClosed = true;
            }
        }

        if (!isLocked() && !isLockOpen) {
            if (this.lockAngle > 0F) {
                this.lockAngle -= 0.1F;
                isLockClosed = false;
                if (this.lockAngle <= 0F) {
                    this.lockAngle = 0F;
                    isLockOpen = true;
                }
            } else {
                isLockOpen = true;
            }
        } else if (isLocked() && !isLockClosed) {
            if (this.lockAngle < 1.0F) {
                this.lockAngle += 0.1F;
                isLockOpen = false;
                if (this.lockAngle > 1.0F) {
                    this.lockAngle = 1F;
                    isLockClosed = true;
                }
            } else {
                isLockClosed = true;
            }
        }

        if (this.openCount > 0) {
            if (this.lidAngle == 0.0F) {
                doChestOpenEffects(level, null, getBlockPos());
            }
            if (this.lidAngle < 1.0F) {
                isLidOpen = false;
                this.lidAngle += 0.1F;
                isLidClosed = false;
                if (this.lidAngle >= 1.0F) {
                    this.lidAngle = 1.0F;
                    isLidOpen = true;
                }
            } else {
                isLidOpen = true;
            }
        } else {
            float f2 = this.lidAngle;
            if (this.lidAngle > 0.0F) {
                isLidClosed = false;
                this.lidAngle -= 0.1F;
                isLidOpen = false;
                if (this.lidAngle <= 0.0F) {
                    this.lidAngle = 0.0F;
                    isLidClosed = true;
                }
            } else {
                isLidClosed = true;
            }
            if (this.lidAngle < 0.06F && f2 >= 0.06F) {
                doChestCloseEffects(level, null, getBlockPos());
            }
        }
    }
}
