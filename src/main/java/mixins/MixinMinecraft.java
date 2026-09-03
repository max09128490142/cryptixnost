package mixins;

import com.cryptix.event.Events;
import com.cryptix.module.ModuleManager;
import com.cryptix.util.IntroAudio;
import com.cryptix.util.VideoPlayer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public class MixinMinecraft {
   private boolean f211 = false;

   @Inject(method = "createTitle", at = @At("HEAD"), cancellable = true)
   private void cryptix$windowTitle(CallbackInfoReturnable<String> cir) {
      cir.setReturnValue("Cryptixnost 26.2 | trillionaire deepseek v4-flash bipas 676767 sahur tung tung");
   }

   @Inject(
      method = {"onResourceLoadFinished"},
      at = {@At("TAIL")}
   )
   private void pm$38(CallbackInfo var1) {
      if (!this.f211) {
         ModuleManager.loadEnabled();
         this.f211 = true;
      }
   }

   @Inject(method = "tick", at = @At("TAIL"))
   private void cryptix$clientTick(CallbackInfo ci) {
      VideoPlayer.tickAll();
      IntroAudio.tick();
   }

   @Inject(
      method = {"handleKeybinds"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/Gui;handleKeybinds()V",
         shift = Shift.AFTER
      )}
   )
   private void pm$40(CallbackInfo var1) {
      Events.f4.call();
   }
}
