package sn0wfrog.sn0wfrogs_capybaras.init;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.block_entity.ThermalSpringBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModBlockEntities {

    BlockEntityType<ThermalSpringBlockEntity> THERMAL_SPRING_BLOCK_ENTITY = BlockEntityType.Builder.create(ThermalSpringBlockEntity::new, ModBlocks.THERMAL_SPRING_BLOCK).build();

    static void initialize() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, "thermal_spring"), THERMAL_SPRING_BLOCK_ENTITY);

    }
}
