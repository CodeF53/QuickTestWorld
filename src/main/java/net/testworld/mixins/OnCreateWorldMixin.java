package net.testworld.mixins;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.testworld.TestWorld.VOID_WORLD;
import static net.testworld.TestWorld.isTestWorldSelected;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Key;
import org.spongepowered.asm.mixin.transformer.meta.MixinInner;

@Mixin(CreateWorldScreen.class)
public abstract class OnCreateWorldMixin {
  @Redirect(method = "onCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;Z)V"))
  void changeSettings(Minecraft minecraft, CreateWorldScreen createWorldScreen, Lifecycle lifecycle, Runnable runnable, boolean bl) {
    Holder<WorldPreset> worldType = createWorldScreen.getUiState().getWorldType().preset();
    if (worldType != null && worldType.is(VOID_WORLD)) {
      // Internals
      // - Gamemode Creative
      createWorldScreen.getUiState().setGameMode(WorldCreationUiState.SelectedGameMode.CREATIVE);
      // - Cheats On
      createWorldScreen.getUiState().setAllowCheats(true);
      // - Generate Structures Off
      createWorldScreen.getUiState().setGenerateStructures(false);

      // Game Rules
      GameRules gameRules = createWorldScreen.getUiState().getGameRules();
      // - daylight cycle off
      setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doDaylightCycle", GameRules.Category.UPDATES), false);
      // - weather cycle off
      setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doWeatherCycle", GameRules.Category.UPDATES), false);
      // - wandering trader off
      setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doTraderSpawning", GameRules.Category.SPAWNING), false);
      // - pillager patrols off
      setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doPatrolSpawning", GameRules.Category.SPAWNING), false);
      // - phantom spawning off
      setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doInsomnia", GameRules.Category.SPAWNING), false);
      isTestWorldSelected = true;
    } else {
      isTestWorldSelected = false;
    }
    WorldOpenFlows.confirmWorldCreation(minecraft, createWorldScreen, lifecycle, runnable, bl);
  }

  private static <T extends GameRules.Value<T>> void setGameRuleBool(GameRules gameRules, GameRules.Key<T> key, Boolean val) {
    T value = gameRules.getRule(key);
    Map<String, ParsedArgument<CommandSourceStack, ?>> arguments = new LinkedHashMap<>();
    arguments.put("value", new ParsedArgument<>(0, 0, val));
    ((GameRules.Value) value).updateFromArgument(new CommandContext<>(null, null, arguments, null, null, null, null, null, null, false), "value");
  }

  private static <T extends GameRules.Value<T>> void setGameRuleInt(GameRules gameRules, GameRules.Key<T> key, int val) {
    T value = gameRules.getRule(key);
    Map<String, ParsedArgument<CommandSourceStack, ?>> arguments = new LinkedHashMap<>();
    arguments.put("value", new ParsedArgument<>(0, 0, val));
    ((GameRules.Value) value).updateFromArgument(new CommandContext<>(null, null, arguments, null, null, null, null, null, null, false), "value");
  }
}
