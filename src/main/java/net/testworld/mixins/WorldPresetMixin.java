package net.testworld.mixins;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static net.testworld.TestWorld.VOID_WORLD;

@Mixin(WorldPresets.Bootstrap.class)
public abstract class WorldPresetMixin {
  @Shadow protected abstract Holder<WorldPreset> registerCustomOverworldPreset(ResourceKey<WorldPreset> key, LevelStem dimensionOptions);

  @Shadow protected abstract LevelStem makeOverworld(ChunkGenerator chunkGenerator);

  @Inject(method = "run", at = @At("RETURN"))
  private void addPresets(CallbackInfoReturnable<RegistryAccess.RegistryEntry<WorldPreset>> cir) {
    this.registerCustomOverworldPreset(VOID_WORLD, this.makeOverworld(
      new FlatLevelSource(null,
        new FlatLevelGeneratorSettings(Optional.empty(), BuiltinRegistries.BIOME)))
    );
  }
}