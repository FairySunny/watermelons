package icu.sunny.mc.watermelons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PicnicBasketBlock extends HorizontalFacingBlock {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            createCuboidShape(0.0, 0.0, 1.0, 16.0, 10.0, 15.0),
            createCuboidShape(1.0, 0.0, 0.0, 15.0, 10.0, 16.0),
            createCuboidShape(0.0, 0.0, 1.0, 16.0, 10.0, 15.0),
            createCuboidShape(1.0, 0.0, 0.0, 15.0, 10.0, 16.0)
    };

    public PicnicBasketBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(FACING).getHorizontal()];
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        if (serverPlayer.getSpawnPointDimension() == world.getRegistryKey() && pos.equals(serverPlayer.getSpawnPointPosition())) {
            return ActionResult.CONSUME;
        }
        serverPlayer.setSpawnPoint(world.getRegistryKey(), pos, 0.0f, false, true);
        return ActionResult.SUCCESS;
    }
}
