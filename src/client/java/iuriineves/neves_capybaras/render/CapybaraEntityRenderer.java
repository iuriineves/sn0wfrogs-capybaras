package iuriineves.neves_capybaras.render;

import iuriineves.neves_capybaras.entity.CapybaraEntity;
import iuriineves.neves_capybaras.model.CapybaraEntityModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Optional;

public class CapybaraEntityRenderer extends GeoEntityRenderer<CapybaraEntity> {
    public CapybaraEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CapybaraEntityModel());
    }

    @Override
    public void preRender(MatrixStack poseStack, CapybaraEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (animatable.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);

            Optional<GeoBone> head = model.getBone("head");
            head.ifPresent(geoBone -> {
                geoBone.setScaleX(1.5f);
                geoBone.setScaleY(1.5f);
                geoBone.setScaleZ(1.5f);
            });
        } else {
            poseStack.scale(1f, 1f, 1f);

            Optional<GeoBone> head = model.getBone("head");
            head.ifPresent(geoBone -> {
                geoBone.setScaleX(1f);
                geoBone.setScaleY(1f);
                geoBone.setScaleZ(1f);
            });
        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, packedLight, red, green, blue, alpha);
    }
}
