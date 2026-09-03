package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// The "Add Server" / edit-server screen; it overrides extractRenderState.
@Mixin(ManageServerScreen.class)
public class MixinManageServerScreen {
   @Inject(method = "extractRenderState", at = @At("HEAD"))
   private void cryptix$video(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial, CallbackInfo ci) {
      Screen self = (Screen)(Object)this;
      VideoPlayer.ADDSERVER.render(graphics, self.width, self.height);
   }
}
