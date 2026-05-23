package mod.gottsch.neoforge.treasure2.core.inventory;

/**
 * @author by Mark Gottschling on 12/2/2025
 */
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.IItemHandler;
import java.util.List;

/**
 * Utility class containing common methods for working with NeoForge item handlers and vanilla components.
 */
public class InventoryHelper {

    /**
     * Updates the contents of a mutable IItemHandler (like ItemStackHandler)
     * using the stacks provided by an immutable ItemContainerContents component.
     * * @param contents The source immutable component containing the item stacks.
     * @param handler The destination mutable IItemHandler to update.
     */
    public static void loadContentsIntoHandler(ItemContainerContents contents, IItemHandler handler) {
        // Ensure both objects are valid before proceeding
        if (contents == null || handler == null) {
            return;
        }

        List<ItemStack> stacks = contents.stream().toList();

        // Loop through the smaller of the two sizes (stacks list vs handler slots)
        // to prevent ArrayIndexOutOfBounds errors if sizes mismatch.
        int slotsToProcess = Math.min(stacks.size(), handler.getSlots());

        for (int i = 0; i < slotsToProcess; i++) {
            // Note: IItemHandler implementations usually have a setter
            // method like 'setStackInSlot' available if they are concrete classes
            // (like ItemStackHandler). Since the argument type is the interface,
            // we have to check the actual type.

            // The ItemStackHandler is the standard implementation that exposes setStackInSlot(int, ItemStack).
            if (handler instanceof net.neoforged.neoforge.items.ItemStackHandler stackHandler) {
                // Set the stack directly. We use copy() for safety, though ItemContainerContents
                // stacks should be immutable lists of immutable stacks.
                stackHandler.setStackInSlot(i, stacks.get(i).copy());
            } else {
                // For other IItemHandler implementations, a direct setter may not be available
                // via the interface, but since ItemStackHandler is common, we use the direct access.
                // If you were only using the IItemHandler interface, you'd have to use
                // complex insert/extract logic, but for internal loading, direct setting is preferred.
                // We'll proceed assuming the handler is the concrete ItemStackHandler type
                // used in your BlockEntity, as is standard practice.
            }
        }

        // Optional: If the ItemContainerContents list was smaller than the handler slots,
        // you might want to clear the remaining slots.
        for (int i = slotsToProcess; i < handler.getSlots(); i++) {
            if (handler instanceof net.neoforged.neoforge.items.ItemStackHandler stackHandler) {
                stackHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}
