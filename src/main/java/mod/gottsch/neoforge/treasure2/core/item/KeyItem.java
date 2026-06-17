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
import mod.gottsch.neo.gottschcore.random.RandomHelper;
import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.ITreasureChestBlockProxy;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.ITreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.item.effects.IKeyEffects;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.RarityTagAssociationRegistry;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * 
 * @author Mark Gottschling on Jan 11, 2018
 *
 */
public class KeyItem extends Item implements IKeyEffects {
	public static final int DEFAULT_MAX_USES = 25;

	/*
	 * The category that the key belongs to
	 */
	private KeyLockCategory category;

	/*
	 * Is the key craftable
	 */
	private boolean craftable;

	/*
	 * Can the key break attempting to unlock a lock
	 */
	private boolean breakable;

	/*
	 * Can the key take damage and lose durability
	 */
//	private boolean damageable;

	/*
	 * The probability of a successful unlocking
	 */
	private double successProbability;

	/*
	 * A list of predicates that determine if a key fits into a lock.
	 */
	private List<BiPredicate<Level, LockItem >> fitsLock;
	
	/*
	 * A list of predicates that determine if a key will break a lock.
	 */
	private List<Predicate<LockItem >> breaksLock;

	/*
	 * The default (dynamic) durability of the item.
	 * This value should be changed during LevelEvent.load event to equal
	 * the value specified in the config.
	 */
	private int durability = Integer.MIN_VALUE;

	/**
	 * 
	 * @param properties
	 */
	public KeyItem(Properties properties) {
		this(properties, DEFAULT_MAX_USES);
	}

	/**
	 *
	 * @param properties
	 * @param durability
	 */
	public KeyItem(Properties properties, int durability) {
		super(properties.durability(durability));//.defaultDurability(DEFAULT_MAX_USES));
		setCategory(KeyLockCategory.ELEMENTAL);
		setBreakable(true);
		setCraftable(false);
		setSuccessProbability(90D);
		setDurability(durability);
	}

	/**
	 * Format:
	 * 		Item Name (vanilla minecraft)
	 * 		Rarity: [...]  [color = Gold] 
	 * 		Category:  [...] [color = Gold]
	 * 		Uses Remaining: [n]
	 * 		Max Uses: [n] [color = Gold]
	 * 		Breakable: [Yes | No] [color = Dark Red | Green]
	 * 		Craftable: [Yes | No] [color = Green | Dark Red]
	 * 	 	Damageable: [Yes | No] [color = Dark Red | Green]
	 */
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//		if (stack.getCapability(DURABILITY).isPresent()) {
//			stack.getCapability(DURABILITY).ifPresent(cap -> {
//				if (cap.isInfinite()) {
//					tooltipComponents.add(Component.translatable(LangUtil.tooltip("cap.durability.amount.infinite")));
//				}
//				else {
//					tooltipComponents.add(Component.translatable(LangUtil.tooltip("cap.durability.amount"), cap.durability(stack.getItem()) - stack.getDamageValue(), cap.durability(stack.getItem())));
//				}
//			});
//		}
//		else {
//			tooltipComponents.add(Component.translatable(LangUtil.tooltip("cap.durability.amount"), /*stack.getMaxDamage() - stack.getDamageValue()*/"whaat", getDurability()));
//		}

		tooltipComponents.add(Component.translatable(LangUtil.tooltip("key_lock.rarity"), ChatFormatting.BLUE + Component.translatable(getRarity(Minecraft.getInstance().level.registryAccess()).getName().toLowerCase()).getString().toUpperCase() ));
		tooltipComponents.add(Component.translatable(LangUtil.tooltip("key_lock.category"), ChatFormatting.GOLD + Component.translatable(getCategory().toString().toLowerCase()).getString().toUpperCase()));

		LangUtil.appendAdvancedHoverText(tooltipComponents, tt -> {
			
			// is breakable tooltip
			MutableComponent breakable = null;
			if (isBreakable()) {
				breakable = Component.translatable(LangUtil.tooltip("boolean.yes")).withStyle(ChatFormatting.DARK_RED);
			}
			else {
				breakable = Component.translatable(LangUtil.tooltip("boolean.no")).withStyle(ChatFormatting.GREEN);
			}
			tooltipComponents.add(
					Component.translatable(LangUtil.tooltip("key_lock.breakable"), breakable));

			MutableComponent craftable = null;
			if (isCraftable()) {
				craftable = Component.translatable(LangUtil.tooltip("boolean.yes")).withStyle(ChatFormatting.GREEN);
			}
			else {
				craftable = Component.translatable(LangUtil.tooltip("boolean.no")).withStyle(ChatFormatting.DARK_RED);
			}
			tooltipComponents.add(Component.translatable(LangUtil.tooltip("key_lock.craftable"), craftable));
		
			appendHoverSpecials(stack, context.level(), tooltipComponents, tooltipFlag);
			appendHoverExtras(stack, context.level(), tooltipComponents, tooltipFlag);
		});
		// NOTE adding curse here makes it unremovable.
		// TEMP fix for double Curses displayed
		appendCurse(stack, tooltipComponents);
	}

	public void appendCurse(ItemStack stack, List<Component> tooltip) {

	}

	public  void appendHoverSpecials(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
	}
	
	public void appendHoverExtras(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
	}

	/**
	 * 
	 */
	@Override
	public boolean isValidRepairItem(ItemStack itemToRepair, ItemStack resourceItem) {
		return resourceItem.getItem() == this || super.isValidRepairItem(itemToRepair, resourceItem);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		// exit if on the client
		if (WorldInfo.isClientSide(context.getLevel())) {
			return InteractionResult.FAIL;
		}

		BlockPos chestPos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(chestPos);
		Block block = state.getBlock();

		// test if the block is a chest proxy (ex. multiple block chests like Wither Chest)
		if (block instanceof ITreasureChestBlockProxy) {
			chestPos = ((ITreasureChestBlockProxy)block).getChestPos(chestPos);
			state = context.getLevel().getBlockState(chestPos);
			block = state.getBlock();
		}

		// determine if block at pos is a treasure chest
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
				ItemStack heldItemStack = context.getPlayer().getItemInHand(context.getHand());
				boolean breakKey = true;
				boolean fitsLock = false;
				LockState lockState = null;
				boolean isKeyBroken = false;
				// check if this key is one that opens a lock (only first lock that key fits is unlocked).
				lockState = fitsFirstLock(context.getLevel(), chestBlockEntity.getLockStates());
				if (lockState != null) {
					fitsLock = true;
				}

				if (fitsLock) {
					breakKey = useKeyOnLock(context, block, state, chestPos, chestBlockEntity, lockState);
				}

//				IDurabilityHandler cap = heldItemStack.getCapability(DURABILITY).orElseThrow(IllegalStateException::new);
				
				// check key's breakability
				if (breakKey) {
					Treasure.LOGGER.debug("breakKey -> {}", breakKey);
					if (!context.getPlayer().isCreative() && (isBreakable() || anyLockBreaksKey(chestBlockEntity.getLockStates(), this)) && Config.SERVER.keysAndLocks.enableKeyBreaks.get()) {
						Treasure.LOGGER.debug("is breakable -> {}", isBreakable());
						// this damage block is considering if a key has been merged with another key.
						// it is only 'breaking' 1 key's worth of damage
						// ex k1(1/10d) + k2(0/10d) = k3(1/20d), only apply 9 damage
						// so k3 = (10/20d) ie 1 key's worth damage was applied.
//						8/20/2024:
//						int durability = cap.durability(heldItemStack.getItem());

						// get the default durability of of the item
						int durability = getDurability();
						int damage = heldItemStack.getDamageValue() + (durability - (heldItemStack.getDamageValue() % durability));

						heldItemStack.setDamageValue(damage);
						Treasure.LOGGER.debug("damaging key -> {}", heldItemStack.getDamageValue());
						if (heldItemStack.getDamageValue() >= heldItemStack.getMaxDamage()) {
							// key is fully consumed
							heldItemStack.shrink(1);
						}

						// do effects
						doKeyBreakEffects(context.getLevel(), context.getPlayer(), chestPos);

						// flag the key as broken
						isKeyBroken = true;
					}
					else if (!fitsLock) {
						doKeyNotFitEffects(context.getLevel(), context.getPlayer(), chestPos);
					}
					else {
						doKeyUnableToUnlockEffects(context.getLevel(), context.getPlayer(), chestPos);
					}						
				}

				// user attempted to use key - increment the damage
				if (!context.getPlayer().isCreative() && isDamageable(heldItemStack) && !isKeyBroken) {
					heldItemStack.setDamageValue(heldItemStack.getDamageValue() + 1);
					Treasure.LOGGER.debug("damaging key -> {}", heldItemStack.getDamageValue());

//					if (heldItemStack.getDamageValue() >= cap.durability(heldItemStack.getItem())) {
//						heldItemStack.shrink(1);
//					}
				}
			} catch (Exception e) {
				Treasure.LOGGER.error("error: ", e);
			}			
		}		

		return super.useOn(context);
	}

	/**
	 *
	 * @param context the original context.
	 * @param block the calculated block. takes into account use of proxy blocks.
	 * @param state the calculated state. takes into account use of proxy blocks.
	 * @param chestPos the calculated chestPos. takes into account use of proxy blocks.
	 * @param blockEntity the calculated block entity. takes into account use of proxy blocks.
	 * @param lockState the lock state.
	 * @return a boolean value to indicate whether the key should be broken
	 */
	protected boolean useKeyOnLock(UseOnContext context, Block block, BlockState state, BlockPos chestPos, ITreasureChestBlockEntity blockEntity, LockState lockState) {
		if (unlock(context.getLevel(), lockState.getLock().orElseThrow())) {
			// unlock the lock
			doUnlock(context, blockEntity, lockState);

			if (!state.getValue(AbstractTreasureChestBlock.DISCOVERED)) {
				blockEntity = ((AbstractTreasureChestBlock) block).discovered((AbstractTreasureChestBlockEntity) blockEntity, state, context.getLevel(), chestPos, context.getPlayer());
			}

			// update the client
			((AbstractTreasureChestBlockEntity) blockEntity).updateAttachmentAndSync();

			// don't break the key
			return false;
		}
		// default break the key
		return true;
	}

	/**
	 * 
	 * @param context
	 * @param chestTileEntity
	 * @param lockState
	 */
	public void doUnlock(UseOnContext context, ITreasureChestBlockEntity chestTileEntity, LockState lockState) {
		LockItem lock = lockState.getLock().orElseThrow();
		lock.doUnlock(context.getLevel(), context.getPlayer(), context.getClickedPos(), lockState);

		if (!breaksLock(lock)) {
			// spawn the lock
			lock.dropLock(context.getLevel(), context.getClickedPos());
		}

		// sync the cleared lock state to all tracking clients
		if (chestTileEntity instanceof AbstractTreasureChestBlockEntity be) {
			be.sendUpdates();
		}
	}

	/**
	 * This method is a secondary check against a lock item.
	 * Add predicates to the fistsLock list to overrule LockItem.acceptsKey()
	 *  if this is a key with special abilities.
	 * @param lockItem
	 * @return
	 */
	public boolean fitsLock(Level level, LockItem lockItem) {
		if (getFitsLock() == null || getFitsLock().isEmpty()) {
			return false;
		}
		for (BiPredicate<Level, LockItem> p : this.getFitsLock()) {
			boolean result = p.test(level, lockItem);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param lockStates
	 * @return
	 */
	public LockState  fitsFirstLock(Level level, List<LockState> lockStates) {
		LockState lockState = null;
		// check if this key is one that opens a lock (only first lock that key fits is unlocked).
		for (LockState ls : lockStates) {
			if (ls.getLock().isPresent()) {
				lockState = ls;
				if (lockState.getLock().get().acceptsKey(this) || fitsLock(level, lockState.getLock().get())) {
					return ls;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param lockItem
	 * @return
	 */
	public boolean unlock(Level level, LockItem lockItem) {
		if (lockItem.acceptsKey(this) || fitsLock(level, lockItem)) {
			Treasure.LOGGER.debug("lock -> {} accepts key -> {}", ModUtil.getName(lockItem), ModUtil.getName(this));
			if (RandomHelper.checkProbability(new Random(), this.getSuccessProbability())) {
				Treasure.LOGGER.debug("unlock attempt met probability");
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param lockItem
	 * @return
	 */
    public boolean breaksLock(LockItem lockItem) {
		if (getBreaksLock() == null || getBreaksLock().isEmpty()) {
			return false;
		}
		for (Predicate<LockItem> p : this.getBreaksLock()) {
			boolean result = p.test(lockItem);
			if (!result) {
				return false;
			}
		}
		return true;
    }

	/**
	 * 
	 * @param lockStates
	 * @param key
	 * @return
	 */
	public boolean anyLockBreaksKey(List<LockState> lockStates, KeyItem key) {
		for (LockState ls : lockStates) {
			if (ls.getLock().isPresent()) {
				if (ls.getLock().get().breaksKey(key)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the rarity
	 */
	public IRarity getRarity(RegistryAccess provider) {
		return RarityTagAssociationRegistry.getKeyRarity(this, provider).orElseGet(TreasureRarities.UNKNOWN::get);
	}

	/**
	 * @return the craftable
	 */
	public boolean isCraftable() {
		return craftable;
	}

	/**
	 * @param craftable the craftable to set
	 */
	public KeyItem setCraftable(boolean craftable) {
		this.craftable = craftable;
		return this;
	}

	@Override
	public String toString() {
		return "KeyItem{" +
				"breakable=" + breakable +
				", category=" + category +
				", craftable=" + craftable +
				", durability=" + durability +
				"} " + super.toString();
	}

	/**
	 * @return the category
	 */
	public KeyLockCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public KeyItem setCategory(KeyLockCategory category) {
		this.category = category;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBreakable() {
		return breakable;
	}

	/**
	 * 
	 * @param breakable
	 */
	public KeyItem setBreakable(boolean breakable) {
		this.breakable = breakable;
		return this;
	}

	public KeyItem addFitsLock(BiPredicate<Level, LockItem> p) {
		if (fitsLock == null) {
			fitsLock = new ArrayList<>();
		}
		fitsLock.add(p);
		return this;
	}

	public List<BiPredicate<Level, LockItem>> getFitsLock() {
		return this.fitsLock;
	}

	public KeyItem addBreaksLock(Predicate<LockItem> p) {
		if (breaksLock == null) {
			breaksLock = new ArrayList<>();
		}
		breaksLock.add(p);
		return this;
	}
	
	public List<Predicate<LockItem>> getBreaksLock() {
		return breaksLock;
	}
	
	/**
	 * @return the successProbability
	 */
	public double getSuccessProbability() {
		return successProbability;
	}

	/**
	 * @param successProbability the successProbability to set
	 */
	public KeyItem setSuccessProbability(double successProbability) {
		this.successProbability = successProbability;
		return this;
	}

	/**
	 * Convenience method
	 * @return the damageable
	 */
	public boolean isDamageable(ItemStack stack) {
		// ensure that stack != null -some mod is using mixins on this function
		// and the stack could possibly be null
		if (stack == null) {
			return super.isDamageable(stack);
		}

//		IDurabilityHandler handler = stack.getCapability(TreasureCapabilities.DURABILITY).map(h -> h).orElse(null);
//		if (handler != null) {
//			return !handler.isInfinite();
//		}
		return true;
	}

//	public void setDamageable(boolean damageable) {
//		this.damageable = damageable;
//	}


	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
}
