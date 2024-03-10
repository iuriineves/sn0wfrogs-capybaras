package iuriineves.neves_capybaras;

import iuriineves.neves_capybaras.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NevesCapybaras implements ModInitializer {
    public static final String MOD_ID = "neves_capybaras";
    public static final Logger LOGGER = LoggerFactory.getLogger("neves_capybaras");

	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.MANDARIN))
			.displayName(Text.translatable("itemGroup.neves_capybaras.capy_group"))
			.entries((context, entries) -> {
				ModItems.ITEMS.keySet().forEach(entries::add);
			})
			.build();


	@Override
	public void onInitialize() {

		ModEntities.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModSoundEvents.initialize();
		ModBlockEntities.initialize();
		ModStatusEffects.initialize();
		Registry.register(Registries.ITEM_GROUP, new Identifier("neves_capybaras", "capy_group"), ITEM_GROUP);

	}
}