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
package mod.gottsch.neoforge.treasure2.core.creativetab;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.item.TreasureItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author Mark Gottschling on May 22, 2025
 */
public class TreasureCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Treasure.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TREASURE_TAB =
            CREATIVE_MODE_TABS.register("treasure2", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Treasure.MODID))
                    .icon(() -> new ItemStack(TreasureItems.WOOD_KEY.get()))
                    .displayItems((params, output) -> {
                        // keys
                        output.accept(TreasureItems.WOOD_KEY.get());
                        output.accept(TreasureItems.STONE_KEY.get());
                        output.accept(TreasureItems.LEAF_KEY.get());
                        output.accept(TreasureItems.EMBER_KEY.get());
                        output.accept(TreasureItems.LIGHTNING_KEY.get());
                        output.accept(TreasureItems.IRON_KEY.get());
                        output.accept(TreasureItems.GOLD_KEY.get());
                        output.accept(TreasureItems.METALLURGISTS_KEY.get());
                        output.accept(TreasureItems.DIAMOND_KEY.get());
                        output.accept(TreasureItems.EMERALD_KEY.get());
                        output.accept(TreasureItems.TOPAZ_KEY.get());
                        output.accept(TreasureItems.ONYX_KEY.get());
                        output.accept(TreasureItems.RUBY_KEY.get());
                        output.accept(TreasureItems.SAPPHIRE_KEY.get());
                        output.accept(TreasureItems.JEWELLED_KEY.get());
                        output.accept(TreasureItems.SPIDER_KEY.get());
                        output.accept(TreasureItems.WITHER_KEY.get());
                        output.accept(TreasureItems.SKELETON_KEY.get());
                        output.accept(TreasureItems.PILFERERS_LOCK_PICK.get());
                        output.accept(TreasureItems.THIEFS_LOCK_PICK.get());
                        output.accept(TreasureItems.BONE_KEY.get());
                        // TODO: ONE_KEY when ported

                        // locks
                        output.accept(TreasureItems.WOOD_LOCK.get());
                        output.accept(TreasureItems.STONE_LOCK.get());
                        output.accept(TreasureItems.LEAF_LOCK.get());
                        output.accept(TreasureItems.EMBER_LOCK.get());
                        output.accept(TreasureItems.IRON_LOCK.get());
                        output.accept(TreasureItems.GOLD_LOCK.get());
                        output.accept(TreasureItems.DIAMOND_LOCK.get());
                        output.accept(TreasureItems.EMERALD_LOCK.get());
                        output.accept(TreasureItems.TOPAZ_LOCK.get());
                        output.accept(TreasureItems.ONYX_LOCK.get());
                        output.accept(TreasureItems.RUBY_LOCK.get());
                        output.accept(TreasureItems.SAPPHIRE_LOCK.get());
                        output.accept(TreasureItems.SPIDER_LOCK.get());
                        output.accept(TreasureItems.WITHER_LOCK.get());
                        // TODO: BONE_LOCK when ported

                        // utility
                        output.accept(TreasureItems.KEY_RING.get());
                        output.accept(TreasureItems.POUCH.get());
                        // TODO: TREASURE_TOOL, coins, gems, EYE_PATCH, CLOVER when ported

                        // chests — only WOOD_CHEST has a registered block item so far
                        output.accept(TreasureItems.WOOD_CHEST.get());
                        // TODO: register block items for remaining chests and add them here
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
