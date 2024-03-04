package iuriineves.neves_capybaras.block_entity;

import iuriineves.neves_capybaras.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermalSpringBlockEntity extends BlockEntity {
    public ThermalSpringBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY.build(), pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {

        if (world.getBlockState(blockPos.up()).getBlock() == Blocks.WATER) {
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0, -5.0, 0.0);
        }
    }
}
