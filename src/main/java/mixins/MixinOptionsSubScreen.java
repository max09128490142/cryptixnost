package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// SoundOptionsScreen does not override removed(); it inherits OptionsSubScreen.removed().
@Mixin(OptionsSubScreen.class)
public class MixinOptionsSubScreen {
   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideo(CallbackInfo ci) {
      if ((Object)this instanceof SoundOptionsScreen) {
         VideoPlayer.SOUND.stop();
      }
   }
}
