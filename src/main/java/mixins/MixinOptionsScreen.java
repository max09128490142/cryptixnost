package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen {
   // OptionsScreen overrides removed() without calling super, so stop the video here.
   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideo(CallbackInfo ci) {
      VideoPlayer.SETTINGS.stop();
   }
}
