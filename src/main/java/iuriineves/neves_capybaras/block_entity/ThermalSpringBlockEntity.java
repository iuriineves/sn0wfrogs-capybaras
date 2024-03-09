package iuriineves.neves_capybaras.block_entity;

import iuriineves.neves_capybaras.init.ModBlockEntities;
import iuriineves.neves_capybaras.init.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermalSpringBlockEntity extends BlockEntity {

    public ThermalSpringBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY, pos, state);

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public static double calculateDistance(BlockPos blockPos1, BlockPos blockPos2) {
        double distanceX = blockPos2.getX() - blockPos1.getX();
        double distanceY = blockPos2.getY() - blockPos1.getY();
        double distanceZ = blockPos2.getZ() - blockPos1.getZ();

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }

    private static void emitParticles(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock() == Blocks.WATER) {
            if (!(world.getTime() % 5 == 0)) {
                return;
            }


            BlockPos waterBlock = blockPos.up();
            while (world.getBlockState(waterBlock).getBlock() == Blocks.WATER) {
                waterBlock = waterBlock.up();
            }

            BlockPos finalWaterBlock = waterBlock;
            BlockPos.iterateOutwards(waterBlock, 10, 1, 10).forEach(blockPos1 -> {
                if (world.getBlockState(blockPos1).getBlock() == Blocks.WATER) {
                    double distance = calculateDistance(finalWaterBlock, blockPos1);
                    if (distance <= 10) {
                        world.addParticle(ParticleTypes.CLOUD, blockPos1.getX() + ((double) world.random.nextBetween(0, 10) / 10), blockPos1.getY() + 0.5, blockPos1.getZ() + ((double) world.random.nextBetween(0, 10) / 10), 0.0 + (((double) world.random.nextBetween(0, 1) / 10) - 0.05), 0.2 - ((0.2 / 10) * distance), 0.0 + (((double) world.random.nextBetween(0, 1) / 10) - 0.05));
                    }
                }
            });
        } else if (world.getBlockState(blockPos.down()).getBlock() == Blocks.WATER) {
            world.addParticle(ParticleTypes.CLOUD, blockPos.getX() + ((double) world.random.nextBetween(3, 6) / 10), blockPos.up().getY(), blockPos.getZ() + ((double) world.random.nextBetween(3, 6) / 10), 0.0 + (((double) world.random.nextBetween(0, 1) / 10) - 0.05), 0.5, 0.0 + (((double) world.random.nextBetween(0, 1) / 10) - 0.05));
        }
    }


    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        emitParticles(world, blockPos);
    }
}
