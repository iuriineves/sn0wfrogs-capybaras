package sn0wfrog.sn0wfrogs_capybaras.init;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.entity.CapybaraEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModEntities {
    Map<EntityType<? extends Entity>, Identifier> ENTITIES = new LinkedHashMap<>();

    private static <T extends EntityType<? extends Entity>> T createEntity(String name, T entity) {
        ENTITIES.put(entity, new Identifier(Sn0wfrogsCapybaras.MOD_ID, name));
        return entity;
    }

    EntityType<CapybaraEntity> CAPYBARA = createEntity(
            "capybara",
            FabricEntityTypeBuilder
                    .create(SpawnGroup.CREATURE, CapybaraEntity::new)
                    .entityFactory(CapybaraEntity::new)
                    .dimensions(EntityDimensions.changing(0.8f, 0.8f))
                    .build()
    );

    static void initialize() {
        ENTITIES.keySet().forEach((entityType) -> {
                Registry.register(Registries.ENTITY_TYPE, ENTITIES.get(entityType), entityType);
                }
        );

        FabricDefaultAttributeRegistry.register(CAPYBARA, CapybaraEntity.createCapybaraAttributes());
    }
}
