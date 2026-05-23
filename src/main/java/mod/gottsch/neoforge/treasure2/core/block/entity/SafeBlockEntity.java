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

import mod.gottsch.neoforge.treasure2.core.chest.ChestInventorySize;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;

public class SafeBlockEntity extends AbstractTreasureChestBlockEntity {

    public float prevLatchPos;
    public float handleAngle;
    public float prevHandleAngle;

    public boolean isHandleOpen = false;
    public boolean isHandleClosed = true;
    public boolean isLidOpen = false;
    public boolean isLidClosed = false;

    public SafeBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.SAFE_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable(LangUtil.screen("safe.name"));
    }

    @Override
    public int getInventorySize() {
        return ChestInventorySize.STANDARD.getSize();
    }

    @Override
    public void tickClient() {
        this.prevLidAngle = this.lidAngle;
        this.prevHandleAngle = this.handleAngle;

        if (this.openCount > 0) {
            if (this.handleAngle > -1.0F) {
                isHandleOpen = false;
                this.handleAngle -= 0.1F;
                isHandleClosed = false;
                if (this.handleAngle <= -1.0F) {
                    this.handleAngle = -1.0F;
                    isHandleOpen = true;
                }
            } else {
                isHandleOpen = true;
            }

            if (isHandleOpen) {
                if (this.lidAngle == 0.0F) {
                    this.playSound(SoundEvents.CHEST_OPEN);
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
                this.playSound(SoundEvents.CHEST_CLOSE);
            }

            if (isLidClosed) {
                if (this.handleAngle < 0.0F) {
                    isHandleClosed = false;
                    this.handleAngle += 0.1F;
                    isHandleOpen = false;
                    if (this.handleAngle >= 0.0F) {
                        this.handleAngle = 0.0F;
                        isHandleClosed = true;
                    }
                } else {
                    isHandleClosed = true;
                }
            }
        }
    }
}
