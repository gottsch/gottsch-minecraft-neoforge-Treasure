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
package mod.gottsch.neoforge.treasure2.core.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neo.gottschcore.spatial.ICoords;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import com.mojang.serialization.Codec;

/**
 * A custom {@link ParticleType} that doubles as its own {@link ParticleOptions}, carrying the
 * source-block coords. 1.21 migration: the 1.20.1 {@code ParticleOptions.Deserializer} + {@code Codec}
 * pair was replaced by {@link #codec()} (a {@link MapCodec}) and {@link #streamCodec()}; the old
 * {@code writeToNetwork}/{@code writeToString} were removed (network ser is now via the StreamCodec).
 *
 * NOTE: colliding mists are spawned client-side in Block#animateTick, so these codecs are not
 * exercised at runtime; coords default to (0,0,0) and are effectively vestigial (the particle uses
 * its own position for collision).
 *
 * @author Mark Gottschling on 2021
 */
public class CollidingParticleType extends ParticleType<CollidingParticleType> implements ParticleOptions, ICollidingParticleType {
	private ICoords sourceCoords;

	public static final MapCodec<CollidingParticleType> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.INT.fieldOf("x").forGetter(d -> d.getSourceCoords().getX()),
					Codec.INT.fieldOf("y").forGetter(d -> d.getSourceCoords().getY()),
					Codec.INT.fieldOf("z").forGetter(d -> d.getSourceCoords().getZ())
			).apply(instance, CollidingParticleType::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, CollidingParticleType> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, d -> d.getSourceCoords().getX(),
			ByteBufCodecs.INT, d -> d.getSourceCoords().getY(),
			ByteBufCodecs.INT, d -> d.getSourceCoords().getZ(),
			CollidingParticleType::new);

	/**
	 * Registration constructor (no source coords). Defaults coords to (0,0,0) so the codecs never NPE.
	 */
	public CollidingParticleType() {
		super(false);
		this.sourceCoords = new Coords(0, 0, 0);
	}

	public CollidingParticleType(int x, int y, int z) {
		super(false);
		this.sourceCoords = new Coords(x, y, z);
	}

	@Override
	public MapCodec<CollidingParticleType> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, CollidingParticleType> streamCodec() {
		return STREAM_CODEC;
	}

	@Override
	public ParticleType<?> getType() {
		return this;
	}

	@Override
	public ICoords getSourceCoords() {
		return sourceCoords;
	}

	@Override
	public void setSourceCoords(ICoords emitterCoords) {
		this.sourceCoords = emitterCoords;
	}
}
