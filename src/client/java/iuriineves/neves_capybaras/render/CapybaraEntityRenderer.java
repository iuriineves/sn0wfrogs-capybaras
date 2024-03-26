package iuriineves.neves_capybaras.render;

import iuriineves.neves_capybaras.entity.CapybaraEntity;
import iuriineves.neves_capybaras.model.CapybaraEntityModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Optional;

public class CapybaraEntityRenderer extends GeoEntityRenderer<CapybaraEntity> {
    public CapybaraEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CapybaraEntityModel());

        addRenderLayer(new CapybaraMandarinLayer(this));
        addRenderLayer(new CapybaraFedoraLayer(this));
    }

    @Override
    public void preRender(MatrixStack poseStack, CapybaraEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (animatable.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, packedLight, red, green, blue, alpha);
    }
}
