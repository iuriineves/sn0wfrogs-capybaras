package iuriineves.neves_capybaras.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface ConsumeItemCallback {

    //gets called the same time as an item gets consumed
    Event<ConsumeItemCallback> EVENT = EventFactory.createArrayBacked(ConsumeItemCallback.class,
            (listeners) -> (stack, user) -> {
                for (ConsumeItemCallback listener : listeners) {
                    ActionResult result = listener.interact(stack, user);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });
    ActionResult interact(ItemStack stack, LivingEntity user);
}
