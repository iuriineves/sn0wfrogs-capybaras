package sn0wfrog.sn0wfrogs_capybaras.world.tree;

import sn0wfrog.sn0wfrogs_capybaras.Sn0wfrogsCapybaras;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator MANDARIN_BUSH =
            new SaplingGenerator("mandarin_bush", 0f,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Sn0wfrogsCapybaras.MOD_ID, "mandarin_bush"))),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
}