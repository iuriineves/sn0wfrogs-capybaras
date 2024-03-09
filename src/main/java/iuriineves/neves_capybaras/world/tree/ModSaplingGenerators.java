package iuriineves.neves_capybaras.world.tree;

import iuriineves.neves_capybaras.NevesCapybaras;
import iuriineves.neves_capybaras.NevesCapybarasDataGenerator;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator MANDARIN_BUSH =
            new SaplingGenerator("mandarin_bush", 0f,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(NevesCapybaras.MOD_ID, "mandarin_bush"))),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
}