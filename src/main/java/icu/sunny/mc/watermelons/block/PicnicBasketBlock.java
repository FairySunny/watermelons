package icu.sunny.mc.watermelons.block;

import icu.sunny.mc.watermelons.WatermelonsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class PicnicBasketBlock extends HorizontalFacingBlock {
    private static final BooleanProperty FOOD = BooleanProperty.of("food");

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            createCuboidShape(0.0, 0.0, 1.0, 16.0, 10.0, 15.0),
            createCuboidShape(1.0, 0.0, 0.0, 15.0, 10.0, 16.0),
            createCuboidShape(0.0, 0.0, 1.0, 16.0, 10.0, 15.0),
            createCuboidShape(1.0, 0.0, 0.0, 15.0, 10.0, 16.0)
    };

    public PicnicBasketBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(FOOD, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FOOD);
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
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(WatermelonsMod.MOD_ID, "picnic_basket_food"))) && !state.get(FOOD)) {
            BlockState newState = state.with(FOOD, true);
            world.setBlockState(pos, newState, NOTIFY_ALL);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));
            world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }

        if (world.isClient) {
            return ActionResult.CONSUME;
        }
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        boolean ok = false;
        if (state.get(FOOD)) {
            serverPlayer.clearStatusEffects();
            serverPlayer.setHealth(serverPlayer.getMaxHealth());
            serverPlayer.getHungerManager().setFoodLevel(20);
            serverPlayer.getHungerManager().setSaturationLevel(20.0f);
            ok = true;
        }
        if (!(serverPlayer.getSpawnPointDimension() == world.getRegistryKey() && pos.equals(serverPlayer.getSpawnPointPosition()))) {
            serverPlayer.setSpawnPoint(world.getRegistryKey(), pos, 0.0f, false, true);
            ok = true;
        }
        return ActionResult.success(ok);
    }
}
