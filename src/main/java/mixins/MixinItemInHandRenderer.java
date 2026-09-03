package mixins;

import com.cryptix.module.ModuleManager;
import com.cryptix.module.combat.KillAura;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemInHandRenderer.class})
public class MixinItemInHandRenderer {
   @Redirect(
      method = {"submitArmWithItem"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/AbstractClientPlayer;getUsedItemHand()Lnet/minecraft/world/InteractionHand;"
      )
   )
   private InteractionHand pm$33(AbstractClientPlayer var1) {
      KillAura var2 = ModuleManager.f26;
      return var2 != null && var2.f15 ? InteractionHand.MAIN_HAND : var1.getUsedItemHand();
   }

   @Inject(
      method = {"submitArmWithItem"},
      slice = {@Slice(
         from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"
         )
      )},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V",
         ordinal = 0,
         shift = Shift.AFTER
      )}
   )
   private void pm$34(
      AbstractClientPlayer var1,
      float var2,
      float var3,
      InteractionHand var4,
      float var5,
      ItemStack var6,
      float var7,
      PoseStack var8,
      SubmitNodeCollector var9,
      int var10,
      CallbackInfo var11
   ) {
      if (ModuleManager.f34.isEnabled()) {
         float var12 = Mth.sin((double)(var5 * var5 * (float) Math.PI));
         float var13 = Mth.sin((double)(Mth.sqrt(var5) * (float) Math.PI));
         float var14 = (float)ModuleManager.f34.f27.m220();
         float var15 = (float)ModuleManager.f34.f28.m220();
         float var16 = (float)ModuleManager.f34.f29.m220();
         float var17 = (float)ModuleManager.f34.f30.m220();
         HumanoidArm var18 = var1.getMainArm();
         float var19 = var18 == HumanoidArm.RIGHT ? 1.0F : -1.0F;
         var8.translate(var15, var16, var17);
         var8.mulPose(Axis.YP.rotationDegrees(45.0F * var19));
         var8.mulPose(Axis.YP.rotationDegrees(-10.0F * var19));
         var8.mulPose(Axis.YP.rotationDegrees(var12 * -20.0F * var19));
         var8.mulPose(Axis.ZP.rotationDegrees(var13 * -20.0F * var19));
         var8.mulPose(Axis.XP.rotationDegrees(var13 * -80.0F));
         var8.mulPose(Axis.YP.rotationDegrees(-45.0F * var19));
         var8.translate(0.0F, 0.06F, 0.0F);
         var8.scale(0.95F * var14, 0.95F * var14, 0.95F * var14);
      }
   }

   @Redirect(
      method = {"submitArmWithItem"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/AbstractClientPlayer;getUseItemRemainingTicks()I"
      )
   )
   private int pm$32(AbstractClientPlayer var1) {
      KillAura var2 = ModuleManager.f26;
      return var2 != null && var2.f15 ? 20 : var1.getUseItemRemainingTicks();
   }

   @Redirect(
      method = {"submitArmWithItem"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z"
      )
   )
   private boolean pm$31(AbstractClientPlayer var1) {
      KillAura var2 = ModuleManager.f26;
      if (var2 != null && var2.f15) {
         ItemStack var3 = var1.getMainHandItem();
         if (!var3.isEmpty() && var3.getUseAnimation() == ItemUseAnimation.BLOCK) {
            return true;
         }
      }

      return var1.isUsingItem();
   }
}
