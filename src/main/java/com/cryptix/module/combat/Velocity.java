package com.cryptix.module.combat;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.ModeSetting;
import com.cryptix.setting.NumberSetting;
import com.cryptix.util.TargetFinder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundStartConfigurationPacket;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Velocity extends Module {
   private final ModeSetting f335;
   private static final String f321 = "Disabled";
   private static final String f319 = "Reduce Ticks";
   private final NumberSetting f338;
   private static final String f320 = "Reduce";
   private final BooleanSetting f337;
   private final NumberSetting f331;
   private static final String f317 = "Vertical";
   private static final String f329 = "Reverse";
   private int f343;
   private static final String f325 = "Delay";
   private final BooleanSetting f336;
   private static final String f330 = "Jump";
   private final NumberSetting f333;
   private final BooleanSetting f340;
   private static final String f327 = "Delay Range";
   private static final String f316 = "Horizontal";
   private int f342;
   private static final String f324 = "Jump Reset";
   public final List<Packet> f18;
   private static final String f328 = "Delay Until Ground";
   private static final String f326 = "Delay Ticks";
   private final NumberSetting f339;
   private static final String f322 = "Normal";
   private final BooleanSetting f341;
   private static final String f318 = "Reduce Motion";
   private int f346;
   private final NumberSetting f332;
   private static final String f323 = "AirPush";
   private boolean f344;
   private final NumberSetting f334;
   private boolean f345;
   private static final String f315 = "Velocity";

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f8 && mc.player.onGround() && this.f344) {
         mc.player.input.makeJump();
         System.out.println(f330);
         this.f344 = false;
      }

      if (var1 == Events.f3) {
         if (!this.f335.m228(f321)) {
            this.setSuffix(this.f335.m224());
         } else {
            this.setSuffix(null);
         }

         if (!this.f18.isEmpty()) {
            this.f343++;
            if ((double)this.f343 > this.f338.m220() || this.f340.m215() && mc.player.onGround()) {
               this.pm$76();
            }
         }

         if (this.f342 >= 0 && (double)this.f342 < this.f334.m220() && mc.gui.screen() == null) {
            this.f342++;
            Vec3 var2 = mc.player.getDeltaMovement();
            double var3 = this.f333.m220();
            if (this.f335.m228(f322)) {
               LivingEntity var5 = TargetFinder.m47(3.0, false);
               if (var5 != null && var5 instanceof Player && this.f346 != mc.player.tickCount && this.f18.isEmpty() && var5.isAlive()) {
                  Vec3 var6 = mc.player.getEyePosition();
                  Vec3 var7 = var5.getEyePosition();
                  double var8 = var7.x - var6.x;
                  double var10 = var7.y - var6.y;
                  double var12 = var7.z - var6.z;
                  double var14 = Math.sqrt(var8 * var8 + var12 * var12);
                  float var16 = (float)(Math.toDegrees(Math.atan2(var12, var8)) - 90.0);
                  float var17 = (float)(-Math.toDegrees(Math.atan2(var10, var14)));
                  Events.f3.m77(var16);
                  Events.f3.m83(var17);
                  this.pm$74(var5);
               }
            } else if (this.f335.m228(f323)) {
               LivingEntity var23 = TargetFinder.m49(false);
               if (var23 != null && this.f346 != mc.player.tickCount && this.f18.isEmpty() && var23.isAlive()) {
                  this.pm$74(var23);
               } else {
                  var23 = TargetFinder.m47(3.0, false);
                  if (var23 != null && var23 instanceof Player && this.f346 != mc.player.tickCount && this.f18.isEmpty() && var23.isAlive()) {
                     Vec3 var26 = mc.player.getEyePosition();
                     Vec3 var27 = var23.getEyePosition();
                     double var29 = var27.x - var26.x;
                     double var30 = var27.y - var26.y;
                     double var31 = var27.z - var26.z;
                     double var32 = Math.sqrt(var29 * var29 + var31 * var31);
                     float var33 = (float)(Math.toDegrees(Math.atan2(var31, var29)) - 90.0);
                     float var34 = (float)(-Math.toDegrees(Math.atan2(var30, var32)));
                     Events.f3.m77(var33);
                     Events.f3.m83(var34);
                     this.pm$74(var23);
                  }
               }
            } else {
               mc.player.setDeltaMovement(var2.x * var3, var2.y, var2.z * var3);
            }
         }
      }

      if (var1 == Events.f10) {
         if (Events.f10.m44() instanceof ServerboundAttackPacket) {
            this.f346 = mc.player.tickCount;
         }

         if (Events.f10.m44() instanceof ServerboundPlayerActionPacket && this.f345) {
            var1.setCancelled(true);
         }
      }

      if (var1 == Events.f11) {
         Packet var20 = Events.f11.m41();
         if (var20 instanceof ClientboundSetEntityMotionPacket var21) {
            if (var21.id() == mc.player.getId()) {
               Vec3 var4 = var21.movement();
               if (var4.y > 0.0 && !ModuleManager.f35.isEnabled()) {
                  double var25 = this.f341.m215() ? -this.f331.m220() * 0.01 : this.f331.m220() * 0.01;
                  double var28 = var4.x * var25;
                  double var9 = var4.y * this.f332.m220() * 0.01;
                  double var11 = var4.z * var25;
                  if (this.f331.m220() != 100.0 || this.f341.m215()) {
                     var1.setCancelled(true);
                  }

                  if (this.f334.m220() != 0.0) {
                     this.f342 = 0;
                  }

                  if (this.f337.m215() && this.pm$75()) {
                     this.f18.add(var21);
                     var1.setCancelled(true);
                  } else {
                     if (this.f331.m220() != 100.0 || this.f341.m215()) {
                        mc.player.setDeltaMovement(new Vec3(var28, var9, var11));
                     }

                     if (this.f336.m215() && mc.player.onGround() && mc.player.isSprinting()) {
                        this.f344 = true;
                     }
                  }
               }
            }
         } else if (this.f337.m215() && !this.f18.isEmpty()) {
            if (var20 instanceof ClientboundStartConfigurationPacket || var20 instanceof ClientboundDisconnectPacket) {
               this.pm$76();
               return;
            }

            synchronized (this.f18) {
               this.f18.add(var20);
               var1.setCancelled(true);
            }
         }
      }
   }

   private boolean pm$75() {
      LivingEntity var1 = TargetFinder.m47(6.0, false);
      LivingEntity var2 = TargetFinder.m49(false);
      return (!mc.player.onGround() || !this.f340.m215())
         && (var1 == null || (double)mc.player.distanceTo(var1) > this.f339.m220())
         && (!this.f335.m228(f323) || !mc.player.isSprinting() && (var2 == null || !(mc.player.distanceTo(var2) > 11.0F)));
   }

   private void pm$74(Entity var1) {
      if (!ModuleManager.f33.isEnabled() || !ModuleManager.f33.f22) {
         this.f345 = true;
         mc.gameMode.attack(mc.player, var1);
         mc.getConnection().send(new ServerboundSwingPacket(InteractionHand.MAIN_HAND));
         this.f345 = false;
      }
   }

   private void pm$76() {
      if (!this.f18.isEmpty()) {
         synchronized (this.f18) {
            for (Packet var3 : this.f18) {
               mc.execute(() -> var3.handle(mc.getConnection()));
            }

            this.f18.clear();
         }

         this.f343 = 0;
      }
   }

   public Velocity() {
      super(f315, Category.COMBAT);
      this.f331 = new NumberSetting(f316, this, 100.0, 0.0, 100.0, 5.0);
      this.f332 = new NumberSetting(f317, this, 100.0, 0.0, 100.0, 5.0);
      this.f333 = new NumberSetting(f318, this, 1.0, 0.0, 1.0, 0.1);
      this.f334 = new NumberSetting(f319, this, 0.0, 0.0, 5.0, 1.0);
      this.f335 = new ModeSetting(f320, this, f321, new String[]{f321, f322, f323});
      this.f336 = new BooleanSetting(f324, this, false);
      this.f337 = new BooleanSetting(f325, this, false);
      this.f338 = new NumberSetting(f326, this, 4.0, 1.0, 15.0, 1.0);
      this.f339 = new NumberSetting(f327, this, 3.0, 1.0, 4.0, 0.5);
      this.f340 = new BooleanSetting(f328, this, false);
      this.f341 = new BooleanSetting(f329, this, false);
      this.f18 = new ArrayList<>();
      this.f342 = -1;
   }
}
