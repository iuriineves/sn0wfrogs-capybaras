package iuriineves.neves_capybaras.entity.ai;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import iuriineves.neves_capybaras.NevesCapybaras;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.*;
import java.util.function.BiFunction;

public class StayAroundBlocks<E extends PathAwareEntity> extends MoveToWalkTarget<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryModuleState>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED), Pair.of(MemoryModuleType.PATH, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT));

    protected List<Block> BLOCKS = new ArrayList<>();
    protected BiFunction<E, PlayerEntity, Float> speedMod = (entity, temptingPlayer) -> 1f;
    protected Object2IntFunction<E> closeEnough = entity -> 10;
    protected Object2IntFunction<E> tooClose = entity -> 4;

    public StayAroundBlocks<E> addBlock(Block block) {
        BLOCKS.add(block);

        return this;
    }

    public StayAroundBlocks<E> speedMod(final BiFunction<E, PlayerEntity, Float> speedModifier) {
        this.speedMod = speedModifier;

        return this;
    }

    public StayAroundBlocks<E> setMaxDistance(Object2IntFunction<E> maxDistance) {
        this.closeEnough = maxDistance;

        return this;
    }

    public StayAroundBlocks<E> setMinDistance(Object2IntFunction<E> minDistance) {
        this.tooClose = minDistance;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    private void lookForBlocks(ServerWorld level, E entity) {
        int bigRadius = closeEnough.apply(entity);
        int smallRadius = tooClose.apply(entity);
        List<BlockPos> FOUND_BLOCKS = new LinkedList<>();

        BlockPos.iterateOutwards(entity.getBlockPos(), bigRadius, bigRadius, bigRadius).forEach(blockPos -> {
            Block block = level.getBlockState(blockPos).getBlock();

            if (BLOCKS.contains(block)) {
                FOUND_BLOCKS.add(blockPos);
            }
        });

        BLOCKS.forEach(block -> {
            FOUND_BLOCKS.forEach(blockPos -> {
                Block blockFound = level.getBlockState(blockPos).getBlock();
                if (!(blockFound == block)) return;
                if (!(entity.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) <= smallRadius)) {
                    path = entity.getNavigation().findPathTo(blockPos, 0);
                    BrainUtils.setMemory(entity.getBrain(), MemoryModuleType.PATH, path);
                } else {
                    return;
                }
                NevesCapybaras.LOGGER.info("BLOCK FOUND!!!");
                throw new RuntimeException("Block found.");
            });
        });
    }

    @Override
    protected boolean checkExtraStartConditions(ServerWorld level, E entity) {
        lookForBlocks(level, entity);
        return super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return checkExtraStartConditions((ServerWorld) entity.getWorld(), entity);
    }
}

