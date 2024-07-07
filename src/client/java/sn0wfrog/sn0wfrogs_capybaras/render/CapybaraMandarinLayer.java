package sn0wfrog.sn0wfrogs_capybaras.render;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.entity.CapybaraEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

import static sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras.LOGGER;  // Kept for Debug statements that have been commented.

public class CapybaraMandarinLayer extends GeoRenderLayer<CapybaraEntity> {
    private static final Identifier TEXTURE = Identifier.of(Sn0wfrogsCapybaras.MOD_ID, "textures/entity/mandarin.png");

    public CapybaraMandarinLayer(GeoRenderer<CapybaraEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack poseStack,
                       CapybaraEntity animatable,
                       BakedGeoModel bakedModel,
                       RenderLayer renderType,
                       VertexConsumerProvider bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay)
    {
        if (animatable.hasMandarin() && !animatable.hasFedora()) {
            RenderLayer armorRenderType = RenderLayer.getArmorCutoutNoCull(TEXTURE);

            //LOGGER.info("Accessing Mandarin Layer");

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                    bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV,
                    Color.WHITE.argbInt());
        }
    }
}
