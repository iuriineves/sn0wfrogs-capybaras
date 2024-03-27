package sn0wfrog.sn0wfrogs_capybaras.entity;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.entity.ai.CapybaraWalk;
import sn0wfrog.sn0wfrogs_capybaras.entity.ai.FollowTemptationFixed;
import sn0wfrog.sn0wfrogs_capybaras.entity.ai.Loaf;
import sn0wfrog.sn0wfrogs_capybaras.init.ModEntities;
import sn0wfrog.sn0wfrogs_capybaras.init.ModItems;
import sn0wfrog.sn0wfrogs_capybaras.init.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.ItemTemptingSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.entity.EntityPose.SITTING;
import static net.minecraft.entity.EntityPose.STANDING;

public class CapybaraEntity extends AnimalEntity implements GeoEntity, SmartBrainOwner<CapybaraEntity> {

    public static final List<Type> NATURAL_TYPES = ImmutableList.of(Type.BROWN, Type.DARK, Type.RED);

    public static final TrackedData<Boolean> MANDARIN = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> TYPE = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<Long> LAST_POSE_TICK = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.LONG);
    public static final TrackedData<Boolean> STATIONARY = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> RIDING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    protected final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected final RawAnimation SIT_ANIM = RawAnimation.begin().thenPlay("lay");
    protected final RawAnimation SIT_IDLE_ANIM = RawAnimation.begin().thenLoop("lay_idle");
    protected final RawAnimation REVERSED_SIT_ANIM = RawAnimation.begin().thenPlay("lay_reversed");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public CapybaraEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

        setPathfindingPenalty(PathNodeType.WATER, 0.0f);

        MobNavigation mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
    }

    public static DefaultAttributeContainer.Builder createCapybaraAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {

        if (world.getRandom().nextInt(100) == 0) {
            this.setCapybaraType(Type.ALBINO);
        } else if (world.getRandom().nextInt(100) == 0) {
            CapybaraEntity capybaraEntity = this;

            for (int i = 0; i < world.getRandom().nextBetween(1, 4); i++) {
                CapybaraEntity lastCapybaraEntity = capybaraEntity;

                capybaraEntity = ModEntities.CAPYBARA.create(world.toServerWorld());

                if (capybaraEntity != null && capybaraEntity != this){
                    capybaraEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);

                    capybaraEntity.setCapybaraType(getRandomNaturalType(world.getRandom()));
                    capybaraEntity.startRiding(lastCapybaraEntity);
                    capybaraEntity.setRiding(true);
                }
            }
        } else {
            this.setCapybaraType(getRandomNaturalType(world.getRandom()));
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(MANDARIN, false);
        this.dataTracker.startTracking(TYPE, "");
        this.dataTracker.startTracking(LAST_POSE_TICK, 0L);
        this.dataTracker.startTracking(STATIONARY, true);
        this.dataTracker.startTracking(RIDING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("Mandarin", this.hasMandarin());
        nbt.putString("CapybaraType", this.getCapybaraType().toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains("Mandarin")) {
            this.setMandarin(nbt.getBoolean("Mandarin"));
        }

        if (nbt.contains("CapybaraType")) {
            this.setCapybaraType(Type.byName(nbt.getString("CapybaraType")));
        }
    }


    Type getRandomNaturalType(Random random) {
        return NATURAL_TYPES.get(random.nextInt(NATURAL_TYPES.size()));
    }

    public Identifier getCapybaraTexture() {
        return this.getCapybaraType().capybaraTexture;
    }

    public Type getCapybaraType() {
        return Type.byName(this.dataTracker.get(TYPE));
    }

    public void setCapybaraType(Type type) {
        this.dataTracker.set(TYPE, type.toString());
    }

    public void setMandarin(boolean mandarin) {
        this.dataTracker.set(MANDARIN, mandarin);
    }

    public boolean hasMandarin() {
        return this.dataTracker.get(MANDARIN);
    }

    public void setLastPoseTick(long lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }

    public long getLastPoseTick() {
        return this.dataTracker.get(LAST_POSE_TICK);
    }

    public boolean hasFedora() {
        if (this.getCustomName() != null) return Objects.equals(this.getCustomName().toString().toLowerCase(), "literal{agent c}");
        return false;
    }

    public boolean isTransitioningPoses() {
        return this.getWorld().getTime() - this.getLastPoseTick() < 20L;
    }

    public boolean isLoafing() {
        return this.getPose() == SITTING && !this.isTransitioningPoses();
    }

    public void startLoafing() {
        this.setPose(SITTING);
        this.setLastPoseTick(this.getWorld().getTime());
    }

    public void stopLoafing() {
        this.setPose(STANDING);
        this.setLastPoseTick(this.getWorld().getTime());
    }

    public boolean isStationary() {
        return this.dataTracker.get(STATIONARY);
    }

    public void setStationary(boolean stationary) {
        this.dataTracker.set(STATIONARY, stationary);
    }

    public boolean isRiding() {
        return this.dataTracker.get(RIDING);
    }

    public void setRiding(boolean riding) {
        this.dataTracker.set(RIDING, riding);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_CAPYBARA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_CAPYBARA_ATTACKED;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_CAPYBARA_ATTACKED;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    public float getMovementSpeed() {
        return super.getMovementSpeed();
    }

    @Override
    public double getSwimHeight() {
        return super.getSwimHeight();
    }

    @Override
    public void travel(Vec3d movementInput) {
        // increase speed if on water
        if (this.isTouchingWater()) {
            this.updateVelocity(0.1f, movementInput);
        }
        super.travel(movementInput);
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        if (isRiding()) {
            this.setRiding(false);
            this.stopRiding();
            return ActionResult.SUCCESS;
        }

        //mandarin on head logic
        if ((hasMandarin() && player.isSneaking())) {
            if (player.getWorld().isClient()) player.swingHand(Hand.MAIN_HAND);

            if (!player.getWorld().isClient()) {
                if (!player.isCreative())
                    player.getWorld().spawnEntity(new ItemEntity(player.getWorld(), this.getX(), this.getY() + 1, this.getZ(), new ItemStack(ModItems.MANDARIN)));
                setMandarin(false);
            }
            return ActionResult.CONSUME;
        } else if (!hasMandarin() && !this.isBaby()){
            if (hand == Hand.MAIN_HAND && Objects.equals(player.getMainHandStack().getItem(), ModItems.MANDARIN) && !player.isSneaking()) {
                if (player.getWorld().isClient()) player.swingHand(Hand.MAIN_HAND);

                if (!player.getWorld().isClient()) {
                    if (!player.isCreative()) player.getMainHandStack().decrement(1);
                    setMandarin(true);
                }
                return ActionResult.CONSUME;

            }
        }

        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.changing(getWidth(), getHeight());
    }

    private <E extends CapybaraEntity>PlayState predicate(final AnimationState<E> event) {
        event.getController().setAnimationSpeed(1);

        if (this.isTransitioningPoses()){
            if (this.getPose() == SITTING) {
                return event.setAndContinue(SIT_ANIM);
            } else {
                return event.setAndContinue(REVERSED_SIT_ANIM);
            }
        }

        if (this.isLoafing()) {
            return event.setAndContinue(SIT_IDLE_ANIM);
        }

        if (event.isMoving()) {
            event.getController().setAnimationSpeed(1.5);

            return event.setAndContinue(WALK_ANIM);
        }

        return event.setAndContinue(IDLE_ANIM);
    }

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            controllers.add(new AnimationController<>(this, "Walking", 0, this::predicate));
        }

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return this.geoCache;
        }

        @Nullable
        @Override
        public CapybaraEntity createChild(ServerWorld world, PassiveEntity entity) {
            CapybaraEntity capybaraEntity = ModEntities.CAPYBARA.create(world);
            assert capybaraEntity != null;
            capybaraEntity.setCapybaraType(this.getCapybaraType());
            return capybaraEntity;
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return stack.getItem() == getTemptItem();
        }

        protected Item getTemptItem() {
            return Items.MELON_SLICE;
        }

        @Override
        protected Brain.Profile<?> createBrainProfile() {
            return new SmartBrainProvider<>(this);
        }

        @Override
        protected void mobTick() {
            tickBrain(this);
        }

        @Override
        public List<? extends ExtendedSensor<CapybaraEntity>> getSensors() {
            return ObjectArrayList.of(
                    new NearbyLivingEntitySensor<>(),
                    new HurtBySensor<>(),
                    new ItemTemptingSensor<CapybaraEntity>()
                            .temptedWith((entity, stack) -> Ingredient.ofItems(getTemptItem()).test(stack))
            );
        }

        @Override
        public BrainActivityGroup<CapybaraEntity> getCoreTasks() {
            return BrainActivityGroup.coreTasks(
                    new FloatToSurfaceOfFluid<>(),
                    new LookAtTarget<>(),
                    new CapybaraWalk<>()
            );
        }

        @Override
        public BrainActivityGroup<CapybaraEntity> getIdleTasks() {
            return BrainActivityGroup.idleTasks(
                    new FirstApplicableBehaviour<>(
                            new FollowParent<>(),
                            new FollowTemptationFixed<>()
                                    .speedMod((entity, playerEntity) -> 1.5f),
                            new BreedWithPartner<>(),
                            new SetPlayerLookTarget<>(),
                            new SetRandomLookTarget<>()
                    ),
                    new OneRandomBehaviour<>(
                            new Loaf<>()
                                    .setChance(entity -> 0.6f)
                                    .runFor(entity -> entity.getRandom().nextBetween(200, 400))
                                    .cooldownFor(entity -> entity.getRandom().nextBetween(200, 400)),
                            new SetRandomWalkTarget<>(),
                            new Idle<>().runFor(entity -> entity.getRandom().nextBetween(30, 60))
                    )
            );
        }

        @Override
        public Map<Activity, BrainActivityGroup<? extends CapybaraEntity>> getAdditionalTasks() {
            return Map.of(Activity.PANIC, new BrainActivityGroup<CapybaraEntity>(Activity.PANIC)
                    .behaviours(new Panic<>()
                            .setRadius(15, 10)
                            .speedMod(entity -> 1.5f))
                    .requireAndWipeMemoriesOnUse(MemoryModuleType.HURT_BY_ENTITY));
        }

        @Override
        public List<Activity> getActivityPriorities() {
            return ObjectArrayList.of(Activity.FIGHT, Activity.PANIC, Activity.IDLE);
        }

        public enum Type {
            RED(new Identifier(Sn0wfrogsCapybaras.MOD_ID, "textures/entity/red_capybara.png")),
            ALBINO(new Identifier(Sn0wfrogsCapybaras.MOD_ID, "textures/entity/albino_capybara.png")),
            BROWN(new Identifier(Sn0wfrogsCapybaras.MOD_ID, "textures/entity/brown_capybara.png")),
            DARK(new Identifier(Sn0wfrogsCapybaras.MOD_ID, "textures/entity/dark_capybara.png"));

            public final Identifier capybaraTexture;
            Type(Identifier capybaraTexture) {
                this.capybaraTexture = capybaraTexture;
            }

            public static Type byName(String name) {
                for (Type type : values()) {
                    if (type.name().equals(name)) {
                        return type;
                    }
                }
                return Type.RED;
            }
        }
    }
