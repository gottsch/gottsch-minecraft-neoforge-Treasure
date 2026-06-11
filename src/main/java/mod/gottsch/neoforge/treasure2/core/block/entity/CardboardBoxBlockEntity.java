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

public class CardboardBoxBlockEntity extends AbstractTreasureChestBlockEntity {

    public float prevInnerLidPos;
    public float innerLidAngle;
    public float prevInnerLidAngle;

    public boolean isLidOpen = false;
    public boolean isLidClosed = true;
    public boolean isInnerLidOpen = false;
    public boolean isInnerLidClosed = true;

    public CardboardBoxBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.CARDBOARD_BOX_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable(LangUtil.screen("cardboard_box.name"));
    }

    @Override
    public int getInventorySize() {
        return ChestInventorySize.STANDARD.getSize();
    }

    @Override
    public void tickClient() {
        this.prevLidAngle = this.lidAngle;
        this.prevInnerLidAngle = this.innerLidAngle;

        if (this.openCount > 0) {
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

            if (isLidOpen) {
                if (isInnerLidClosed) {
                    doChestOpenEffects(level, null, getBlockPos());
                }
                if (this.innerLidAngle < 1.0F) {
                    isInnerLidOpen = false;
                    this.innerLidAngle += 0.1F;
                    isInnerLidClosed = false;
                    if (this.innerLidAngle >= 1.0F) {
                        this.innerLidAngle = 1.0F;
                        isInnerLidOpen = true;
                    }
                } else {
                    isInnerLidOpen = true;
                }
            }
        } else {
            if (this.innerLidAngle > 0.0F) {
                isInnerLidClosed = false;
                this.innerLidAngle -= 0.1F;
                isInnerLidOpen = false;
                if (this.innerLidAngle <= 0.0F) {
                    this.innerLidAngle = 0.0F;
                    isInnerLidClosed = true;
                }
            } else {
                isInnerLidClosed = true;
            }

            if (isInnerLidClosed) {
                if (isLidOpen) {
                    doChestCloseEffects(level, null, getBlockPos());
                }
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
            }
        }
    }
}
