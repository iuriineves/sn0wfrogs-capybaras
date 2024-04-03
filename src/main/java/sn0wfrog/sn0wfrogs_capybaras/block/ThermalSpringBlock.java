package sn0wfrog.sn0wfrogs_capybaras.block;

import sn0wfrog.sn0wfrogs_capybaras.block_entity.ThermalSpringBlockEntity;
import sn0wfrog.sn0wfrogs_capybaras.init.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThermalSpringBlock extends Block implements BlockEntityProvider {
    public ThermalSpringBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ThermalSpringBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type != ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY) return null;
        return ThermalSpringBlockEntity::tick;
    }
}