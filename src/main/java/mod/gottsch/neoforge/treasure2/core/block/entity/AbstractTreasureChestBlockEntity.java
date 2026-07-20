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
package mod.gottsch.neoforge.treasure2.core.block.entity;

import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.attachment.AttachmentHelper;
import mod.gottsch.neoforge.treasure2.core.attachment.TreasureAttachments;
import mod.gottsch.neoforge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.neoforge.treasure2.core.block.effects.IChestEffects;
import mod.gottsch.neoforge.treasure2.core.config.Config;
import mod.gottsch.neoforge.treasure2.core.generator.chest.ChestGenerationHelper;
import mod.gottsch.neoforge.treasure2.core.inventory.StandardChestContainerMenu;
import mod.gottsch.neoforge.treasure2.core.lock.LockSlot;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.network.InventorySyncPacket;
import mod.gottsch.neoforge.treasure2.core.network.TreasureNetworking;
import mod.gottsch.neoforge.treasure2.core.particle.TreasureParticles;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mark Gottschling on Nov 10, 2022
 *
 */
public abstract class AbstractTreasureChestBlockEntity extends BlockEntity
		implements ITreasureChestBlockEntity, IChestEffects, MenuProvider, Nameable {

	/*
	 * NBT keys for the one-shot "generation data" compound written by the chest subprocessors onto the
	 * StructureBlockInfo at world-gen time. Vanilla loadWithComponents(nbt) feeds it to this block
	 * entity at placement, where loadAdditional() parses it once into the persistent attachments. This
	 * is required because StructureProcessor.finalizeProcessing runs pre-placement (no block entity
	 * exists there yet), so the data cannot be applied to the block entity during processing.
	 */
	public static final String GENERATION_DATA_TAG = "TreasureGenerationData";
	public static final String LOOT_TABLE_TAG = "lootTable";
	public static final String SEALED_TAG = "sealed";
	public static final String MIMIC_TAG = "mimic";
	public static final String LOCK_STATES_TAG = "lockStates";
	public static final String GENERATION_CONTEXT_TAG = "generationContext";

	/*
	 * the transient inventory of the chest
	 */
	public final ItemStackHandler itemHandler = createHandler();

	/*
	 * A list of lockStates the chest has. The list should be the size of the max
	 * allowed for the chestType.
	 */
	/*
	 * Vanilla properties for controlling the lid
	 */
	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;
	/** The angle of the lid last tick */
	public float prevLidAngle;

	/*
	 * Server updated properties
	 */
	/** The number of players currently using this chest */
	public int openCount;
	/** Server sync counter (once per 20 ticks) */
	public int ticksSinceSync;

	/** flag used by getFullInventoryContents() to bypass the locked-chest read guard */
	private boolean bypassLockForRead = false;

	/**
	 * 
	 * @param type
	 * @param pos
	 * @param state
	 */
	public AbstractTreasureChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		setFacing(Direction.NORTH.get3DDataValue());
	}


	/**
	 * 
	 */
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
		Treasure.LOGGER.debug("is chest sealed -> {}", this.isSealed());
		if (this.isSealed() && !getLevel().isClientSide) {
			IRarity rarity = this.getGenerationContext().getLootRarity();
			ChestGenerationHelper.fillChest((ServerLevel) getLevel(), getLevel().getRandom(), this, rarity, playerEntity);
			// only clear the seal once the chest is actually filled, so a failed fill doesn't
			// permanently strand the chest empty - it will simply retry on the next open.
			this.setSealed(false);
		}
		return createChestContainerMenu(windowId, playerInventory, playerEntity);
	}

	/**
	 * 
	 * @param windowId
	 * @param playerInventory
	 * @param playerEntity
	 * @return
	 */
	public AbstractContainerMenu createChestContainerMenu(int windowId, Inventory playerInventory, Player playerEntity) {
		return new StandardChestContainerMenu(windowId, this.worldPosition, playerInventory, playerEntity);
	}

	/**
	 * 
	 * @return
	 */
	private ItemStackHandler createHandler() {
		return new ItemStackHandler(getInventorySize()) {

			@Override
			protected void onContentsChanged(int slot) {
				// to make sure the BE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
				// send updates to clients
				if (level != null && !level.isClientSide) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
					// Update the immutable ItemContainerContents attachment and sync it.
					updateAttachmentAndSync();
				}
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return !isLocked();
			}

			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
				if (isLocked()) {
					return ItemStack.EMPTY;
				}
				return super.insertItem(slot, stack, simulate);
			}
			
			@Override
			public ItemStack getStackInSlot(int slot) {
				if (!bypassLockForRead && isLocked()) {
					return ItemStack.EMPTY;
				}
				return super.getStackInSlot(slot);
			}
			
			@Override
			public void setStackInSlot(int slot, ItemStack stack) {
				if (isLocked()) {
					return;
				}
				super.setStackInSlot(slot, stack);
			}
		};
	}


	/**
	 * 
	 * @param level
	 * @param pos
	 */
	public void dropContents(Level level, BlockPos pos) {
		SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
		for(int i = 0; i < itemHandler.getSlots(); i++) {
			inv.setItem(i, itemHandler.getStackInSlot(i));
		}

		Containers.dropContents(this.level, this.worldPosition, inv);
	}

	/**
	 * 
	 */
	@Override
	public void tickClient() {
		this.prevLidAngle = this.lidAngle;
		if (this.openCount > 0 && this.lidAngle == 0.0F) {
			doChestOpenEffects(level, null, getBlockPos());
//			this.playSound(SoundEvents.CHEST_OPEN);
		}

		if (this.openCount == 0 && this.lidAngle > 0.0F || this.openCount > 0 && this.lidAngle < 1.0F) {
			float f2 = this.lidAngle;

			if (this.openCount > 0) {
				this.lidAngle += 0.1F;
			} else {
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			//float f3 = 0.5F;
			if (this.lidAngle < 0.5F && f2 >= 0.5F) {
				doChestCloseEffects(level, null, getBlockPos());
//				this.playSound(SoundEvents.CHEST_CLOSE);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}	
	}

	@Override
	public void tickParticle() {
		if (Config.SERVER.effects.enableUndiscoveredEffects.get()
				&& !getBlockState().getValue(AbstractTreasureChestBlock.DISCOVERED)) {

			if (getLevel().getGameTime() % 10 == 0) {
				RandomSource random = getLevel().getRandom();
				for(int k = 0; k < 5; ++k) {
					SimpleParticleType coinParticle;
					int x = k % 3;
					if (x == 0) {
						coinParticle = TreasureParticles.COPPER_COIN_PARTICLE.get();
					} else if (x == 1) {
						coinParticle = TreasureParticles.SILVER_COIN_PARTICLE.get();
					} else {
						coinParticle = TreasureParticles.GOLD_COIN_PARTICLE.get();
					}
					getLevel().addParticle(coinParticle, 
							(double)getBlockPos().getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 
							(double)getBlockPos().getY() + random.nextDouble() + random.nextDouble(),
							(double)getBlockPos().getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1),
							0.0D, 0.07D, 0.0D);
				}
			}
		}
	}


	@Override
	public void tickServer() {
	}
	
	/**
	 * 
	 * @param sound
	 */
	protected void playSound(SoundEvent sound) {
		level.playSound(null, getBlockPos(), sound, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	// --- Synchronization Logic ---

	/**
	 * Helper method to update the ItemContainerContents attachment and trigger the manual sync.
	 * This is called automatically by the onContentsChanged() method in the handler.
	 */
    public void updateAttachmentAndSync() {
		if (this.level.isClientSide) return; // Only run on the server

		// 1. Convert the mutable ItemStackHandler's contents into the immutable ItemContainerContents object.
		List<ItemStack> stacks = new ArrayList<>(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			// We use getStackInSlot() to retrieve the item stack at the current index.
			stacks.add(itemHandler.getStackInSlot(i));
		}

		ItemContainerContents currentContents = ItemContainerContents.fromItems(stacks);
		setInventory(currentContents);
		this.setChanged();

		InventorySyncPacket packet = new InventorySyncPacket(this.getBlockPos(), currentContents);
		TreasureNetworking.syncInventoryToClients(this, packet);
	}

	// --- New Capability Accessor (REQUIRED FOR BLOCK CAPABILITIES) ---
	/**
	 * Exposes the internal mutable item handler instance for the Block Capability system.
	 * This method is called during capability lookup.
	 */
	public IItemHandler getInventoryHandler() {
		return this.itemHandler;
	}

	/**
	 * Returns the full inventory contents, bypassing the locked-chest read guard.
	 * Used when breaking a locked chest to preserve its inventory on the dropped item.
	 */
	public ItemContainerContents getFullInventoryContents() {
		bypassLockForRead = true;
		try {
			List<ItemStack> stacks = new ArrayList<>(itemHandler.getSlots());
			for (int i = 0; i < itemHandler.getSlots(); i++) {
				stacks.add(itemHandler.getStackInSlot(i));
			}
			return ItemContainerContents.fromItems(stacks);
		} finally {
			bypassLockForRead = false;
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("inventory", itemHandler.serializeNBT(registries));
	}

	/**
	 * Provides the packet used to sync this block entity to clients when
	 * {@link net.minecraft.world.level.Level#sendBlockUpdated} is called.
	 */
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * The full NBT used both for initial chunk-load sync and for {@link #getUpdatePacket} payloads.
	 * Includes attachments via {@code saveCustomOnly}.
	 */
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveCustomOnly(registries);
	}

	/**
	 * Broadcast a full block-entity sync to all tracking clients. Use after mutating
	 * attachments (e.g. lock states) that aren't covered by {@link #updateAttachmentAndSync}.
	 */
	public void sendUpdates() {
		if (level == null || level.isClientSide) return;
		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
		setChanged();
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		// NOTE super first: this runs NeoForge's attachment deserialization, which only fires when the
		// tag actually contains attachment data (i.e. on normal chunk reload, never at structure
		// placement). Applying the generation data after super therefore never gets clobbered.
		super.loadAdditional(tag, registries);
		if (tag.contains("inventory")) {
			itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
		}
		// one-shot generation data injected by the chest subprocessors at world-gen placement
		if (tag.contains(GENERATION_DATA_TAG)) {
			applyGenerationData(tag.getCompound(GENERATION_DATA_TAG), registries);
		}
	}

	/**
	 * Parses the placement-time generation-data compound (loot table, sealed flag, lock states, mimic,
	 * generation context) into the persistent attachments. Only called once, at structure placement.
	 */
	private void applyGenerationData(CompoundTag data, HolderLookup.Provider registries) {
		RegistryOps<Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);

		if (data.contains(SEALED_TAG)) {
			setSealed(data.getBoolean(SEALED_TAG));
		}
		if (data.contains(LOOT_TABLE_TAG) && !data.getString(LOOT_TABLE_TAG).isEmpty()) {
			setLootTable(ResourceLocation.parse(data.getString(LOOT_TABLE_TAG)));
		}
		if (data.contains(MIMIC_TAG) && !data.getString(MIMIC_TAG).isEmpty()) {
			setMimic(ResourceLocation.parse(data.getString(MIMIC_TAG)));
		}
		if (data.contains(LOCK_STATES_TAG)) {
			LockState.CODEC.listOf().parse(ops, data.get(LOCK_STATES_TAG))
					.resultOrPartial(err -> Treasure.LOGGER.warn("unable to parse generation lock states -> {}", err))
					.ifPresent(decodedLocks -> {
						// buildLocks only encodes locked slots; reconstruct the full per-layout slot
						// list so empty slots aren't lost (which would cap the chest at fewer than
						// its layout-defined maximum).
						if (getBlockState().getBlock() instanceof AbstractTreasureChestBlock chestBlock) {
							List<LockState> fullSlots = new ArrayList<>();
							for (LockSlot slot : chestBlock.getLockLayout().getSlots()) {
								LockState ls = new LockState();
								ls.setSlot(slot);
								decodedLocks.stream()
										.filter(d -> d.getSlot().getIndex() == slot.getIndex())
										.findFirst()
										// adopt the decoded slot too: it was rotated to the chest's facing at
										// generation time. Keeping the layout (unrotated) slot would render
										// the lock on the chest's default face, i.e. "backwards".
										.ifPresent(d -> { ls.setSlot(d.getSlot()); d.getLock().ifPresent(ls::setLock); });
								fullSlots.add(slot.getIndex(), ls);
							}
							setLockStates(fullSlots);
						} else {
							setLockStates(decodedLocks);
						}
					});
		}
		if (data.contains(GENERATION_CONTEXT_TAG)) {
			GenerationContext.CODEC.parse(ops, data.get(GENERATION_CONTEXT_TAG))
					.resultOrPartial(err -> Treasure.LOGGER.warn("unable to parse generation context -> {}", err))
					.ifPresent(this::setGenerationContext);
		}
	}


	@Override
	public boolean isLocked() {
		return hasLocks();
	}

	@Override
	public boolean hasLocks() {
		if (getLockStates() == null || getLockStates().isEmpty()) {
			return false;
		}
		for (LockState state : getLockStates()) {
			if (state.getLock().isPresent())
				return true;
		}
		return false;
	}

	@Override
	public Component getDisplayName() {
		return this.getName();
	}

	@Override
	public Component getName() {
		return hasCustomName() ? getCustomName() : this.getDefaultName();
	}

	@Override
	public Component getDefaultName() {
		return Component.translatable(LangUtil.screen("default_chest.name"));
	}

	@Override
	public Component getCustomName() {
		return AttachmentHelper.customNameValue(this).map(Component::literal).orElse(null);
	}

	public void setCustomName(Component name) {
		setCustomName(name.getString());
	}

	public void setCustomName(String name) {
		AttachmentHelper.setCustomName(this, name);
	}

	@Override
	public ItemContainerContents getInventory() {
		return AttachmentHelper.inventory(this);
	}

	public void setInventory(ItemContainerContents inventory) {
		AttachmentHelper.setInventory(this, inventory);
	}

	// TODO all this needs to be added to AttachmentHelper and ComponentHelper
	@Override
	public List<LockState> getLockStates() {
		return AttachmentHelper.lockStates(this).lockStates();
	}

	@Override
	public void setLockStates(List<LockState> lockStates) {
		AttachmentHelper.setLockStates(this, lockStates);
	}

	@Override
	public Direction getFacing() {
		return AttachmentHelper.facing(this);
	}

	public void setFacing(Direction facing) {
		AttachmentHelper.setFacing(this, facing);
	}

	@Override
	public void setFacing(int facingIndex) {
		AttachmentHelper.setFacing(this, Direction.from3DDataValue(facingIndex));
	}

	@Override
	public boolean isSealed() {
		return AttachmentHelper.sealed(this).sealed();
	}

	@Override
	public void setSealed(boolean sealed) {
		AttachmentHelper.setSealed(this, sealed);
	}

	@Override
	public ResourceLocation getLootTable() {
		return AttachmentHelper.lootTable(this);
	}

	@Override
	public void setLootTable(ResourceLocation lootTable) {
		AttachmentHelper.setLootTable(this, lootTable);
	}


	@Override
	public GenerationContext getGenerationContext() {
		return AttachmentHelper.generationContext(this);
	}

	@Override
	public void setGenerationContext(GenerationContext generationContext) {
		AttachmentHelper.setGenerationContext(this, generationContext);
	}
	
	@Override
	public ResourceLocation getMimic() {
		return AttachmentHelper.mimic(this);
	}

	@Override
	public void setMimic(ResourceLocation mimic) {
		AttachmentHelper.setMimic(this, mimic);
	}

}
