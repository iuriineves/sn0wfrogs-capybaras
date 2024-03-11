package iuriineves.neves_capybaras.world.tree;

import iuriineves.neves_capybaras.NevesCapybaras;
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
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(NevesCapybaras.MOD_ID, "mandarin_bush"));
    }
}
