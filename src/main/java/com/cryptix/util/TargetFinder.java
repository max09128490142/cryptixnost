package com.cryptix.util;

import com.cryptix.module.ModuleManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class TargetFinder implements Wrapper {
   public static LivingEntity m48(double var0, boolean var2) {
      if (ModuleManager.f29.isEnabled()) {
         return null;
      } else {
         Player var3 = null;
         double var4 = Double.MAX_VALUE;
         double var6 = var0 * var0;
         LocalPlayer var8 = Wrapper.mc.player;
         double var9 = var8.getX();
         double var11 = var8.getY();
         double var13 = var8.getZ();

         for (Player var16 : Wrapper.mc.level.players()) {
            if (var16 != Wrapper.mc.player
               && !pm$112(var16)
               && var16.isAlive()
               && var16.deathTime <= 0
               && !var16.isRemoved()
               && (!ModuleManager.f27.m136(var16) || !var2)) {
               double var17 = var16.getX() - var9;
               double var19 = var16.getY() - var11;
               double var21 = var16.getZ() - var13;
               double var23 = var17 * var17 + var19 * var19 + var21 * var21;
               if (!(var23 > var6) && var23 < var4) {
                  var4 = var23;
                  var3 = var16;
               }
            }
         }

         return var3;
      }
   }

   public static Entity m50() {
      Entity var0 = null;
      double var1 = Double.MAX_VALUE;

      for (Entity var4 : Wrapper.mc.level.players()) {
         if (var4 instanceof LivingEntity) {
            LivingEntity var5 = (LivingEntity)var4;
            if (var4 != Wrapper.mc.player && var5.isAlive() && !ModuleManager.f27.m136(var4)) {
               double var6 = Wrapper.mc.player.distanceToSqr(var4);
               if (var6 < var1) {
                  var1 = var6;
                  var0 = var4;
               }
            }
         }
      }

      return var0;
   }

   public static LivingEntity m47(double var0, boolean var2) {
      if (ModuleManager.f29.isEnabled()) {
         return null;
      } else {
         Player var3 = null;
         double var4 = Double.MAX_VALUE;
         double var6 = var0 * var0;
         LocalPlayer var8 = Wrapper.mc.player;
         double var9 = var8.getX();
         double var11 = var8.getY();
         double var13 = var8.getZ();

         for (Player var16 : Wrapper.mc.level.players()) {
            if (var16 != Wrapper.mc.player && var16.isAlive() && var16.deathTime <= 0 && !var16.isRemoved() && (!ModuleManager.f27.m136(var16) || !var2)) {
               double var17 = var16.getX() - var9;
               double var19 = var16.getY() - var11;
               double var21 = var16.getZ() - var13;
               double var23 = var17 * var17 + var19 * var19 + var21 * var21;
               if (!(var23 > var6) && var23 < var4) {
                  var4 = var23;
                  var3 = var16;
               }
            }
         }

         return var3;
      }
   }

   private static boolean pm$112(Player var0) {
      if (var0 == null || Wrapper.mc.player == null) {
         return false;
      } else if (Wrapper.mc.player.isAlliedTo(var0)) {
         return true;
      } else {
         String var1 = Wrapper.mc.player.getDisplayName().getString();
         String var2 = var0.getDisplayName().getString();
         if (var1.length() >= 2 && var2.length() >= 2) {
            String var3 = var1.substring(0, 2);
            String var4 = var2.substring(0, 2);
            if (var3.equals(var4)) {
               return true;
            }
         }

         return false;
      }
   }

   public static LivingEntity m49(boolean var0) {
      LivingEntity var1 = null;
      double var2 = -1.0;

      for (Entity var5 : Wrapper.mc.level.entitiesForRendering()) {
         if (var5 instanceof LivingEntity) {
            LivingEntity var6 = (LivingEntity)var5;
            if (var6 != Wrapper.mc.player
               && var6.isAlive()
               && var6.deathTime <= 0
               && !var6.isRemoved()
               && (!ModuleManager.f27.m136(var5) || !var0)
               && !ModuleManager.f29.isEnabled()
               && var5 instanceof Player) {
               double var7 = (double)Wrapper.mc.player.distanceTo(var6);
               if (!(var7 < 11.0) && var7 > var2) {
                  var2 = var7;
                  var1 = var6;
               }
            }
         }
      }

      return var1;
   }
}
