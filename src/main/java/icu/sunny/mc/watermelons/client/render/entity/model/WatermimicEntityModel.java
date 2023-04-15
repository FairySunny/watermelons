package icu.sunny.mc.watermelons.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;

public class WatermimicEntityModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart root;

    public WatermimicEntityModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                EntityModelPartNames.CUBE,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-8.0f, 8.0f, -8.0f, 16.0f, 16.0f, 16.0f),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f)
        );
        return TexturedModelData.of(modelData, 64, 32);
    }
}
