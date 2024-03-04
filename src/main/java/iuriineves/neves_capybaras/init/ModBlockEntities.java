package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.block_entity.ThermalSpringBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlockEntities {

    Map<FabricBlockEntityTypeBuilder<? extends BlockEntity>, Identifier> BLOCK_ENTITIES = new LinkedHashMap<>();

    BlockEntityType<ThermalSpringBlockEntity> THERMAL_SPRING_BLOCK_ENTITY = createBlockEntity("thermal_spring", FabricBlockEntityTypeBuilder.create(ThermalSpringBlockEntity::new, ModBlocks.THERMAL_SPRING_BLOCK)).build();

    private static FabricBlockEntityTypeBuilder<? extends BlockEntity> createBlockEntity(String name, FabricBlockEntityTypeBuilder<? extends BlockEntity> blockEntityType) {
        BLOCK_ENTITIES.put(blockEntityType, new Identifier(NevesCapybaras.MOD_ID, name));
        return blockEntityType;
    }

    static void initialize() {
        BLOCK_ENTITIES.keySet().forEach(blockEntity -> Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCK_ENTITIES.get(blockEntity), blockEntity));
    }
}
