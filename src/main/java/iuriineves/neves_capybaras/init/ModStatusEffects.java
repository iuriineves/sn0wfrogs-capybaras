package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.effect.SweetenedStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModStatusEffects {
    Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();

    SweetenedStatusEffect SWEETENED = createStatusEffect("sweetened", new SweetenedStatusEffect(StatusEffectCategory.BENEFICIAL, 0xf34a13));

    private static <T extends StatusEffect> T createStatusEffect(String name, T effect) {
        EFFECTS.put(effect, new Identifier(NevesCapybaras.MOD_ID, name));
        return effect;
    }

    static void initialize() {
        EFFECTS.keySet().forEach(item -> Registry.register(Registries.STATUS_EFFECT, EFFECTS.get(item), item));
    }
}