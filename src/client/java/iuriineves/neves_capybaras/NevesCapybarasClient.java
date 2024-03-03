package iuriineves.neves_capybaras;

import iuriineves.neves_capybaras.init.ModEntities;
import iuriineves.neves_capybaras.render.CapybaraEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class NevesCapybarasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.CAPYBARA, CapybaraEntityRenderer::new);

	}
}