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

import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.core.component.TreasureComponents;
import mod.gottsch.neoforge.treasure2.core.inventory.PouchContainerMenu;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;

/**
 * @author Mark Gottschling on May 13, 2020
 *
 */
public class PouchItem extends Item implements MenuProvider {

	public static final int INVENTORY_SIZE = 9;

	/**
	 *
	 * @param properties
	 */
	public PouchItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	/**
	 *
	 */
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		tooltip.add(Component.translatable(LangUtil.tooltip("pouch")).withStyle(ChatFormatting.GOLD));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand) {
		// exit if on the client
		if (WorldInfo.isClientSide(worldIn)) {
			return InteractionResultHolder.fail(player.getItemInHand(hand));
		}

		ItemStack stack = player.getItemInHand(hand);

		// open the pouch
		player.openMenu(this, buf -> ItemStack.STREAM_CODEC.encode(buf, stack));

		return InteractionResultHolder.pass(stack);
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {

		// get the held item
		ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (heldItem == null || !(heldItem.getItem() instanceof PouchItem)) {
			heldItem = player.getItemInHand(InteractionHand.OFF_HAND);
			if (heldItem == null || !(heldItem.getItem() instanceof PouchItem))
				return null;
		}

		// create inventory from the component-backed handler
		IItemHandler inventory = new ComponentItemHandler(heldItem, TreasureComponents.POUCH_INVENTORY.get(), INVENTORY_SIZE);

		// open the container
		return new PouchContainerMenu(windowId, playerInventory, inventory);
	}

	@Override
	public Component getDisplayName() {
		return getDescription();
	}
}
