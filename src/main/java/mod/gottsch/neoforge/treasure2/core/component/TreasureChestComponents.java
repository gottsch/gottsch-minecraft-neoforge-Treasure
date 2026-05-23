package mod.gottsch.neoforge.treasure2.core.component;

import mod.gottsch.neoforge.treasure2.core.attachment.AttachmentHelper;
import mod.gottsch.neoforge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * @author by Mark Gottschling on 12/4/2025
 */
public class TreasureChestComponents {
    private CustomNameComponent customName;
    private Direction facing;
    private ResourceLocation lootTable;
    private ResourceLocation mimic;
    private SealedComponent sealed;
    private LockStatesComponent lockStates;

    public TreasureChestComponents() {}

    public static TreasureChestComponents from(BlockEntity blockEntity) {
        TreasureChestComponents components = new TreasureChestComponents();

        components.customName = AttachmentHelper.customName(blockEntity);
        components.facing = AttachmentHelper.facing(blockEntity);
        components.lootTable = AttachmentHelper.lootTable(blockEntity);
        components.mimic = AttachmentHelper.mimic(blockEntity);
        components.sealed = AttachmentHelper.sealed(blockEntity);
        components.lockStates = AttachmentHelper.lockStates(blockEntity);
        return components;
    }

    public void to(AbstractTreasureChestBlockEntity blockEntity) {
//        blockEntity.setCustomName(this.customName.name());
        AttachmentHelper.setCustomName(blockEntity, this.customName);
        AttachmentHelper.setFacing(blockEntity, facing);
        AttachmentHelper.setLootTable(blockEntity, lootTable);
        AttachmentHelper.setMimic(blockEntity, mimic);
        AttachmentHelper.setSealed(blockEntity, sealed);
        AttachmentHelper.setLockStates(blockEntity, lockStates);

    }

    public CustomNameComponent getCustomName() {
        return customName;
    }

    public void setCustomName(CustomNameComponent customName) {
        this.customName = customName;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public LockStatesComponent getLockStates() {
        return lockStates;
    }

    public void setLockStates(LockStatesComponent lockStates) {
        this.lockStates = lockStates;
    }

    public ResourceLocation getLootTable() {
        return lootTable;
    }

    public void setLootTable(ResourceLocation lootTable) {
        this.lootTable = lootTable;
    }

    public ResourceLocation getMimic() {
        return mimic;
    }

    public void setMimic(ResourceLocation mimic) {
        this.mimic = mimic;
    }

    public SealedComponent getSealed() {
        return sealed;
    }

    public void setSealed(SealedComponent sealed) {
        this.sealed = sealed;
    }
}
