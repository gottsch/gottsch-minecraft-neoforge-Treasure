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
package mod.gottsch.neoforge.treasure2.core.item;

import mod.gottsch.neo.gottschcore.util.ModUtil;
import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlockProxy;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.ITreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.component.TreasureComponents;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.inventory.KeyRingContainerMenu;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;

/**
 * @author Mark Gottschling on Mar 9, 2018
 *
 */
public class KeyRingItem extends Item implements MenuProvider {

	public static final int INVENTORY_SIZE = 14;

	/**
	 *
	 * @param properties
	 */
	public KeyRingItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	/**
	 *
	 */
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		tooltip.add(Component.translatable(LangUtil.tooltip("key_lock.key_ring")).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		// exit if on the client
		if (WorldInfo.isClientSide(context.getLevel())) {
			return InteractionResult.FAIL;
		}

		BlockPos chestPos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(chestPos);
		Block block = context.getLevel().getBlockState(chestPos).getBlock();

		// test if the block is a chest proxy (ex. multiple block chests like Wither Chest)
		if (block instanceof ITreasureChestBlockProxy) {
			chestPos = ((ITreasureChestBlockProxy)block).getChestPos(chestPos);
			state = context.getLevel().getBlockState(chestPos);
			block = context.getLevel().getBlockState(chestPos).getBlock();
		}

		if (block instanceof AbstractTreasureChestBlock) {
			// get the tile entity
			BlockEntity blockEntity = context.getLevel().getBlockEntity(chestPos);
			if (blockEntity == null || !(blockEntity instanceof ITreasureChestBlockEntity)) {
				Treasure.LOGGER.warn("null or incorrect blockEntity");
				return InteractionResult.FAIL;
			}
			ITreasureChestBlockEntity chestBlockEntity = (ITreasureChestBlockEntity)blockEntity;

			// determine if chest is locked
			if (!chestBlockEntity.hasLocks()) {
				return InteractionResult.SUCCESS;
			}

			try {
				ItemStack heldItem = context.getPlayer().getItemInHand(context.getHand());
				IItemHandler handler = new ComponentItemHandler(heldItem, TreasureComponents.KEY_RING_INVENTORY.get(), INVENTORY_SIZE);

				// cycle through all keys in key ring until one is able to fit lock and use it to unlock the lock.
				for (int i = 0; i < INVENTORY_SIZE; i++) {
					ItemStack keyStack = handler.getStackInSlot(i);
					if (!keyStack.isEmpty() && keyStack.getItem() instanceof KeyItem)  {
						KeyItem key = (KeyItem) keyStack.getItem();
						Treasure.LOGGER.debug("using key from keyring -> {}", ModUtil.getName(key));
						boolean breakKey = true;
						boolean fitsLock = false;
						LockState lockState = null;
						boolean isKeyBroken = false;

						// check if this key is one that opens a lock (only first lock that key fits is unlocked).
						lockState = key.fitsFirstLock(context.getLevel(), chestBlockEntity.getLockStates());
						if (lockState != null) {
							fitsLock = true;
						}
						Treasure.LOGGER.debug("key fits lock -> {}", lockState);

						if (fitsLock) {
							if (key.unlock(context.getLevel(), lockState.getLock())) {
								// unlock the lock
								doUnlock(context, (AbstractTreasureChestBlockEntity)chestBlockEntity, key, lockState);

								if (!state.getValue(AbstractTreasureChestBlock.DISCOVERED)) {
									chestBlockEntity = ((AbstractTreasureChestBlock) block).discovered((AbstractTreasureChestBlockEntity) chestBlockEntity, state, context.getLevel(), chestPos, context.getPlayer());
								}

								// update the client
								((AbstractTreasureChestBlockEntity) chestBlockEntity).updateAttachmentAndSync();

								// don't break the key
								breakKey = false;
							}

							if (breakKey) {
								if (!context.getPlayer().isCreative() && (key.isBreakable() || key.anyLockBreaksKey(chestBlockEntity.getLockStates(), key)) && Config.SERVER.keysAndLocks.enableKeyBreaks.get()) {
									// this damage block is considering if a key has been merged with another key.
									// it is only 'breaking' 1 key's worth of damage
									// ex k1(1/10d) + k2(0/10d) = k3(1/20d), only apply 9 damage
									// so k3 = (10/20d) ie 1 key's worth damage was applied.
									int durability = key.getDurability();
									int damage = keyStack.getDamageValue() + (durability - (keyStack.getDamageValue() % durability));
									keyStack.setDamageValue(damage);

									// write the modified key stack back to the component-backed handler
									handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
									handler.insertItem(i, keyStack, false);

									key.doKeyBreakEffects(context.getLevel(), context.getPlayer(), chestPos);

									// the key is broken, do not attempt to damage it.
									isKeyBroken = true;
								}
								else if (!fitsLock) {
									key.doKeyNotFitEffects(context.getLevel(), context.getPlayer(), chestPos);
								}
								else {
									key.doKeyUnableToUnlockEffects(context.getLevel(), context.getPlayer(), chestPos);
								}
							}
							if (!context.getPlayer().isCreative() && key.isDamageable(keyStack) && !isKeyBroken) {
								keyStack.setDamageValue(keyStack.getDamageValue() + 1);
								// write back to the component-backed handler
								handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
								handler.insertItem(i, keyStack, false);
							}
							// key unlocked a lock, end loop (ie only unlock 1 lock at a time)
							break;
						}
					}
				}
			} catch (Exception e) {
				Treasure.LOGGER.error("error: ", e);
			}
		}
		else {
			// open gui
			return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
		}

		// this should prevent the onItemRightClick from happening./
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		// exit if on the client
		if (WorldInfo.isClientSide(level)) {
			return InteractionResultHolder.fail(player.getItemInHand(hand));
		}

		ItemStack stack = player.getItemInHand(hand);

		// mark the key ring as open
		stack.set(TreasureComponents.KEY_RING_OPEN.get(), Unit.INSTANCE);

		// open the key ring
		player.openMenu(this, buf -> ItemStack.STREAM_CODEC.encode(buf, stack));

		return InteractionResultHolder.pass(stack);
	}

	/**
	 *
	 * @param context
	 * @param chestBlockEntity
	 * @param lockState
	 */
	public void doUnlock(UseOnContext context, AbstractTreasureChestBlockEntity chestBlockEntity, KeyItem key, LockState lockState) {
		key.doUnlock(context, chestBlockEntity, lockState);
	}

	/**
	 *
	 */
	@Override
	public boolean onDroppedByPlayer(ItemStack stack, Player player) {
		// NOTE only works on 'Q' press, not mouse drag and drop
		if (stack.has(TreasureComponents.KEY_RING_OPEN.get())) {
			return false;
		}
		return super.onDroppedByPlayer(stack, player);
	}

	/**
	 *
	 */
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
		// get the held item
		ItemStack keyRingItem = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (keyRingItem == null || !(keyRingItem.getItem() instanceof KeyRingItem)) {
			keyRingItem = player.getItemInHand(InteractionHand.OFF_HAND);
			if (keyRingItem == null || !(keyRingItem.getItem() instanceof KeyRingItem))
				return null;
		}

		// create inventory from the component-backed handler
		IItemHandler inventory = new ComponentItemHandler(keyRingItem, TreasureComponents.KEY_RING_INVENTORY.get(), INVENTORY_SIZE);

		// open the container
		return new KeyRingContainerMenu(windowId, playerInventory, inventory);
	}

	@Override
	public Component getDisplayName() {
		return getDescription();
	}
}
