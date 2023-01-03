package net.testworld.mixins;

import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static net.testworld.TestWorld.VOID_WORLD;
import static net.testworld.TestWorld.isTestWorldSelected;

@Mixin(WorldGenSettingsComponent.class)
public abstract class WorldGenSettingsComponentMixin {
  @Shadow private Optional<Holder<WorldPreset>> preset;

  @Inject(method = "method_32679(Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lnet/minecraft/client/gui/components/CycleButton;Lnet/minecraft/core/Holder;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldGenSettingsComponent;preset:Ljava/util/Optional;", shift = At.Shift.AFTER))
  private void onSetWorldPreset(CreateWorldScreen createWorldScreen, CycleButton<?> cycleButton, Holder<?> holder, CallbackInfo ci) {
    preset.ifPresentOrElse(h -> isTestWorldSelected = h.is(VOID_WORLD), () -> isTestWorldSelected = false);
  }
}
