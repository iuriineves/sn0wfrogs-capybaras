package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static iuriineves.neves_capybaras.init.ModEntities.CAPYBARA;

public interface ModItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item CAPYBARA_SPAWN_EGG = createItem("capybara_spawn_egg", new SpawnEggItem(CAPYBARA, 0xa25a3d, 0x332b24, new FabricItemSettings()));
    Item MANDARIN = createItem("mandarin", new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    private static <T extends Item> T createItem(String name, T item) {
        ITEMS.put(item, new Identifier(NevesCapybaras.MOD_ID, name));
        return item;
    }

    static void initialize() {
        ITEMS.keySet().forEach(item -> {
            Registry.register(Registries.ITEM, ITEMS.get(item), item);

        });
    }
}
