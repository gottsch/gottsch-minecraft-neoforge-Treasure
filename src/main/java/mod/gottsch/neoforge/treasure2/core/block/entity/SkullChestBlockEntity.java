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
import mod.gottsch.neoforge.treasure2.core.chest.ISkullChestType;
import mod.gottsch.neoforge.treasure2.core.chest.SkullChestType;
import mod.gottsch.neoforge.treasure2.core.inventory.SkullChestContainerMenu;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SkullChestBlockEntity extends AbstractTreasureChestBlockEntity {

    private ISkullChestType skullType;

    public SkullChestBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.SKULL_CHEST_BLOCK_ENTITY_TYPE.get(), pos, state);
        skullType = SkullChestType.SKULL;
    }

    public SkullChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public AbstractContainerMenu createChestContainerMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new SkullChestContainerMenu(windowId, this.worldPosition, playerInventory, playerEntity);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (skullType != null) {
            tag.putString("skullType", getSkullType().getName());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("skullType")) {
            setSkullType(SkullChestType.valueOf(tag.getString("skullType")));
        } else {
            setSkullType(SkullChestType.SKULL);
        }
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable(LangUtil.screen("skull_chest.name"));
    }

    @Override
    public int getInventorySize() {
        return ChestInventorySize.SKULL.getSize();
    }

    public ISkullChestType getSkullType() {
        return skullType;
    }

    public void setSkullType(ISkullChestType skullType) {
        this.skullType = skullType;
    }
}
