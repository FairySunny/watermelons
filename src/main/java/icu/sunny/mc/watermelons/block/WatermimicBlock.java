package icu.sunny.mc.watermelons.block;

import icu.sunny.mc.watermelons.WatermelonsMod;
import icu.sunny.mc.watermelons.entity.mob.WatermimicEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;

public class WatermimicBlock extends Block {
    public WatermimicBlock(Settings settings) {
        super(settings);
    }

    private void spawnWatermimic(ServerWorld world, BlockPos pos) {
        WatermimicEntity watermimicEntity = WatermelonsMod.WATERMIMIC_ENTITY.create(world);
        if (watermimicEntity != null) {
            watermimicEntity.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(watermimicEntity);
            watermimicEntity.playSpawnEffects();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            this.spawnWatermimic(world, pos);
        }
    }
}
