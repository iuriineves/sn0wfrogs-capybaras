package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlocks {
    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();

    Block THERMAL_SPRING_BLOCK = createBlock("thermal_spring", new Block(FabricBlockSettings.create().strength(4.0f)), true);

    private static <T extends Block> T createBlock(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(NevesCapybaras.MOD_ID, name));
        if (createItem) {
            Registry.register(Registries.ITEM, BLOCKS.get(block), new BlockItem(block, new FabricItemSettings()));
        }
        return block;
    }

    static void initialize() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
    }
}
