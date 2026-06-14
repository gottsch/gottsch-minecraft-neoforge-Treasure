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

import java.util.List;

import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * Opens any lock and never breaks (infinite use). Faithful port of the Forge inline anon
 * key. Infinite-use replaces the old durability capability: {@link #isDamageable(ItemStack)}
 * returns false, which both stops use-damage and excludes it from anvil key-merging.
 *
 * NOTE: Forge applied the vanishing curse via a tooltip-time stack mutation. That hack isn't
 * portable to 1.21 (enchantments are a datapack registry — no Holder at static init), so the
 * curse is instead applied lazily in {@link #inventoryTick} via the level's registry access.
 *
 * @author Mark Gottschling
 */
public class OneKey extends KeyItem {

	public OneKey(Properties properties, int durability) {
		super(properties, durability);
		setCategory(KeyLockCategory.MAGIC);
		setBreakable(false);
		setCraftable(false);
		// opens any lock
		addFitsLock((level, lock) -> true);
	}

	@Override
	public boolean isDamageable(ItemStack stack) {
		// infinite use — also excludes the key from anvil merging
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);
		// faithfully restore Forge's "lost on death" vanishing curse, applied lazily here (server-side)
		// rather than via Forge's tooltip-time mutation: in 1.21 the Holder<Enchantment> is only
		// available at runtime through the level's registry access (enchantments are a datapack registry).
		if (level.isClientSide()) {
			return;
		}
		Holder<Enchantment> vanishing = level.registryAccess()
				.lookupOrThrow(Registries.ENCHANTMENT)
				.getOrThrow(Enchantments.VANISHING_CURSE);
		if (stack.getEnchantments().getLevel(vanishing) <= 0) {
			stack.enchant(vanishing, 1);
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public Component getName(ItemStack stack) {
		return ((MutableComponent) super.getName(stack)).withStyle(ChatFormatting.YELLOW);
	}

	@Override
	public void appendHoverSpecials(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(LangUtil.tooltip("key_lock.specials"),
				ChatFormatting.GOLD + Component.translatable(LangUtil.tooltip("key_lock.one_key.specials")).getString()));
	}

	@Override
	public void appendHoverExtras(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal(LangUtil.NEWLINE));
		tooltip.add(Component.literal(LangUtil.INDENT4)
				.append(Component.translatable(LangUtil.tooltip("key_lock.one_key.lore"))
						.append(Component.literal(LangUtil.INDENT4)).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC)));
		tooltip.add(Component.literal(LangUtil.NEWLINE));
	}
}
