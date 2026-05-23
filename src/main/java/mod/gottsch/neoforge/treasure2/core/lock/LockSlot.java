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
package mod.gottsch.neoforge.treasure2.core.lock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neo.gottschcore.spatial.Heading;
import mod.gottsch.neo.gottschcore.spatial.Rotate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * Defines a slot on a chest where a lock is placed.
 * Used in TreasureChestTypes to define the slots on different chest types.
 * Used in TreasureChestTileEntity to save current orientation of occupied slots.
 * @author Mark Gottschling on Jan 9, 2018
 *
 */
public class LockSlot {

	private int index;
	private Heading face;
	private float xOffset;
	private float yOffset;
	private float zOffset;
	private float rotation;

	public static final Codec<LockSlot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("index").forGetter(LockSlot::getIndex),
			Heading.CODEC.fieldOf("face").forGetter(LockSlot::getFace),
			Codec.FLOAT.fieldOf("xOffset").forGetter(LockSlot::getXOffset),
			Codec.FLOAT.fieldOf("yOffset").forGetter(LockSlot::getYOffset),
			Codec.FLOAT.fieldOf("zOffset").forGetter(LockSlot::getZOffset),
			Codec.FLOAT.fieldOf("rotation").forGetter(LockSlot::getRotation)
	).apply(instance, LockSlot::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, LockSlot> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, LockSlot::getIndex,
			Heading.STREAM_CODEC, LockSlot::getFace,
			ByteBufCodecs.FLOAT, LockSlot::getXOffset,
			ByteBufCodecs.FLOAT, LockSlot::getYOffset,
			ByteBufCodecs.FLOAT, LockSlot::getZOffset,
			ByteBufCodecs.FLOAT, LockSlot::getRotation,
			LockSlot::new
	);

	/**
	 * Empty constructor
	 */
	public LockSlot() {	
	}
	
	/**
	 * 
	 */
	public LockSlot(int index, Heading face, float x, float y, float z, float rotation) {
		setIndex(index);
		setFace(face);
		setXOffset(x);
		setYOffset(y);
		setZOffset(z);
		setRotation(rotation);
	}

	/**
	 * 
	 * @param r
	 * @return
	 */
//	@Override
	public LockSlot rotate(Rotate r) {
		Heading newFace = getFace().rotateY(r);
		float x = getXOffset();
		float z = getZOffset();
		float rotation = getRotation();
	
		/*
		 *  NOTE this currently only works for a 1x1 standard cube size. See Treasure1710 for making generic
		 *  method to calculate rotation position/shapes on multicube/irregular shaped blocks.
		 */
		// switch on the rotation
		switch(r) {
		case ROTATE_90:
			// switch on the base
			switch (this.getFace()) {
			case NORTH:
				x = 1 - getZOffset();
				z = getXOffset();
				rotation = 90F;
				break;
			case EAST:
				x = getZOffset();
				z = getXOffset();
				rotation = 180F;
				break;
			case SOUTH:
				x = 1 - getZOffset();
				z = getXOffset();
				rotation = -90F;
				break;
			case WEST:
				x = getZOffset();
				z = getXOffset();
				rotation = 0F;
				break;
			default:
				break;			
			}
			break;
		case ROTATE_180:
			switch (this.getFace()) {
			case NORTH:
				x = 1 - getXOffset();
				z = 1 - getZOffset();
				rotation = 180F;				
			case SOUTH:
				x = 1 - getXOffset();
				z = 1 - getZOffset();
				rotation = 180F;
				break;
			case EAST:
				x = 1 - getXOffset();
				z = getZOffset();
				rotation = -90;
			case WEST:
				x = 1 - getXOffset();
				z = getZOffset();
				rotation = 90;
				break;
			default:
				break;			
			}
			break;		
		case ROTATE_270:
			switch (this.getFace()) {
			case NORTH:
				x = getZOffset();
				z = 1-getXOffset();
				rotation = -90;
				break;
			case EAST:
				x = getZOffset();
				z = 1 - getXOffset();
				rotation = 0;
				break;
			case SOUTH:
				x = getZOffset();
				z = 1 - getXOffset();
				rotation = 90;
				break;
			case WEST:
				x = getZOffset();
				z = 1- getXOffset();
				rotation = 180;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}		
		LockSlot slot = new LockSlot(getIndex(), newFace, x, getYOffset(), z, rotation);
		return slot;
	}
	
	/**
	 * @return the face
	 */
//	@Override
	public Heading getFace() {
		return face;
	}

	/**
	 * @param face the face to set
	 */
//	@Override
	public void setFace(Heading face) {
		this.face = face;
	}

	/**
	 * @return the xOffset
	 */
//	@Override
	public float getXOffset() {
		return xOffset;
	}

	/**
	 * @param xOffset the xOffset to set
	 */
//	@Override
	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	/**
	 * @return the yOffset
	 */
//	@Override
	public float getYOffset() {
		return yOffset;
	}

	/**
	 * @param yOffset the yOffset to set
	 */
//	@Override
	public void setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	/**
	 * @return the zOffset
	 */
//	@Override
	public float getZOffset() {
		return zOffset;
	}

	/**
	 * @param zOffset the zOffset to set
	 */
//	@Override
	public void setZOffset(float zOffset) {
		this.zOffset = zOffset;
	}

	/**
	 * @return the index
	 */
//	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
//	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the rotation
	 */
//	@Override
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
//	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LockSlot [index=" + index + ", face=" + face + ", xOffset=" + xOffset + ", yOffset=" + yOffset
				+ ", zOffset=" + zOffset + ", rotation=" + rotation + "]";
	}
	
}
