package icu.sunny.mc.watermelons.client.render.entity;

import icu.sunny.mc.watermelons.WatermelonsMod;
import icu.sunny.mc.watermelons.client.WatermelonsModClient;
import icu.sunny.mc.watermelons.client.render.entity.model.WatermimicEntityModel;
import icu.sunny.mc.watermelons.entity.mob.WatermimicEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WatermimicEntityRenderer extends MobEntityRenderer<WatermimicEntity, WatermimicEntityModel<WatermimicEntity>> {
    private static final Identifier TEXTURE = new Identifier(WatermelonsMod.MOD_ID, "textures/entity/watermimic/watermimic.png");

    public WatermimicEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WatermimicEntityModel<>(context.getPart(WatermelonsModClient.WATERMIMIC_ENTITY_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WatermimicEntity entity) {
        return TEXTURE;
    }
}
