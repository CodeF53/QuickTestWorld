package net.testworld.mixins;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.tree.CommandNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(CommandContext.class)
public class testmixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("modid");

    @Inject(method = "<init>", at = @At("TAIL"))
    private void printArgs(Object source, String input, Map arguments, Command command, CommandNode rootNode, List nodes, StringRange range, CommandContext child, RedirectModifier modifier, boolean forks, CallbackInfo ci) {
        if (arguments!=null) {
            LOGGER.info("arguments " + arguments.toString());
            arguments.forEach((o, o2) -> {LOGGER.info(o.toString()); LOGGER.info(o.toString()); o2.toString();});
        }
        LOGGER.info("");
    }
}
