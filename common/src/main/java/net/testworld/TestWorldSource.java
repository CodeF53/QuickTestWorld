package net.testworld;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class TestWorldSource extends FlatLevelSource {
    public final boolean ImRealYouCunt = true;
    public TestWorldSource(Registry<StructureSet> registry, FlatLevelGeneratorSettings flatLevelGeneratorSettings) {
        super(registry, flatLevelGeneratorSettings);
    }
}
