package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DirectJoinServerScreen.class)
public class MixinDirectJoinServerScreen {
   @Inject(method = "extractRenderState", at = @At("HEAD"))
   private void cryptix$video(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial, CallbackInfo ci) {
      Screen self = (Screen)(Object)this;
      VideoPlayer.DIRECT.render(graphics, self.width, self.height);
   }

   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideo(CallbackInfo ci) {
      VideoPlayer.DIRECT.stop();
   }
}
