package iuriineves.neves_capybaras.mixin;

import iuriineves.neves_capybaras.event.ConsumeItemCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class OnConsumeMixin {
	@Inject(at = @At(value = "INVOKE"), method = "finishUsing")
	private void onConsume(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<Boolean> info) {
		ActionResult result = ConsumeItemCallback.EVENT.invoker().interact(stack, user);

		if (result == ActionResult.FAIL) {
			info.cancel();
		}

	}
}