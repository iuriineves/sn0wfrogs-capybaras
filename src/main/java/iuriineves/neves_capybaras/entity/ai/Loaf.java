package iuriineves.neves_capybaras.entity.ai;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import iuriineves.neves_capybaras.entity.CapybaraEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class Loaf<E extends CapybaraEntity> extends ExtendedBehaviour<E> {

    protected Object2FloatFunction<E> chance = entity -> 1F;

    private static final List<Pair<MemoryModuleType<?>, MemoryModuleState>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.IS_TEMPTED, MemoryModuleState.REGISTERED));

    public Loaf<E> setChance(final Object2FloatFunction<E> chance) {
        this.chance = chance;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean shouldRun(ServerWorld level, E entity) {
        return shouldKeepRunning(entity) && this.chance.apply(entity) >= entity.getRandom().nextFloat();
    }

    @Override
    protected void start(E entity) {
        if (!entity.isLoafing()) {
            entity.startLoafing();
        }
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return !Boolean.TRUE.equals(BrainUtils.getMemory(entity, MemoryModuleType.IS_PANICKING)) && entity.isStationary() && !Boolean.TRUE.equals(BrainUtils.getMemory(entity, MemoryModuleType.IS_TEMPTED));
    }

    @Override
    protected void stop(E entity) {
        entity.stopLoafing();
    }
}
