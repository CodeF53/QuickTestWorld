package net.testworld.mixins;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.WorldPresetTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.WorldPresetTags;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

import static net.testworld.TestWorld.VOID_WORLD;

@Mixin(WorldPresetTagsProvider.class)
public abstract class WorldPresetTagsProviderMixin extends TagsProvider<WorldPreset> {
    protected WorldPresetTagsProviderMixin(PackOutput packOutput, ResourceKey<? extends Registry<WorldPreset>> resourceKey, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, resourceKey, completableFuture);
    }

    @Inject(method = "addTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/tags/TagsProvider$TagAppender;add(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/data/tags/TagsProvider$TagAppender;", shift = At.Shift.AFTER, ordinal = 4))
    private void addTags(HolderLookup.Provider provider, CallbackInfo ci) {
        this.tag(WorldPresetTags.NORMAL).add(VOID_WORLD);
    }
}
