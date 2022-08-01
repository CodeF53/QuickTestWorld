package net.testworld.mixins;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.testworld.TestWorldSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreateWorldScreen.class)
public abstract class OnCreateWorldMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("modid");
    @Redirect(method = "onCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;)V"))
    void changeSettings(Minecraft minecraft, CreateWorldScreen createWorldScreen, Lifecycle lifecycle, Runnable runnable) {
        LOGGER.info("aaaa");
        if (createWorldScreen.worldGenSettingsComponent.settings().worldGenSettings().overworld() instanceof FlatLevelSource flatLevelSource) {
            LOGGER.info("world flatworld");
            if (flatLevelSource instanceof TestWorldSource) {
                LOGGER.info("world testworld");
                // Internals
                // - Hardcore Off
                createWorldScreen.hardCore = false;
                // - Gamemode Creative
                createWorldScreen.gameMode = CreateWorldScreen.SelectedGameMode.CREATIVE;
                // - Cheats On
                createWorldScreen.commands = true;
                /*
                // Game Rules
                GameRules gameRules = createWorldScreen.gameRules;
                // - daylight cycle off
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("doDaylightCycle", GameRules.Category.UPDATES));
                // - weather cycle off
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("doWeatherCycle", GameRules.Category.UPDATES));
                // - random tick speed 0
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("randomTickSpeed", GameRules.Category.UPDATES));
                // - wandering trader off
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("doTraderSpawning", GameRules.Category.SPAWNING));
                // - pillager patrols off
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("doPatrolSpawning", GameRules.Category.SPAWNING));
                // - phantom spawning off
                setGameRule(gameRules, new Key<GameRules.BooleanValue>("doInsomnia", GameRules.Category.SPAWNING));
                }
                 */
            }
        }
        WorldOpenFlows.confirmWorldCreation(Minecraft.getInstance(), createWorldScreen, createWorldScreen.worldGenSettingsComponent.settings().worldSettingsStability(), createWorldScreen::createNewWorld);
    }

    /*static<T extends GameRules.Value<T>> void setGameRule(GameRules gameRules, GameRules.Key<T> key) {
        /*T value = gameRules.getRule(key);
        ((GameRules.Value)value).setFromArgument(new CommandContext(null, null,
                this.input = input;
                this.arguments = arguments;
                this.command = command;
                this.rootNode = rootNode;
                this.nodes = nodes;
                this.range = range;
                this.child = child;
                this.modifier = modifier;
                this.forks = forks;
        ), "value");
    }*/
}
