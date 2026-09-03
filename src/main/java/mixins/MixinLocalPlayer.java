package mixins;

import com.cryptix.event.Events;
import com.cryptix.event.impl.EventPostMotion;
import com.cryptix.event.impl.EventPreMotion;
import com.cryptix.event.impl.EventSlowdown;
import com.cryptix.event.impl.EventSprint;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LocalPlayer.class})
public abstract class MixinLocalPlayer extends AbstractClientPlayer {
   @Shadow
   private double yLast;
   @Shadow
   @Final
   protected Minecraft minecraft;
   @Shadow
   private double xLast;
   @Shadow
   private boolean lastOnGround;
   @Shadow
   @Final
   public ClientPacketListener connection;
   @Shadow
   private float xRotLast;
   @Shadow
   private int sprintTriggerTime;
   @Shadow
   private boolean lastHorizontalCollision;
   @Shadow
   private float yRotLast;
   @Shadow
   private int positionReminder;
   @Shadow
   private boolean autoJumpEnabled;
   @Shadow
   private double zLast;

   @Shadow
   protected abstract boolean canStartSprinting();

   public MixinLocalPlayer(ClientLevel var1, GameProfile var2) {
      super(var1, var2);
   }

   @Redirect(
      method = {"modifyInput"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/LocalPlayer;itemUseSpeedMultiplier()F"
      )
   )
   private float pm$46(LocalPlayer var1) {
      EventSlowdown var2 = Events.f9.m92(this.itemUseSpeedMultiplier());
      var2.call();
      return var2.m93();
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void pm$45(CallbackInfo var1) {
      Events.f3.m75(this.getYRot(), this.getXRot()).call();
      if (Events.f3.isCancelled()) {
         var1.cancel();
      }
   }

   @Shadow
   protected abstract float itemUseSpeedMultiplier();

   @Shadow
   protected abstract void sendIsSprintingIfNeeded();

   @Overwrite
   private void sendPosition() {
      EventPreMotion var1 = Events.f1.m62(this.getX(), this.getY(), this.getZ(), this.onGround(), this.horizontalCollision);
      var1.call();
      this.sendIsSprintingIfNeeded();
      if (this.isControlledCamera()) {
         float var3 = Events.f3.m76();
         float var4 = Events.f3.m82();
         double var5 = var1.m63() - this.xLast;
         double var7 = var1.m65() - this.yLast;
         double var9 = var1.m67() - this.zLast;
         double var11 = (double)(var3 - this.yRotLast);
         double var13 = (double)(var4 - this.xRotLast);
         this.positionReminder++;
         boolean var15 = Mth.lengthSquared(var5, var7, var9) > Mth.square(2.0E-4) || this.positionReminder >= 20;
         boolean var2 = var11 != 0.0 || var13 != 0.0;
         if (var15 && var2) {
            this.connection.send(new PosRot(var1.m63(), var1.m65(), var1.m67(), var3, var4, var1.m71(), var1.m73()));
         } else if (var15) {
            this.connection.send(new Pos(var1.m63(), var1.m65(), var1.m67(), var1.m71(), var1.m73()));
         } else if (var2) {
            this.connection.send(new Rot(var3, var4, var1.m71(), var1.m73()));
         } else if (this.lastOnGround != var1.m71() || this.lastHorizontalCollision != var1.m73()) {
            this.connection.send(new StatusOnly(var1.m71(), var1.m73()));
         }

         if (var15) {
            this.xLast = var1.m63();
            this.yLast = var1.m65();
            this.zLast = var1.m67();
            this.positionReminder = 0;
         }

         if (var2) {
            this.yRotLast = var3;
            this.xRotLast = var4;
         }

         this.lastOnGround = var1.m71();
         this.lastHorizontalCollision = var1.m73();
         this.autoJumpEnabled = (Boolean)this.minecraft.options.autoJump().get();
      }

      EventPostMotion var17 = Events.f2;
      var17.call();
   }

   @Shadow
   protected abstract boolean isControlledCamera();

   @Redirect(
      method = {"aiStep"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/LocalPlayer;canStartSprinting()Z"
      )
   )
   private boolean pm$47(LocalPlayer var1) {
      EventSprint var2 = Events.f12.m95(this.sprintTriggerTime, this.canStartSprinting());
      var2.call();
      return var2.m98();
   }
}
