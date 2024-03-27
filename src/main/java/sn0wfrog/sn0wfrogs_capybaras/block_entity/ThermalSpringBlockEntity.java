package sn0wfrog.sn0wfrogs_capybaras.block_entity;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.init.ModBlockEntities;
import sn0wfrog.sn0wfrogs_capybaras.init.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ThermalSpringBlockEntity extends BlockEntity {

    boolean hasPlayer;


    public ThermalSpringBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY, pos, state);

        this.hasPlayer = false;
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

    private static void emitParticles(World world, BlockPos blockPos, ThermalSpringBlockEntity blockEntity) {

        // check if block above is water
        if (world.getBlockState(blockPos.up()).getFluidState().getFluid() == Fluids.WATER) {

            // find the highest water block
            BlockPos waterBlock = blockPos.up();
            while (world.getBlockState(waterBlock).getBlock() == Blocks.WATER) {
                waterBlock = waterBlock.up();
            }
            BlockPos finalWaterBlock = waterBlock;

            if (world.getTime() % 85 == 0) {
                world.playSound((double)finalWaterBlock.getX() + 0.5, (double)finalWaterBlock.getY() + 0.5, (double)finalWaterBlock.getZ() + 0.5, ModSoundEvents.BLOCK_THERMAL_SPRING, SoundCategory.BLOCKS, 1f, 1.0f, true);
            }

            // iterate through every block on a 5 block radius
            BlockPos.iterateOutwards(finalWaterBlock, 10, 1, 10).forEach(blockPos1 -> {
                // thermal spring logic
                if (world.getBlockState(blockPos1).getFluidState().getFluid() == Fluids.WATER) {
                    double distance = calculateDistance(finalWaterBlock, blockPos1);
                    if (distance <= 10 && world.getTime() % 20 == 0) {
                        world.addParticle(Sn0wfrogsCapybaras.WATER_VAPOR, blockPos1.getX() + ((double) world.random.nextBetween(0, 10) / 10), blockPos1.getY() + 0.5, blockPos1.getZ() + ((double) world.random.nextBetween(0, 10) / 10), 0.0 + (((double) world.random.nextDouble() / 20) - 0.05), 0.1 - ((0.2 / 20) * distance), 0.0 + ((world.random.nextDouble() / 20) - 0.05));
                    }
                }
            });
        } else if (world.getBlockState(blockPos.down()).getFluidState().getFluid() == Fluids.WATER) {
            // geyser logic
            if (world.getTime() % 85 == 0) {
                world.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, ModSoundEvents.BLOCK_GEYSER, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            }

            pushUp(world, blockPos, blockEntity);
            world.addParticle(Sn0wfrogsCapybaras.GEYSER, blockPos.getX() + ((double) world.random.nextBetween(3, 6) / 10), blockPos.up().getY(), blockPos.getZ() + ((double) world.random.nextBetween(3, 6) / 10), 0.0 + ((world.random.nextDouble() / 10) - 0.05), 0.5, 0.0 + ((world.random.nextDouble() / 10) - 0.05));
        }
    }

    // next time i need a method for pushing the player up ill just call it burpee
    private static void pushUp(World world, BlockPos blockPos, ThermalSpringBlockEntity blockEntity) {
        boolean hasPlayerEntity = false;

        Vec3d blockVec3d = blockPos.toCenterPos();
        Vec3d offset = new Vec3d(0.5, 0.5, 0.5);
        List<Entity> entities = world.getOtherEntities(null, new Box(blockVec3d.subtract(offset), new Vec3d(blockVec3d.getX(), blockVec3d.getY() + 8, blockVec3d.getZ()).add(offset)));

        for (Entity entity : entities) {

            Vec3d currentMotion = entity.getVelocity();
            double acceleration = 0.2;
            double distanceFadeOff = (0.1 / 8) * (entity.getBlockPos().getY() - blockPos.getY());

            if (entity instanceof PlayerEntity playerEntity) {

                if (playerEntity.getAbilities().flying) return;

                if (!blockEntity.hasPlayer) world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, SoundCategory.BLOCKS, 1.0f, 1.0f);

                hasPlayerEntity = true;
                blockEntity.hasPlayer = true;

                if (entity.isSneaking()) acceleration = 0.035; distanceFadeOff = 0;
            }

            entity.setVelocity(currentMotion.x, Math.min(currentMotion.y + acceleration - distanceFadeOff, 1), currentMotion.z);
        }

        if (!hasPlayerEntity) blockEntity.hasPlayer = false;
    }


    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, ThermalSpringBlockEntity blockEntity) {
        emitParticles(world, blockPos, blockEntity);
    }
}
