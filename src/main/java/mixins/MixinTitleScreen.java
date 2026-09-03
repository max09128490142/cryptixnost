package mixins;

import com.cryptix.util.ModTextures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
   private static final String CRYPTIX_MENU_IMAGE = "textures/gui/menu.png";
   private static final int IMG_W = 445;
   private static final int IMG_H = 449;

   // Draw our image stretched over the whole screen, right after the panorama
   // background and before the widgets (buttons stay visible and clickable on top).
   @Inject(
      method = "extractRenderState",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screens/TitleScreen;extractPanorama(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V",
         shift = Shift.AFTER
      )
   )
   private void cryptix$drawMenuImage(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial, CallbackInfo ci) {
      Screen self = (Screen)(Object)this;
      // The mod's assets are not in any resource pack, so upload it ourselves on first use.
      Identifier image = ModTextures.register(CRYPTIX_MENU_IMAGE);
      graphics.blit(RenderPipelines.GUI_TEXTURED, image, 0, 0, 0.0F, 0.0F, self.width, self.height, IMG_W, IMG_H, IMG_W, IMG_H, -1);
   }
}
