package iuriineves.neves_capybaras;

import iuriineves.neves_capybaras.init.ModBlocks;
import iuriineves.neves_capybaras.init.ModEntities;
import iuriineves.neves_capybaras.render.CapybaraEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class NevesCapybarasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.CAPYBARA, CapybaraEntityRenderer::new);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_BUSH_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_LEAVES_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANDARIN_LEAVES_WITH_FLOWER_BLOCK, RenderLayer.getCutout());
	}
}