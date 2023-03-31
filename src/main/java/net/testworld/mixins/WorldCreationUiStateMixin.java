package net.testworld.mixins;

import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.testworld.TestWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.testworld.TestWorld.VOID_WORLD;

@Mixin(WorldCreationUiState.class)
public class WorldCreationUiStateMixin {
    @Shadow @Final private List<WorldCreationUiState.WorldTypeEntry> normalPresetList;

//    @Shadow @Final private List<WorldCreationUiState.WorldTypeEntry> altPresetList;

    @Inject(method = "updatePresetLists", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", shift = At.Shift.AFTER, ordinal = 0))
    private void addTestWorldNormalPresetList(CallbackInfo ci) {
        normalPresetList.add(new WorldCreationUiState.WorldTypeEntry(TestWorld.getVoidWorld()));
    }

//    @Inject(method = "updatePresetLists", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", shift = At.Shift.AFTER, ordinal = 1))
//    private void addTestWorldAltPresetList(CallbackInfo ci) {
//        altPresetList.add(new WorldCreationUiState.WorldTypeEntry(TestWorld.getVoidWorld()));
//    }
}
