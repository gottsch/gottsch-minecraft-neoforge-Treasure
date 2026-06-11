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
package mod.gottsch.neoforge.treasure2.core.world.feature;

import mod.gottsch.neoforge.treasure2.Treasure;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 *
 * @author Mark Gottschling May 12, 2023
 *
 */
public class FeatureType implements IFeatureType {
	private final ResourceLocation ID;

	private final String name;
	private final int code;

	public FeatureType(String name, int code) {
		this.name = name;
		this.code = code;
		ID = ResourceLocation.fromNamespaceAndPath(Treasure.MODID, name);
	}

	@Override
	public ResourceLocation getRegistryId() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		FeatureType that = (FeatureType) o;
		return code == that.code && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, code);
	}

	@Override
	public String toString() {
		return "FeatureType{" +
				"ID=" + ID +
				", name='" + name +
				", code=" + code + '\'' +
				'}';
	}
}
