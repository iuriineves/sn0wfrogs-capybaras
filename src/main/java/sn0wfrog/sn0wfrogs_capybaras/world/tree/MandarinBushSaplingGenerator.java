package sn0wfrog.sn0wfrogs_capybaras.world.tree;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class MandarinBushSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Sn0wfrogsCapybaras.MOD_ID, "mandarin_bush"));
    }
}