package icu.sunny.mc.watermelons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

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
}
