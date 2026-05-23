package mod.gottsch.neoforge.treasure2.core.component;

import mod.gottsch.neoforge.treasure2.core.attachment.TreasureAttachments;
import mod.gottsch.neoforge.treasure2.core.world.feature.TreasureFeatureTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

/**
 * @author by Mark Gottschling on 11/26/2025
 */
public class ComponentHelper {
    /*
     * component accessors
     */
    // inventory
    public static Optional<ItemContainerContents> inventory(ItemStack stack) {
        return Optional.ofNullable(stack.get(TreasureComponents.ITEM_INVENTORY));
    }

    // lock states
    public static Optional<LockStatesComponent> lockStates(ItemStack stack) {
        return Optional.ofNullable(stack.get(TreasureComponents.LOCK_STATES));
    }

    public static CustomNameComponent setCustomName(ItemStack stack, CustomNameComponent component) {
        return stack.set(TreasureComponents.CUSTOM_NAME, component);
    }
}
