package net.testworld;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

public class TestWorld {
  // defining our registry key. this key provides an Identifier for our preset, that we can use for our lang files and data elements.
  public static final ResourceKey<WorldPreset> VOID_WORLD = ResourceKey.create(Registries.WORLD_PRESET, new ResourceLocation("testworld", "testworld"));
  public static boolean isTestWorldSelected = false;
}
