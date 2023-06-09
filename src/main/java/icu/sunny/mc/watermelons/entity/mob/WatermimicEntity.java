package icu.sunny.mc.watermelons.entity.mob;

import icu.sunny.mc.watermelons.WatermelonsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class WatermimicEntity extends HostileEntity {
    public WatermimicEntity(EntityType<? extends WatermimicEntity> entityType, World world) {
        super(entityType, world);
    }

    private BlockPos getBlockizedPos() {
        return BlockPos.ofFloored(getX(), getY() + 0.5, getZ());
    }

    private boolean canBlockizeAt(BlockPos pos) {
        if (!world.getBlockState(pos).isAir()) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos.down());
        return !blockState.isAir() && blockState.getFluidState().isEmpty();
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(1, new PowderSnowJumpGoal(this, this.world));
        goalSelector.add(3, new BlockizeGoal());
        goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    private class BlockizeGoal extends Goal {
        @Nullable
        private Long startTime;

        BlockizeGoal() {
            setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (startTime == null) {
                startTime = world.getTime();
                return false;
            }
            return world.getTime() - startTime >= 40L &&
                    world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) &&
                    getTarget() == null &&
                    getNavigation().isIdle() &&
                    canBlockizeAt(getBlockizedPos());
        }

        @Override
        public void start() {
            BlockPos pos = getBlockizedPos();
            if (canBlockizeAt(pos)) {
                world.setBlockState(pos, WatermelonsMod.WATERMIMIC_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
                playSpawnEffects();
                discard();
            }
        }
    }
}
