package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
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

    private static SoundEvent createSoundEvent(String path) {
        SoundEvent soundEvent = SoundEvent.of(new Identifier(NevesCapybaras.MOD_ID, path));
        SOUND_EVENTS.put(soundEvent, new Identifier(NevesCapybaras.MOD_ID, path));
        return soundEvent;
    }

    static void initialize() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> {
            Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent);
        });
    }
}
