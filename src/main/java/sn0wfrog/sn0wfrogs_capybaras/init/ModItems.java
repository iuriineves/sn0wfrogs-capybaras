package sn0wfrog.sn0wfrogs_capybaras.init;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static sn0wfrog.sn0wfrogs_capybaras.init.ModEntities.CAPYBARA;

public interface ModItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item CAPYBARA_SPAWN_EGG = createItem("capybara_spawn_egg", new SpawnEggItem(CAPYBARA, 0xa25a3d, 0x332b24, new Item.Settings()));
    Item MANDARIN = createItem("mandarin", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(ModStatusEffects.SWEETENED), 100), 1).build())));

    private static <T extends Item> T createItem(String name, T item) {
        ITEMS.put(item, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name));
        return item;
    }

    static void initialize() {
        ITEMS.keySet().forEach(item -> {
            Registry.register(Registries.ITEM, ITEMS.get(item), item);

        });
    }
}
