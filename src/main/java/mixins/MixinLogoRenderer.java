package mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.util.ARGB;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Replaces the Minecraft logo (and its "Java Edition" strip) with the client's own wordmark.
@Mixin(LogoRenderer.class)
public class MixinLogoRenderer {
   @Unique
   private static final String CRYPTIX_TITLE = "CRYPTIXNOST";
   @Unique
   private static final float CRYPTIX_SCALE = 3.5F;

   @Shadow
   @Final
   private boolean keepLogoThroughFade;

   @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IFI)V", at = @At("HEAD"), cancellable = true)
   private void cryptix$drawWordmark(GuiGraphicsExtractor graphics, int width, float alpha, int y, CallbackInfo ci) {
      ci.cancel();

      float fade = this.keepLogoThroughFade ? 1.0F : alpha;
      int color = ARGB.white(fade);
      // Centre the text inside the 256x44 band the logo used to occupy.
      int lineHeight = (int)(Minecraft.getInstance().font.lineHeight * CRYPTIX_SCALE);
      int textY = y + (LogoRenderer.LOGO_HEIGHT - lineHeight) / 2;

      Matrix3x2fStack pose = graphics.pose();
      pose.pushMatrix();
      pose.scale(CRYPTIX_SCALE, CRYPTIX_SCALE);
      graphics.centeredText(Minecraft.getInstance().font, CRYPTIX_TITLE, (int)(width / 2.0F / CRYPTIX_SCALE), (int)(textY / CRYPTIX_SCALE), color);
      pose.popMatrix();
   }
}
