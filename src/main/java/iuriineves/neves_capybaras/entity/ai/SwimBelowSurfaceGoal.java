package iuriineves.neves_capybaras.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.FluidTags;

public class SwimBelowSurfaceGoal extends Goal {

    private final MobEntity mob;

    public SwimBelowSurfaceGoal(MobEntity mob) {
        this.mob = mob;
    }
    @Override
    public boolean canStart() {
        final Block block = this.mob.getBlockStateAtPos().getBlock();
        final Block blockBellow = this.mob.getWorld().getBlockState(this.mob.getBlockPos().down()).getBlock();
        final Block blockUp = this.mob.getWorld().getBlockState(this.mob.getBlockPos().up()).getBlock();

        return this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getSwimHeight() && this.mob.isTouchingWater() && (block == Blocks.WATER && (blockBellow == Blocks.WATER || blockUp == Blocks.WATER));
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextFloat() < 0.8f) {
            this.mob.getJumpControl().setActive();
        }
    }
}
