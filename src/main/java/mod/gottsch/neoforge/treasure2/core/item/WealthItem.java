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
import mod.gottsch.neoforge.treasure2.core.tag.TreasureTags;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import mod.gottsch.neoforge.treasure2.core.wishable.IWishable;
import mod.gottsch.neoforge.treasure2.core.wishable.IWishableHandler;
import mod.gottsch.neoforge.treasure2.core.wishable.TreasureWishableHandlers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author Mark Gottschling on Aug 17, 2021
 */
public class WealthItem extends Item implements IWishable {

	public WealthItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.translatable(LangUtil.tooltip("wishable")).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
		Level level = entityItem.level();
		if (WorldInfo.isClientSide(level)) {
			return super.onEntityItemUpdate(stack, entityItem);
		}

		if (!stack.is(TreasureTags.Items.WISHABLES)) {
			return super.onEntityItemUpdate(stack, entityItem);
		}

		IWishableHandler handler = getHandler();
		if (handler.isValidLocation(entityItem)) {
			handler.doWishable(entityItem);
			return true;
		}
		return super.onEntityItemUpdate(stack, entityItem);
	}

	@Override
	public IWishableHandler getHandler() {
		return TreasureWishableHandlers.DEFAULT_WISHABLE_HANDLER;
	}
}
