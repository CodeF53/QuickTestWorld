package net.testworld;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

public class TestWorld {
  public static final String MOD_ID = "testworld";

  // defining our registry key. this key provides an Identifier for our preset, that we can use for our lang files and data elements.
  public static final ResourceKey<WorldPreset> VOID_WORLD = ResourceKey.create(Registry.WORLD_PRESET_REGISTRY, new ResourceLocation("testworld", "testworld"));
  public static boolean isTestWorldSelected = false;
}
