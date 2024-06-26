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


/************************************************************************************
     Rewrote part of this interface to match how Minecraft registers Status Effects
     Minecraft 1.21 also requires a 'RegistryEntry<StatusEffect>' to be passed
     instead of how 1.20.4 used to require it.
     Left original code commented out in case original author wishes to reimplement
     their original way of registering later on.
 ************************************************************************************/


public interface ModStatusEffects {
    //Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();

    public static final RegistryEntry<StatusEffect> SWEETENED = register("sweetened", new SweetenedStatusEffect(StatusEffectCategory.BENEFICIAL, 0xf34a13));
    //SweetenedStatusEffect SWEETENED = createStatusEffect("sweetened", new SweetenedStatusEffect(StatusEffectCategory.BENEFICIAL, 0xf34a13));


    /*private static <T extends StatusEffect> T createStatusEffect(String name, T effect) {
        EFFECTS.put(effect, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name));
        return effect;
    }*/

    private static RegistryEntry<StatusEffect> register(String name, StatusEffect statusEffect)
    {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name), statusEffect);
    }

    static void initialize() {
        //EFFECTS.keySet().forEach(item -> Registry.register(Registries.STATUS_EFFECT, EFFECTS.get(item), item));
    }
}