package sn0wfrog.sn0wfrogs_capybaras;

import sn0wfrog.sn0wfrogs_capybaras.init.ModBlocks;
import sn0wfrog.sn0wfrogs_capybaras.init.ModEntities;
import sn0wfrog.sn0wfrogs_capybaras.render.CapybaraEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.render.RenderLayer;

public class Sn0wfrogsCapybarasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.CAPYBARA, CapybaraEntityRenderer::new);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_BUSH_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_MANDARIN_BUSH_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_LEAVES_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_MANDARIN_LEAVES_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_FLOWERS, RenderLayer.getCutout());

		ParticleFactoryRegistry.getInstance().register(Sn0wfrogsCapybaras.WATER_VAPOR, CampfireSmokeParticle.CosySmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(Sn0wfrogsCapybaras.GEYSER, CloudParticle.CloudFactory::new);
	}
}