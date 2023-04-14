package icu.sunny.mc.watermelons.mixin;

import icu.sunny.mc.watermelons.block.PicnicBasketBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "findRespawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;FBB)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void findRespawnPosition(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir) {
        if (world.getBlockState(pos).getBlock() instanceof PicnicBasketBlock) {
            cir.setReturnValue(RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos));
        }
    }
}
