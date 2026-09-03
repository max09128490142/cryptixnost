package mixins;

import com.cryptix.module.ModuleManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ScreenEffectRenderer.class})
public class MixinScreenEffectRenderer {
   @Redirect(
      method = {"submit"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/LocalPlayer;isOnFire()Z"
      )
   )
   private boolean pm$53(LocalPlayer var1) {
      return ModuleManager.f31.isEnabled() ? false : var1.isOnFire();
   }
}
