package iuriineves.neves_capybaras.block_entity;

import iuriineves.neves_capybaras.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ThermalSpringBlockEntity extends BlockEntity {
    public ThermalSpringBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY, pos, state);
    }
}
