package net.testworld.mixins;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.testworld.TestWorld.isTestWorldSelected;
import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(CreateWorldScreen.class)
public abstract class OnCreateWorldMixin {

    @Redirect(method = "onCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;)V"))
    void changeSettings(Minecraft minecraft, CreateWorldScreen createWorldScreen, Lifecycle lifecycle, Runnable runnable) {
        if (isTestWorldSelected) {
            // Internals
            // - Hardcore Off
            createWorldScreen.hardCore = false;
            // - Gamemode Creative
            createWorldScreen.gameMode = CreateWorldScreen.SelectedGameMode.CREATIVE;
            // - Cheats On
            createWorldScreen.commands = true;
            // - Structures Off
            if (createWorldScreen.worldGenSettingsComponent.settings().worldGenSettings().generateStructures())
                createWorldScreen.worldGenSettingsComponent.updateSettings(WorldGenSettings::withStructuresToggled);

            // Game Rules
            GameRules gameRules = createWorldScreen.gameRules;
            // - daylight cycle off
            setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doDaylightCycle", GameRules.Category.UPDATES), false);
            // - weather cycle off
            setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doWeatherCycle", GameRules.Category.UPDATES), false);
            // - random tick speed 0
            setGameRuleInt(gameRules, new Key<GameRules.BooleanValue>("randomTickSpeed", GameRules.Category.UPDATES), 0);
            // - wandering trader off
            setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doTraderSpawning", GameRules.Category.SPAWNING), false);
            // - pillager patrols off
            setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doPatrolSpawning", GameRules.Category.SPAWNING), false);
            // - phantom spawning off
            setGameRuleBool(gameRules, new Key<GameRules.BooleanValue>("doInsomnia", GameRules.Category.SPAWNING), false);
        }
        WorldOpenFlows.confirmWorldCreation(Minecraft.getInstance(), createWorldScreen, createWorldScreen.worldGenSettingsComponent.settings().worldSettingsStability(), createWorldScreen::createNewWorld);
    }

    private static<T extends GameRules.Value<T>> void setGameRuleBool(GameRules gameRules, GameRules.Key<T> key, Boolean val) {
        T value = gameRules.getRule(key);
        Map<String, ParsedArgument<CommandSourceStack, ?>> arguments = new LinkedHashMap<>();
        arguments.put("value", new ParsedArgument<>(0, 0, val));
        ((GameRules.Value)value).updateFromArgument(new CommandContext<>(null, null, arguments, null, null, null, null, null, null, false), "value");
    }
    private static<T extends GameRules.Value<T>> void setGameRuleInt(GameRules gameRules, GameRules.Key<T> key, int val) {
        T value = gameRules.getRule(key);
        Map<String, ParsedArgument<CommandSourceStack, ?>> arguments = new LinkedHashMap<>();
        arguments.put("value", new ParsedArgument<>(0, 0, val));
        ((GameRules.Value)value).updateFromArgument(new CommandContext<>(null, null, arguments, null, null, null, null, null, null, false), "value");
    }
}
