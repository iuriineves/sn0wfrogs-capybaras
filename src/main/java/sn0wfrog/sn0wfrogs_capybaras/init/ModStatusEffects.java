package sn0wfrog.sn0wfrogs_capybaras.init;

import net.minecraft.registry.entry.RegistryEntry;
import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.effect.SweetenedStatusEffect;
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

    private static <T extends StatusEffect> T createStatusEffect(String name, T effect)
    {
        EFFECTS.put(effect, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name));
        return effect;
    }

    static void initialize() {
        EFFECTS.keySet().forEach(item -> Registry.register(Registries.STATUS_EFFECT, EFFECTS.get(item), item));
    }
}