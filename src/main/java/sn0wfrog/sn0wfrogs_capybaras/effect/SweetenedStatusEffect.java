package sn0wfrog.sn0wfrogs_capybaras.effect;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import sn0wfrog.sn0wfrogs_capybaras.event.ConsumeItemCallback;
import sn0wfrog.sn0wfrogs_capybaras.init.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class SweetenedStatusEffect extends StatusEffect {

    public SweetenedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity entity, int amplifier, double proximity) {
        if (!(entity instanceof PlayerEntity player)) { return;}
        if ((entity.getWorld().isClient())) {return;}


        ConsumeItemCallback.EVENT.register((itemStack, user) -> {
            if (user == player && player.hasStatusEffect(ModStatusEffects.SWEETENED)) {      //Registries.STATUS_EFFECT.getEntry(ModStatusEffects.SWEETENED)

                // check for golden and enchanted golden apple for buffed status effects
                if (itemStack.getItem() == Items.GOLDEN_APPLE) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 1));
                }

                if (itemStack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 4));
                }
                FoodComponent itemStackAs_FoodComponent = itemStack.getComponents().get(DataComponentTypes.FOOD);
                // 1.5x food logic
                if( itemStackAs_FoodComponent!= null)
                    player.getHungerManager().add((itemStackAs_FoodComponent.nutrition() / 4), 0.3f);
            }
            return ActionResult.SUCCESS;
        });
    }
}
