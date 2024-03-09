package iuriineves.neves_capybaras.init;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.block.ThermalSpringBlock;
import iuriineves.neves_capybaras.world.tree.ModSaplingGenerators;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlocks {
    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();

    ThermalSpringBlock THERMAL_SPRING_BLOCK = createBlock("thermal_spring", new ThermalSpringBlock(FabricBlockSettings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.PACKED_MUD)), true);
    LeavesBlock MANDARIN_LEAVES_BLOCK = createBlock("mandarin_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.AZALEA_LEAVES).nonOpaque()), true);
    LeavesBlock MANDARIN_LEAVES_WITH_FLOWER_BLOCK = createBlock("mandarin_leaves_with_flower", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.FLOWERING_AZALEA_LEAVES).nonOpaque()), true);

    SaplingBlock MANDARIN_BUSH_SAPLING = createBlock("mandarin_bush_sapling", new SaplingBlock(ModSaplingGenerators.MANDARIN_BUSH, FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).nonOpaque()), true);

    private static <T extends Block> T createBlock(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(NevesCapybaras.MOD_ID, name));
        if (createItem) {
            ModItems.ITEMS.put(new BlockItem(block, new FabricItemSettings()), new Identifier(NevesCapybaras.MOD_ID, name));
        }
        return block;
    }

    static void initialize() {
        BLOCKS.keySet().forEach(block -> {
            Registry.register(Registries.BLOCK, BLOCKS.get(block), block);
        });
    }
}
