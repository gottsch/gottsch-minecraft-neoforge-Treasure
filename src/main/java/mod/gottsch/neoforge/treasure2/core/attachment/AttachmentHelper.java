package mod.gottsch.neoforge.treasure2.core.attachment;

import mod.gottsch.neoforge.treasure2.core.block.entity.GenerationContext;
import mod.gottsch.neoforge.treasure2.core.component.CustomNameComponent;
import mod.gottsch.neoforge.treasure2.core.component.LockStatesComponent;
import mod.gottsch.neoforge.treasure2.core.component.SealedComponent;
import mod.gottsch.neoforge.treasure2.core.lock.LockState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author by Mark Gottschling on 11/30/2025
 */
public class AttachmentHelper {
    /*
     * attachment accessors
     */
    // inventory
    public static ItemContainerContents inventory(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.INVENTORY);
    }

    // custom name
    public static CustomNameComponent customName(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.CUSTOM_NAME);
    }

    public static LockStatesComponent lockStates(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.LOCK_STATES);
    }

    public static Direction facing(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.FACING);
    }

    public static SealedComponent sealed(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.SEALED);
    }

    public static ResourceLocation lootTable(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.LOOT_TABLE);
    }

    public static ResourceLocation mimic(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.MIMIC);
    }

    public static GenerationContext generationContext(BlockEntity blockEntity) {
        return blockEntity.getData(TreasureAttachments.GENERATION_CONTEXT);
    }

    /*
     * attachment setters
     */

    public static ItemContainerContents setInventory(BlockEntity blockEntity, ItemContainerContents inventory) {
        return blockEntity.setData(TreasureAttachments.INVENTORY, inventory);
    }

    public static CustomNameComponent setCustomName(BlockEntity blockEntity, String name) {
//        return blockEntity.setData(TreasureAttachments.CUSTOM_NAME, new CustomNameComponent(name));
        return setCustomName(blockEntity, new CustomNameComponent(name));
    }


    public static CustomNameComponent setCustomName(BlockEntity blockEntity, CustomNameComponent component) {
        return blockEntity.setData(TreasureAttachments.CUSTOM_NAME, component);
    }

    public static LockStatesComponent setLockStates(BlockEntity blockEntity, List<LockState> lockStates) {
        return setLockStates(blockEntity,
                new LockStatesComponent(lockStates));
    }

    public static LockStatesComponent setLockStates(BlockEntity blockEntity, LockStatesComponent component) {
        return blockEntity.setData(TreasureAttachments.LOCK_STATES, component);
    }

    public static Direction setFacing(BlockEntity blockEntity, Direction facing) {
        return blockEntity.setData(TreasureAttachments.FACING, facing);
    }

    public static SealedComponent setSealed(BlockEntity blockEntity, SealedComponent sealedComponent) {
        return blockEntity.setData(TreasureAttachments.SEALED, sealedComponent);
    }

    public static SealedComponent setSealed(BlockEntity blockEntity, boolean sealed) {
        return setSealed(blockEntity, new SealedComponent(sealed));
    }

    public static ResourceLocation setLootTable(BlockEntity blockEntity, ResourceLocation lootTable) {
        return blockEntity.setData(TreasureAttachments.LOOT_TABLE, lootTable);
    }

    public static ResourceLocation setMimic(BlockEntity blockEntity, ResourceLocation mimic) {
        return blockEntity.setData(TreasureAttachments.MIMIC, mimic);
    }

    public static GenerationContext setGenerationContext(BlockEntity blockEntity, GenerationContext context) {
        return blockEntity.setData(TreasureAttachments.GENERATION_CONTEXT, context);
    }
    /*
     * value accessors
     */
    // custom name
    public static Optional<String> customNameValue(BlockEntity blockEntity) {
        String name = customName(blockEntity).name();
        return Optional.ofNullable(name.isBlank() ? null : name);
    }

    public static List<LockState> lockStatesValues(BlockEntity blockEntity) {
        return lockStates(blockEntity).lockStates();
    }
}
