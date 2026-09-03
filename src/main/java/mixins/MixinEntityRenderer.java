package mixins;

import com.cryptix.event.Events;
import com.cryptix.event.impl.EventEntityOutline;
import com.cryptix.event.impl.EventRenderNameTag;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public abstract class MixinEntityRenderer<T extends Entity, S extends EntityRenderState> {
   @Inject(
      method = {"submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void pm$49(EntityRenderState var1, PoseStack var2, SubmitNodeCollector var3, CameraRenderState var4, int var5, CallbackInfo var6) {
      var2.pushPose();
      if (var1.scoreText != null) {
         EventRenderNameTag var7 = Events.f13.m22(var2, var1.scoreText, var1.nameTagAttachment, var5, var1.lightCoords, !var1.isDiscrete);
         var7.call();
         var3.submitNameTag(var2, var7.m25(), var7.m26(), var7.m24(), var7.m28(), var7.m27(), var4);
         var2.translate(0.0F, 0.25875F, 0.0F);
      }

      if (var1.nameTag != null) {
         EventRenderNameTag var8 = Events.f13.m22(var2, var1.nameTag, var1.nameTagAttachment, var5, var1.lightCoords, !var1.isDiscrete);
         var8.call();
         var3.submitNameTag(var2, var8.m25(), var8.m26(), var8.m24(), var8.m28(), var8.m27(), var4);
      }

      var2.popPose();
      var6.cancel();
   }

   @Inject(
      method = {"extractRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void pm$50(Entity var1, EntityRenderState var2, float var3, CallbackInfo var4) {
      boolean var5 = Minecraft.getInstance().shouldEntityAppearGlowing(var1);
      EventEntityOutline var6 = Events.f14.m36(var5 ? ARGB.opaque(var1.getTeamColor()) : 0, var1);
      var6.call();
      var2.outlineColor = var6.m37();
   }
}
