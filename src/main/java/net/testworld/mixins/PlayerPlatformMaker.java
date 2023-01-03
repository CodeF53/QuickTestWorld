package net.testworld.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.testworld.TestWorld.isTestWorldSelected;

@Mixin(ServerPlayer.class)
public abstract class PlayerPlatformMaker {
  @Inject(method = "<init>", at = @At("TAIL"))
  private void platform(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile, ProfilePublicKey profilePublicKey, CallbackInfo ci) {
    if (isTestWorldSelected) {
      serverLevel.setBlock(new BlockPos(((ServerPlayer) (Object) this).position()), Blocks.GLASS.defaultBlockState(), 0);
      ((ServerPlayer) (Object) this).setPos(((ServerPlayer) (Object) this).position().add(0, 2, 0));
      isTestWorldSelected = false;
    }
  }
}
