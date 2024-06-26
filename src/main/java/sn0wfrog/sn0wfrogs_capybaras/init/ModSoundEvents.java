package sn0wfrog.sn0wfrogs_capybaras.init;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModSoundEvents {

    Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

    SoundEvent ENTITY_CAPYBARA_AMBIENT = createSoundEvent("entity.capybara.ambient");
    SoundEvent ENTITY_CAPYBARA_ATTACKED = createSoundEvent("entity.capybara.attacked");

    SoundEvent BLOCK_GEYSER = createSoundEvent("block.thermal_spring.geyser");
    SoundEvent BLOCK_THERMAL_SPRING = createSoundEvent("block.thermal_spring.thermal_spring");

    private static SoundEvent createSoundEvent(String path) {
        SoundEvent soundEvent = SoundEvent.of(Identifier.of(Sn0wfrogsCapybaras.MOD_ID, path));
        SOUND_EVENTS.put(soundEvent, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, path));
        return soundEvent;
    }

    static void initialize() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> {
            Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent);
        });
    }
}
