package sn0wfrog.sn0wfrogs_capybaras.entity.ai;

import sn0wfrog.sn0wfrogs_capybaras.entity.CapybaraEntity;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;

public class CapybaraWalk<E extends CapybaraEntity> extends MoveToWalkTarget<E> {

    @Override
    protected void start(E entity) {
        super.start(entity);

        entity.setStationary(false);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerWorld level, E entity) {
        return super.checkExtraStartConditions(level, entity) && !entity.isLoafing() && !entity.isTransitioningPoses();
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);

        entity.setStationary(true);
    }
}
