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
package mod.gottsch.neoforge.treasure2.core.inventory;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 *
 * @author Mark Gottschling on Nov 22, 2022
 *
 */
public class TreasureContainers {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Treasure.MODID);

	// containers
	public static final DeferredHolder<MenuType<?>, MenuType<StandardChestContainerMenu>> STANDARD_CHEST_CONTAINER =
			registerMenuType("standard_chest_container", StandardChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<SkullChestContainerMenu>> SKULL_CHEST_CONTAINER =
			registerMenuType("skull_chest_container", SkullChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<StrongboxContainerMenu>> STRONGBOX_CONTAINER =
			registerMenuType("strongbox_container", StrongboxContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<CompressorChestContainerMenu>> COMPRESSOR_CHEST_CONTAINER =
			registerMenuType("compressor_chest_container", CompressorChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<VikingChestContainerMenu>> VIKING_CHEST_CONTAINER =
			registerMenuType("viking_chest_container", VikingChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<WitherChestContainerMenu>> WITHER_CHEST_CONTAINER =
			registerMenuType("wither_chest_container", WitherChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<CelestialChestContainerMenu>> CELESTIAL_CHEST_CONTAINER =
			registerMenuType("celestial_chest_container", CelestialChestContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<KeyRingContainerMenu>> KEY_RING_CONTAINER =
			registerMenuType("key_ring_container", KeyRingContainerMenu::new);

	public static final DeferredHolder<MenuType<?>, MenuType<PouchContainerMenu>> POUCH_CONTAINER =
			registerMenuType("pouch_container", PouchContainerMenu::new);

	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}

	public static void register(IEventBus bus) {
		MENUS.register(bus);
	}
}
