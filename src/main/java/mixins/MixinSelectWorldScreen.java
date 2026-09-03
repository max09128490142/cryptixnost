package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class MixinSelectWorldScreen {
   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideo(CallbackInfo ci) {
      VideoPlayer.SINGLE.stop();
   }
}
