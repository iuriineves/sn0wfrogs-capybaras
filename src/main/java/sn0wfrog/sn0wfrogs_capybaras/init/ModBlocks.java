package sn0wfrog.sn0wfrogs_capybaras.init;

import net.minecraft.item.Item;
import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import sn0wfrog.sn0wfrogs_capybaras.block.ThermalSpringBlock;
import sn0wfrog.sn0wfrogs_capybaras.world.tree.ModSaplingGenerators;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
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
    LeavesBlock FLOWERING_MANDARIN_LEAVES_BLOCK = createBlock("flowering_mandarin_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.FLOWERING_AZALEA_LEAVES).nonOpaque()), true);

    SaplingBlock MANDARIN_BUSH_SAPLING = createBlock("mandarin_bush_sapling", new SaplingBlock(ModSaplingGenerators.MANDARIN_BUSH, FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).nonOpaque()), true);
    FlowerPotBlock POTTED_MANDARIN_BUSH_SAPLING = createBlock("potted_mandarin_bush_sapling", new FlowerPotBlock(MANDARIN_BUSH_SAPLING, FabricBlockSettings.copyOf(Blocks.POTTED_OAK_SAPLING).nonOpaque()), false);

    GlowLichenBlock MANDARIN_FLOWERS = createBlock("mandarin_flowers", new GlowLichenBlock(FabricBlockSettings.create().luminance(0).sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque()), true);

    private static <T extends Block> T createBlock(String name, T block, boolean createItem) {
        BLOCKS.put(block, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name));
        if (createItem) {
            ModItems.ITEMS.put(new BlockItem(block, new Item.Settings()), Identifier.of(Sn0wfrogsCapybaras.MOD_ID, name));
        }
        return block;
    }

    static void initialize() {
        BLOCKS.keySet().forEach(block -> {
            Registry.register(Registries.BLOCK, BLOCKS.get(block), block);
        });
    }
}
