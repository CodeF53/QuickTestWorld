package net.testworld.mixins;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.biome.Biomes.THE_VOID;
import static net.testworld.TestWorld.VOID_WORLD;

@Mixin(WorldPresets.Bootstrap.class)
public abstract class WorldPresetMixin {

  @Shadow protected abstract void registerCustomOverworldPreset(ResourceKey<WorldPreset> resourceKey, LevelStem levelStem);

  @Shadow protected abstract LevelStem makeOverworld(ChunkGenerator chunkGenerator);

  @Shadow @Final private HolderGetter<Biome> biomes;
  @Shadow @Final private HolderGetter<PlacedFeature> placedFeatures;

  @Shadow @Final private HolderGetter<StructureSet> structureSets;

  @Inject(method = "run", at = @At("RETURN"))
  private void addPresets(CallbackInfo ci) {
    this.registerCustomOverworldPreset(VOID_WORLD, this.makeOverworld(
      new FlatLevelSource(new FlatLevelGeneratorSettings(
        Optional.of(HolderSet.direct(this.structureSets.getOrThrow(BuiltinStructureSets.STRONGHOLDS), this.structureSets.getOrThrow(BuiltinStructureSets.VILLAGES))),
        FlatLevelGeneratorSettings.getDefaultBiome(this.biomes),
        FlatLevelGeneratorSettings.createLakesList(this.placedFeatures))
      )));
  }
}