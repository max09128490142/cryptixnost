package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public class MixinVideoSettingsScreen {
   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideo(CallbackInfo ci) {
      VideoPlayer.VIDEO.stop();
   }
}
