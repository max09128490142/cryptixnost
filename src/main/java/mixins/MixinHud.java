package mixins;

import com.cryptix.module.ModuleManager;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({Hud.class})
public class MixinHud {
   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"
      ),
      index = 3
   )
   private int pm$30(int var1) {
      return var1 - this.pm$24();
   }

   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"
      ),
      index = 1
   )
   private int pm$29(int var1) {
      return var1 - this.pm$24();
   }

   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"
      ),
      index = 0
   )
   private int pm$27(int var1) {
      return var1 - this.pm$23();
   }

   private int pm$23() {
      return ModuleManager.f32.isEnabled() ? (int)ModuleManager.f32.f32.m220() : 0;
   }

   private int pm$24() {
      return ModuleManager.f32.isEnabled() ? (int)ModuleManager.f32.f33.m220() : 0;
   }

   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"
      ),
      index = 2
   )
   private int pm$28(int var1) {
      return var1 - this.pm$23();
   }

   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"
      ),
      index = 2
   )
   private int pm$25(int var1) {
      return var1 - this.pm$23();
   }

   @ModifyArg(
      method = {"displayScoreboardSidebar"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"
      ),
      index = 3
   )
   private int pm$26(int var1) {
      return var1 - this.pm$24();
   }
}
