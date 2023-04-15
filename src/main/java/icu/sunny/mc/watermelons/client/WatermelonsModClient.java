package icu.sunny.mc.watermelons.client;

import icu.sunny.mc.watermelons.WatermelonsMod;
import icu.sunny.mc.watermelons.client.render.entity.WatermimicEntityRenderer;
import icu.sunny.mc.watermelons.client.render.entity.model.WatermimicEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WatermelonsModClient implements ClientModInitializer {
    static {
        EntityRendererRegistry.register(WatermelonsMod.WATERMIMIC_ENTITY, WatermimicEntityRenderer::new);
    }

    public static EntityModelLayer WATERMIMIC_ENTITY_MODEL_LAYER = registerEntityModelLayer(
            "watermimic",
            WatermimicEntityModel::getTexturedModelData
    );

    private static EntityModelLayer registerEntityModelLayer(String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(new Identifier(WatermelonsMod.MOD_ID, name), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        return layer;
    }

    @Override
    public void onInitializeClient() {
        WatermelonsMod.LOGGER.info("WatermelonsModClient.onInitializeClient begin");

        WatermelonsMod.LOGGER.info("WatermelonsModClient.onInitializeClient end");
    }
}
