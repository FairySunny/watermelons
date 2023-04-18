package icu.sunny.mc.watermelons.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WatermimicEntityModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart top;

    public WatermimicEntityModel(ModelPart root) {
        this.root = root;
        this.top = root.getChild("top");
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        top.pitch = MathHelper.cos(limbAngle * 0.2f) * limbDistance - 0.8f;
    }

    private static ModelPartBuilder withTeeth(ModelPartBuilder builder, float offsetY) {
        builder.uv(0, 16);
        for (int i = 0; i < 4; i++) {
            builder.cuboid(-7.0f + 4.0f * i, offsetY, -16.0f, 2.0f, 2.0f, 2.0f);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                builder.cuboid(-8.0f + 14.0f * i, offsetY, -12.0f + 4.0f * j, 2.0f, 2.0f, 2.0f);
            }
        }
        return builder;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                "top",
                withTeeth(
                        ModelPartBuilder.create()
                                .uv(0, 0)
                                .cuboid(-8.0f, -8.0f, -16.0f, 16.0f, 8.0f, 16.0f),
                        0.0f
                ),
                ModelTransform.pivot(0.0f, 16.0f, 8.0f)
        );
        modelPartData.addChild(
                "bottom",
                withTeeth(
                        ModelPartBuilder.create()
                                .uv(0, 24)
                                .cuboid(-8.0f, 16.0f, -16.0f, 16.0f, 8.0f, 16.0f),
                        14.0f
                ),
                ModelTransform.pivot(0.0f, 0.0f, 8.0f)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }
}
