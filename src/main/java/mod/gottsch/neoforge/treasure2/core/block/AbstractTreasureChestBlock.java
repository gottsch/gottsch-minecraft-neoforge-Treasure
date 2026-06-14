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
package mod.gottsch.neoforge.treasure2.core.block;

import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.neo.gottschcore.spatial.Coords;
import mod.gottsch.neo.gottschcore.spatial.Heading;
import mod.gottsch.neo.gottschcore.spatial.ICoords;
import mod.gottsch.neo.gottschcore.spatial.Rotate;
import mod.gottsch.neo.gottschcore.world.WorldInfo;
import mod.gottsch.neoforge.treasure2.Treasure;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.block.entity.ITreasureChestBlockEntity;
import mod.gottsch.neoforge.treasure2.core.component.*;
import mod.gottsch.neoforge.treasure2.core.inventory.InventoryHelper;
import mod.gottsch.neoforge.treasure2.core.lock.LockLayout;
import mod.gottsch.neoforge.treasure2.core.lock.LockSlot;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import mod.gottsch.neoforge.treasure2.core.rarity.IRarity;
import mod.gottsch.neoforge.treasure2.core.rarity.TreasureRarities;
import mod.gottsch.neoforge.treasure2.core.registry.RarityTagAssociationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Mark Gottschling on Sep 16, 2018
 *
 */
public abstract class AbstractTreasureChestBlock extends BaseEntityBlock implements ITreasureChestBlock, SimpleWaterloggedBlock {
	// default shape
	private static final VoxelShape CHEST = Block.box(1, 0, 1, 15, 14, 15);
	// waterlogged state
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


	/*
	 * an array of VoxelShape shapes for the bounding box
	 */
	private VoxelShape[] bounds = new VoxelShape[4];

	/*
	 *  the class of the tileEntityClass this BlockChest should use.
	 */
//	private final Class<?> blockEntityClass;
	private final Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType;

	/*
	 *  an instance of the blockEntity defined by blockEntityClass
	 */
	@Deprecated // replace by inventorySize
	private final AbstractTreasureChestBlockEntity blockEntityInstance;

	/*
	 * the type of chest
	 */
	private LockLayout lockLayout;

	/*
	 * the inventory size
	 */
	private int inventorySize;


	protected static <T extends AbstractTreasureChestBlock> MapCodec<T> makeCodec(
					Function4<BlockEntityType<?>, Integer, LockLayout, Properties, T> factory) {

				return RecordCodecBuilder.mapCodec(instance ->
				instance.group(
						// 1. Entity Type
						BuiltInRegistries.BLOCK_ENTITY_TYPE.byNameCodec()
								.fieldOf("block_entity_type")
								.forGetter(AbstractTreasureChestBlock::getBlockEntityType),

						// 2. int
						Codec.INT.fieldOf("inventory_size").forGetter(AbstractTreasureChestBlock::getInventorySize),

						// 3. lock layout
						LockLayout.CODEC.fieldOf("lock_layout").forGetter(AbstractTreasureChestBlock::getLockLayout),
						// 4. BlockBehaviour.Properties (Must be first to match constructor)
						// Use the propertiesCodec() and attach the forGetter() to its result.
						BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(AbstractTreasureChestBlock::properties)

				).apply(instance, factory)
		);
	}

	public AbstractTreasureChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, int inventorySize, LockLayout lockLayout, Properties properties) {
		this(() -> blockEntityType, inventorySize, lockLayout, properties);
	}

	public AbstractTreasureChestBlock(BlockEntityType<? extends AbstractTreasureChestBlockEntity> blockEntityType, LockLayout lockLayout, Properties properties) {
		this(() -> blockEntityType, lockLayout, properties);
	}

	public AbstractTreasureChestBlock(Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType, int inventorySize, LockLayout lockLayout, Properties properties) {
		super(properties);
		this.blockEntityType = blockEntityType;
		this.lockLayout = lockLayout;
		this.blockEntityInstance = null;
		this.inventorySize = inventorySize;

		setBounds(
				new VoxelShape[] {
						CHEST, 	// N
						CHEST,  	// E
						CHEST,  	// S
						CHEST		// W
				});

		registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(DISCOVERED, true)
						.setValue(WATERLOGGED, Boolean.valueOf(false))
		);
	}

	public AbstractTreasureChestBlock(Supplier<BlockEntityType<? extends AbstractTreasureChestBlockEntity>> blockEntityType, LockLayout lockLayout, Properties properties) {
		super(properties);
		this.blockEntityType = blockEntityType;
		this.lockLayout = lockLayout;
		this.blockEntityInstance = null;

		setBounds(
				new VoxelShape[] {
						CHEST, 	// N
						CHEST,  	// E
						CHEST,  	// S
						CHEST		// W
				});

		registerDefaultState(
				this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(DISCOVERED, true)
				.setValue(WATERLOGGED, Boolean.valueOf(false))
				);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

		AbstractTreasureChestBlockEntity chestTileEntity = null;
		try {
			chestTileEntity = newInstanceBlockEntity(pos, state);

			// setup lock states
			List<LockState> lockStates = new LinkedList<>();

			for (int i = 0; i < lockLayout.getSlots().length; i++) {
				LockState lockState = new LockState();
				lockState.setSlot(lockLayout.getSlots()[i]);
				// add in order of slot indexes
				lockStates.add(lockState.getSlot().getIndex(), lockState);
			}
			chestTileEntity.setLockStates(lockStates);
//			Treasure.LOGGER.info("AbstractTreasureChestBlock | newBlockEntity | lockStates -> {}", chestTileEntity.getLockStates());
//			Treasure.LOGGER.info("AbstractTreasureChestBlock | newBlockEntity | tileEntity -> {} @ {}", chestTileEntity, chestTileEntity.getBlockPos());
		}
		catch(Exception e) {
			Treasure.LOGGER.error("error", e);
		}
		return chestTileEntity;
	}

	/**
	 * 
	 * @param pos
	 * @param state
	 * @return
	 */
	protected AbstractTreasureChestBlockEntity newInstanceBlockEntity(BlockPos pos, BlockState state) {
		/*
		 *  construct a new instance of the block entity.
		 *  ensure to use BlockPos.class and not pos.getClass() for the Class<?> type variable
		 *  because when this method is called from load, a MutableBlockPos is passed in, 
		 *  and reflection will not be able to locate the constructor because it has a different signature, 
		 *  and this will save from having every concrete Chest class needing to implement a separate 
		 *  constructor for the MutableBlockPos.
		 */
//		try {
//			Class<?>[] type = { BlockPos.class, BlockState.class };
//			Constructor<?> cons = getBlockEntityClass().getConstructor(type);
//			return (AbstractTreasureChestBlockEntity) cons.newInstance(pos, state);
//		}
//		catch(Exception e) {
//			Treasure.LOGGER.error("error", e);
//			return null;
//		}
		// 1. Get the stored BlockEntityType instance from the field
		// (We assume the field is named 'blockEntityType' and is of type BlockEntityType<? extends AbstractTreasureChestBlockEntity>)

		// 2. Use the BlockEntityType's factory method 'create'
		// The create method uses the BlockEntitySupplier (the factory) stored internally
		// to call the BE's constructor (BlockEntity(BlockPos, BlockState)).
		return this.blockEntityType.get().create(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return (lvl, pos, blockState, t) -> {
				if (t instanceof ITreasureChestBlockEntity entity) { // test and cast
					entity.tickClient();
					entity.tickParticle();
				}
			};
		}
		else {
			//			return (lvl, pos, blockState, t) -> {
			//				if (t instanceof ITreasureChestBlockEntity entity) { // test and cast
			//					entity.tickServer();
			//				}
			//			};
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder
		.add(FACING)
		.add(DISCOVERED)
		.add(WATERLOGGED);
	}

	/**
	 * 
	 */
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	/**
	 * called just after the player places a block.
	 */
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		// face the block towards the player
		level.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 3);

		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity != null && blockEntity instanceof AbstractTreasureChestBlockEntity chestBlockEntity) {
//			AbstractTreasureChestBlockEntity be = (AbstractTreasureChestBlockEntity)blockEntity;

			boolean isDirty = false;
			Heading previousChestHeading = Heading.NORTH;

			// set the custom name if any
			if (stack.has(DataComponents.CUSTOM_NAME)) {
				chestBlockEntity.setCustomName(stack.getHoverName());
			}

			// load inventory from ItemStack to BlockEntity
			ComponentHelper.inventory(stack).ifPresent(inventory -> {
				// TODO this should update the IItemHandler, NOT the Attachment. the OnChange method of the handler will update the Attachment
				// TODO OR setInventory should be updating the IITemHandler
				chestBlockEntity.setInventory(inventory);
				InventoryHelper.loadContentsIntoHandler(inventory, chestBlockEntity.itemHandler);
			});

			// get the direction the block is facing currently
			Heading heading = Heading.fromDirection(placer.getDirection().getOpposite());

			// rotate the lock states on the block entity
			isDirty = rotateLockStates(level, Coords.of(pos), previousChestHeading.getRotation(heading));

			if (Treasure.LOGGER.isDebugEnabled()) {
				Treasure.LOGGER.debug("new lock states ->");
				for (LockState ls : chestBlockEntity.getLockStates()) {
					Treasure.LOGGER.debug(ls.toString());
				}
			}

			// update the facing on the block entity
			chestBlockEntity.setFacing(placer.getDirection().getOpposite());

			if (isDirty) {
				// update the client with the different properties - facing, lock states, inventory
				chestBlockEntity.updateAttachmentAndSync();
				// TODO the rest
			}
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		super.useWithoutItem(state, level, pos, player, hitResult);

		if (WorldInfo.isClientSide(level)) {
			return InteractionResult.SUCCESS;
		}

		Treasure.LOGGER.debug("using treasure chest...");

		AbstractTreasureChestBlockEntity blockEntity = (AbstractTreasureChestBlockEntity) level.getBlockEntity(pos);
		if (blockEntity != null) {
			// check for mimic — empty ResourceLocation (default) means no mimic
			if (blockEntity.getMimic() != null && !blockEntity.getMimic().getPath().isEmpty()) {
				spawnMimic(level, level.getRandom(), state, pos, player);
			}
			else if (!blockEntity.isLocked()) {			
				// if not discovered then set as discovered
				if (!state.getValue(DISCOVERED)) {
					// mark as discovered
					blockEntity = discovered(blockEntity, state, level, pos, player);
				}
				// 2. Use the vanilla ServerPlayer.openMenu method
				// This handles sending the necessary packet to the client.
				if (player instanceof ServerPlayer serverPlayer) {
					// 1. Cast the BlockEntity to the MenuProvider interface

                    serverPlayer.openMenu(
                            (MenuProvider) blockEntity,
						// The third argument is a lambda that writes the BlockPos to a buffer.
						// This is used by the client to correctly locate the BlockEntity after opening the menu.
						(buffer) -> buffer.writeBlockPos(pos)
				);
				}
			}		
		}
		return InteractionResult.SUCCESS;
	}

	/**
	 * 
	 * @param level
	 * @param random
	 * @param state
	 * @param pos
	 * @param player
	 */
	protected void spawnMimic(Level level, RandomSource random, BlockState state, BlockPos pos, Player player) {
		AbstractTreasureChestBlockEntity blockEntity = (AbstractTreasureChestBlockEntity) level.getBlockEntity(pos);
		EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(blockEntity.getMimic());

		// remove the block entity
		level.removeBlock(pos, true);

		// calculate yRot
		float yRot = switch(state.getValue(FACING)) {
		case DOWN, UP, SOUTH -> 0f;
		case NORTH -> 180f;
		case EAST -> -90f;
		case WEST -> 90f;
		default -> 0f;
		};

		Mob mob = spawn((ServerLevel)level, level.getRandom(), entityType, pos, player, yRot);
		if (mob != null) {
			// DEFERRED (workstream C): give the spawned mimic the chest's loot table + notify the client.
			// Blocked on (1) Mimic#setLootTable (see Mimic.java — Forge's ObfuscationReflectionHelper hack
			// doesn't apply; 1.21 uses Optional<ResourceKey<LootTable>>) and (2) a new MimicSpawn S2C payload
			// (model it on core/network/*MistMessageToServer). Mimic currently spawns with its own loot table.
//			((Mimic)mob).setLootTable(blockEntity.getLootTable());
//			// update client
//			MimicSpawnS2C message = new MimicSpawnS2C(mob.getId(), yRot);
//			TreasureNetworking.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> mob), message);
		}
	}

	/**
	 *
	 * @param level
	 * @param randomSource
	 * @param entityType
	 * @param pos
	 * @param target
	 * @param yRot
	 * @return
	 */
	protected Mob spawn(ServerLevel level, RandomSource randomSource, EntityType<?> entityType, BlockPos pos, LivingEntity target, float yRot) {
		double spawnX = pos.getX() + 0.5;
		double spawnY = pos.getY();
		double spawnZ = pos.getZ() + 0.5;
		if (!WorldInfo.isClientSide(level)) {
			SpawnPlacementType placementType = SpawnPlacements.getPlacementType(entityType);
			if (NaturalSpawner.isValidEmptySpawnBlock(level, pos, level.getBlockState(pos), level.getFluidState(pos), entityType)) {
				Mob mob = (Mob)entityType.create(level);
				mob.setPos(spawnX, spawnY, spawnZ);
				mob.setTarget(target);
				mob.setYRot(yRot);
				mob.yRotO = yRot;
				level.addFreshEntityWithPassengers(mob);
				return mob;			
			}
		}
		return null;
	}

	/**
	 * 
	 * @param blockEntity
	 * @param state
	 * @param level
	 * @param pos
	 * @param player
	 * @return
	 */
	public AbstractTreasureChestBlockEntity discovered(AbstractTreasureChestBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player player) {
		if (level.isClientSide()) {
			return blockEntity;
		}

		// save current chest data
//		CompoundTag tag = new CompoundTag();
//		blockEntity.saveAdditional(tag, level.registryAccess());
		TreasureChestComponents chestComponents = TreasureChestComponents.from(blockEntity);

		// save chest state
		BlockState oldState = state;

		
		// place new chest with old state, but different light
		level.setBlockAndUpdate(pos, oldState.getBlock()
				.defaultBlockState()
				.setValue(AbstractTreasureChestBlock.FACING, oldState.getValue(AbstractTreasureChestBlock.FACING))
				.setValue(AbstractTreasureChestBlock.DISCOVERED, true));

		// load be from item
		AbstractTreasureChestBlockEntity newBlockEntity = (AbstractTreasureChestBlockEntity) level.getBlockEntity(pos);
		// NOTE: preserve the captured chest state as-is. discovered() re-places the block (for the
		// different "discovered" light level), which spawns a fresh BE; we copy the original chest data
		// onto it. Crucially the sealed flag must be preserved — discovery happens on first interaction
		// (incl. unlocking a locked chest), which is BEFORE the chest is opened. Resetting sealed here
		// would skip loot generation in createMenu (chest opens empty). Matches Forge's saveAdditional/
		// loadProperties round-trip, which preserved the full state.
		chestComponents.to(newBlockEntity);

		// mark as dirty
		newBlockEntity.setChanged();

//		DEFERRED (workstream C): mark this chest as discovered in the persistent cache. TreasureChestCache +
//		TreasureSavedData exist in the port, but the cache is slated to migrate to a data Attachment
//		(see TreasureChestCacheData "needs to be converted to an Attachment"); restore once that lands so
//		the cache shape isn't reworked twice. Discovery still works visually (block state DISCOVERED above).
//		TreasureChestCache.getCache().stream()
//				// if matching on dimension and pos, doesn't require to match on biome
//				.filter(chest -> chest.getDimensionName().equals(level.dimensionType().effectsLocation()))
//				.filter(chest -> chest.getCoords().equals(Coords.of(pos)))
//				.findFirst().ifPresent(chest -> {
//					Treasure.LOGGER.debug("marking chest at pos {} as discovered", pos.toShortString());
//					chest.setDiscovered(true);
//					// mark the persistence data as dirty
//					TreasureSavedData.get(level).setDirty();
//				});

		return newBlockEntity;
	}

	/**
	 * 
	 */
	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest,
			FluidState fluid) {

		if (WorldInfo.isClientSide(level)) {
			return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
		}	
		Treasure.LOGGER.debug("chest destroyed by player....!");
		breakChest(state, level, pos, player);

		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
	}

	/**
	 * 
	 */
	@Override
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		if (WorldInfo.isClientSide(level)) {
			super.onBlockExploded(state, level, pos, explosion);
			return;
		}
		Treasure.LOGGER.debug("chest exploded....!");
		breakChest(state, level ,pos, null);

		super.onBlockExploded(state, level, pos, explosion);
	}

	/**
	 * NOTE breakChest() needs to be called from onDestroyedByPlayer() and onBlockExploded() instead of
	 * onRemove() because of the why the light emission is handled. When the chest is replaced to recalculate the light
	 * emission we don't want the chest to drop itself.
	 * @param state
	 * @param level
	 * @param pos
	 * @param player
	 * @return
	 */
	public boolean breakChest(BlockState state, Level level, BlockPos pos, Player player) {		
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity != null) {

			if (blockEntity instanceof AbstractTreasureChestBlockEntity) {
				AbstractTreasureChestBlockEntity be = (AbstractTreasureChestBlockEntity)blockEntity;

				// unlocked!
				if (!be.isLocked()) {
					// drop the be's inventory
					be.dropContents(level, pos);

					// spawn chest item into the world
					ItemStack chestItem = new ItemStack(Item.byBlock(this), 1);
					Containers.dropItemStack(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
							chestItem);

					// write the properties to the nbt
					// TODO research MagicTreasures for components
//					chestItem.has(SPECIFIC DATA COMPONENT)
//					if (!chestItem.hasTag()) {
//						chestItem.setTag(new CompoundTag());
//					}
//					be.saveProperties(chestItem.getTag()); // -> this will become a transfer method between Component and Attachment
				}
				else {
					if (WorldInfo.isServerSide(level)) {
						// TODO save
//						ItemStack chestItem = new ItemStack(Item.byBlock(this), 1);
//						// give the chest a tag compound
//						CompoundTag tag = new CompoundTag();
//						be.saveAdditional(tag);
//						chestItem.setTag(tag);
//						Containers.dropItemStack(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
//								chestItem);
					}
				}
			}
		}		
		return true;
	}

	public boolean  rotateLockStates(CommonLevelAccessor level, ICoords coords, Rotate rotate) {
		boolean hasRotated = false;
		boolean shouldRotate = false;
		if (rotate != Rotate.NO_ROTATE) {
			shouldRotate = true;
		}
		//		Treasure.LOGGER.debug("rotate to:" + rotate);

		AbstractTreasureChestBlockEntity be = null;
		BlockEntity blockEntity = level.getBlockEntity(coords.toPos());
		if (blockEntity != null && blockEntity instanceof AbstractTreasureChestBlockEntity) {
			be = (AbstractTreasureChestBlockEntity) blockEntity;
		}
		else {
			return false;
		}

		try {
			for (LockState lockState : be.getLockStates()) {
				if (lockState != null && lockState.getSlot() != null) {
					//	Treasure.LOGGER.debug("original lock state:" + lockState);
					// if a rotation is needed
					if (shouldRotate) {
						LockSlot newSlot = lockState.getSlot().rotate(rotate);
						//	Treasure.LOGGER.debug("new slot position:" + newSlot);
						lockState.setSlot(newSlot);
						// set the flag to indicate the lockStates have rotated
						hasRotated = true;
					}
				}
			}
		}
		catch(Exception e) {
			Treasure.LOGGER.error("error updating lock states: ", e);
		}
		return hasRotated;
	}

	/**
	 * 
	 */
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(FACING)) {
		default:
		case NORTH:
			return bounds[0];
		case EAST:
			return bounds[1];
		case SOUTH:
			return bounds[2];
		case WEST:
			return bounds[3];
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockState = this.defaultBlockState().setValue(FACING,
				context.getHorizontalDirection().getOpposite());
		//////////
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		blockState.setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
		return blockState;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor levelAccessor, BlockPos pos, BlockPos p_56930_) {
		if (state.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(state, direction, newState, levelAccessor, pos, p_56930_);
	}
	
	@Override
	public FluidState getFluidState(BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	   
//	@Override
//	public Class<?> getBlockEntityClass() {
//		return blockEntityClass;
//	}

	@Override
	public BlockEntityType<? extends AbstractTreasureChestBlockEntity> getBlockEntityType() {
		return blockEntityType.get();
	}

	@Override
	public LockLayout getLockLayout() {
		return lockLayout;
	}

	public void setLockLayout(LockLayout layout) {
		this.lockLayout = layout;
	}

	/**
	 * Wrapper for call to the RarityTagAssociationRegistry and handles null values.
	 */
	@Override
	public IRarity getRarity(HolderLookup.Provider provider) {
		return RarityTagAssociationRegistry.getChestRarity(this, provider).orElseGet(TreasureRarities.UNKNOWN);
	}

	@Override
	public VoxelShape[] getBounds() {
		return bounds;
	}

	@Override
	public AbstractTreasureChestBlock setBounds(VoxelShape[] bounds) {
		this.bounds = bounds;
		return this;
	}

	@Deprecated
	public AbstractTreasureChestBlockEntity getBlockEntityInstance() {
		return blockEntityInstance;
	}

	@Override
	public int getInventorySize() {
		return this.inventorySize;
	}
}
