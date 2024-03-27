package sn0wfrog.sn0wfrogs_capybaras.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import sn0wfrog.sn0wfrogs_capybaras.block_entity.ThermalSpringBlockEntity;
import sn0wfrog.sn0wfrogs_capybaras.init.ModBlockEntities;
import sn0wfrog.sn0wfrogs_capybaras.init.ModBlocks;
import sn0wfrog.sn0wfrogs_capybaras.init.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThermalSpringBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final MapCodec<ThermalSpringBlock> CODEC = ThermalSpringBlock.createCodec(ThermalSpringBlock::new);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    public ThermalSpringBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ThermalSpringBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.THERMAL_SPRING_BLOCK_ENTITY, ThermalSpringBlockEntity::tick);

    }
}
