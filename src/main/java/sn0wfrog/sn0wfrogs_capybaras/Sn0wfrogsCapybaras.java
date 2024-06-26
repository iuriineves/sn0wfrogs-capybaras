package sn0wfrog.sn0wfrogs_capybaras;

import sn0wfrog.sn0wfrogs_capybaras.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sn0wfrogsCapybaras implements ModInitializer {
    public static final String MOD_ID = "sn0wfrogs_capybaras";
    public static final Logger LOGGER = LoggerFactory.getLogger("sn0wfrogs_capybaras");

	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.MANDARIN))
			.displayName(Text.translatable("itemGroup.sn0wfrogs_capybaras.capy_group"))
			.entries((context, entries) -> {
				ModItems.ITEMS.keySet().forEach(entries::add);
			})
			.build();


	public static final SimpleParticleType WATER_VAPOR = FabricParticleTypes.simple();
	public static final SimpleParticleType GEYSER = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {

		ModEntities.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModSoundEvents.initialize();
		ModBlockEntities.initialize();
		ModStatusEffects.initialize();

		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "water_vapor"), WATER_VAPOR);
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "geyser"), GEYSER);

		Registry.register(Registries.ITEM_GROUP, Identifier.of("sn0wfrogs_capybaras", "capy_group"), ITEM_GROUP);

	}
}