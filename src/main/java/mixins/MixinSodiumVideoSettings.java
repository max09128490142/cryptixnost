package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Sodium replaces the vanilla Video Settings screen with its own; target it by name
// (no compile-time dependency on Sodium). It extends Screen and overrides extractRenderState.
@Mixin(targets = "net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen")
public class MixinSodiumVideoSettings {
   @Inject(method = "extractRenderState", at = @At("HEAD"))
   private void cryptix$video(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial, CallbackInfo ci) {
      Screen self = (Screen)(Object)this;
      VideoPlayer.VIDEO.render(graphics, self.width, self.height);
   }
}
