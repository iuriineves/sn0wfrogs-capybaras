package iuriineves.neves_capybaras.entity.ai;


import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import iuriineves.neves_capybaras.NevesCapybaras;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * {@link ExtendedBehaviour ExtendedBehaviour} equivalent of vanilla's {@link net.minecraft.entity.ai.brain.task.TemptTask FollowTemptationFixed}.<br>
 * Has the entity follow a relevant temptation target (I.E. a player holding a tempting item).<br>
 * Will continue running for as long as the entity is being tempted.<br>
 * Defaults:
 * <ul>
 *     <li>Follows the temptation target indefinitely</li>
 *     <li>Will stop following if panicked or if it has an active breed target</li>
 *     <li>Will not follow a temptation target again for 5 seconds after stopping</li>
 *     <li>Considers 2.5 blocks 'close enough' for the purposes of following temptation</li>
 *     <li>1x speed modifier while following</li>
 * </ul>
 */
public class FollowTemptationFixed<E extends PathAwareEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryModuleState>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED), Pair.of(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.IS_TEMPTED, MemoryModuleState.REGISTERED), Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_PRESENT), Pair.of(MemoryModuleType.IS_PANICKING, MemoryModuleState.REGISTERED), Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.REGISTERED));

    protected BiFunction<E, PlayerEntity, Float> speedMod = (entity, temptingPlayer) -> 1f;
    protected BiPredicate<E, PlayerEntity> shouldFollow = (entity, temptingPlayer) -> (entity instanceof AnimalEntity) && !Boolean.TRUE.equals(BrainUtils.getMemory(entity, MemoryModuleType.IS_PANICKING));
    protected BiFunction<E, PlayerEntity, Float> closeEnoughWhen = (owner, temptingPlayer) -> 2.5f;
    protected Object2IntFunction<E> temptationCooldown = entity -> 100;

    public FollowTemptationFixed() {
        super();

        this.runFor(entity -> Integer.MAX_VALUE);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    /**
     * Set the movespeed modifier for the entity when following the tempting player.
     * @param speedModifier The movespeed modifier/multiplier
     * @return this
     */
    public FollowTemptationFixed<E> speedMod(final BiFunction<E, PlayerEntity, Float> speedModifier) {
        this.speedMod = speedModifier;

        return this;
    }

    /**
     * Determine whether the entity should follow the tempting player or not
     * @param predicate The temptation predicate
     * @return this
     */
    public FollowTemptationFixed<E> followIf(final BiPredicate<E, PlayerEntity> predicate) {
        this.shouldFollow = predicate;

        return this;
    }

    /**
     * Sets the amount (in blocks) that the mob can be considered 'close enough' to their temptation that they can stop pathfinding
     * @param closeEnoughMod The distance modifier
     * @return this
     */
    public FollowTemptationFixed<E> closeEnoughDist(final BiFunction<E, PlayerEntity, Float> closeEnoughMod) {
        this.closeEnoughWhen = closeEnoughMod;

        return this;
    }

    /**
     * Sets the length of time (in ticks) the entity should ignore temptation after having previously been tempted.<br>
     * NOTE: This could be ignored if the {@link net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowTemptation#followIf} predicate has been overriden
     * @param cooldownFunction The cooldown function
     * @return this
     */
    public FollowTemptationFixed<E> temptationCooldown(final Object2IntFunction<E> cooldownFunction) {
        this.temptationCooldown = cooldownFunction;

        return this;
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return BrainUtils.hasMemory(entity, MemoryModuleType.TEMPTING_PLAYER) &&
                !BrainUtils.hasMemory(entity, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS) &&
                !BrainUtils.hasMemory(entity, MemoryModuleType.BREED_TARGET) &&
                this.shouldFollow.test(entity, BrainUtils.getMemory(entity, MemoryModuleType.TEMPTING_PLAYER));
    }

    @Override
    protected void start(E entity) {
        BrainUtils.setMemory(entity, MemoryModuleType.IS_TEMPTED, true);
    }

    @Override
    protected void tick(E entity) {
        final PlayerEntity temptingPlayer = BrainUtils.getMemory(entity, MemoryModuleType.TEMPTING_PLAYER);
        final float closeEnough = this.closeEnoughWhen.apply(entity, temptingPlayer);

        BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(temptingPlayer, true));

        if (entity.squaredDistanceTo(temptingPlayer) < closeEnough * closeEnough) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        }
        else {
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(temptingPlayer, false), this.speedMod.apply(entity, temptingPlayer), (int)closeEnough));
        }
    }

    @Override
    protected void stop(E entity) {
        final int cooldownTicks = this.temptationCooldown.apply(entity);

        BrainUtils.setForgettableMemory(entity, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, cooldownTicks, cooldownTicks);
        BrainUtils.setMemory(entity, MemoryModuleType.IS_TEMPTED, false);
        BrainUtils.clearMemories(entity, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET);
    }
}
