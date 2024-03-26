package iuriineves.neves_capybaras.render;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.entity.CapybaraEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Locale;
import java.util.Objects;

public class CapybaraFedoraLayer extends GeoRenderLayer<CapybaraEntity> {
    private static final Identifier TEXTURE = new Identifier(NevesCapybaras.MOD_ID, "textures/entity/fedora.png");

    public CapybaraFedoraLayer(GeoRenderer<CapybaraEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack poseStack, CapybaraEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.hasFedora()) {
            RenderLayer armorRenderType = RenderLayer.getArmorCutoutNoCull(TEXTURE);

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                    bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV,
                    1, 1, 1, 1);
        }
    }
}
