package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.block.ThermalSpringBlock;
import iuriineves.neves_capybaras.block_entity.ThermalSpringBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlockEntities {

    BlockEntityType<ThermalSpringBlockEntity> THERMAL_SPRING_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ThermalSpringBlockEntity::new, ModBlocks.THERMAL_SPRING_BLOCK).build();

    static void initialize() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NevesCapybaras.MOD_ID, "thermal_spring"), THERMAL_SPRING_BLOCK_ENTITY);

    }
}
