package com.cryptix.module.combat;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.ModeSetting;
import com.cryptix.setting.NumberSetting;
import com.cryptix.util.InventoryUtil;
import com.cryptix.util.PacketBlinkQueue;
import com.cryptix.util.TargetFinder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
   private float f301;
   private static final String f262 = "Watchdog";
   private float f300;
   private int f288;
   private boolean f296;
   private static final String f273 = "AutoBlock RMB";
   private static final String f274 = "Attack Teammates";
   private int f297;
   private static final String f263 = "Watchdog2";
   private final BooleanSetting f284;
   private static final String f255 = "Rotations";
   private static final String f266 = "Cycle";
   private boolean f291;
   private boolean f292;
   private boolean f293;
   private boolean f289;
   private static final String f254 = "KillAura";
   private final NumberSetting f281;
   private int f298;
   private static final String f267 = "Attack Delay";
   private static final String f259 = "Disabled";
   private int f294;
   private static final String f276 = "shield";
   private static final String f260 = "Fake";
   private static final String f270 = "Attack Cooldown";
   private static final String f268 = "Attack Delay2";
   private final BooleanSetting f285;
   private final BooleanSetting f286;
   private static final String f269 = "Rotation Range";
   private static final String f261 = "Vanilla";
   private boolean f290;
   public boolean f16;
   private static final String f272 = "Require Sword";
   private final BooleanSetting f287;
   private static final String f256 = "Head";
   public boolean f15;
   private final ModeSetting f278;
   private final NumberSetting f280;
   private static final String f264 = "Swap";
   public Entity f17;
   private final NumberSetting f279;
   private boolean f299;
   private final BooleanSetting f283;
   private static final String f257 = "Optimal";
   private final ModeSetting f277 = new ModeSetting(f255, this, f256, new String[]{f256, f257});
   private static final String f271 = "Ignore Shield";
   private static final String f258 = "AutoBlock";
   private final BooleanSetting f282;
   private static final String f265 = "Swap2";
   private int f295;
   private static final String f275 = "Attack Fireballs";

   private void pm$64() {
      mc.getConnection().send(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, 0, Events.f3.m76(), Events.f3.m82()));
      this.f16 = true;
   }

   private void pm$62() {
      if (this.f291) {
         mc.getConnection().send(new ServerboundSetCarriedItemPacket(mc.player.getInventory().getSelectedSlot()));
         this.f291 = false;
      }
   }

   private float pm$70() {
      float var1 = ((Double)mc.options.sensitivity().get()).floatValue();
      float var2 = var1 * 0.6F + 0.2F;
      return var2 * var2 * var2 * 8.0F * 0.15F;
   }

   private void pm$63(float var1, float var2, boolean var3) {
      if (!ModuleManager.f33.f22) {
         double var4 = this.pm$66(this.f17, var1, var2, 10.0);
         if (var4 <= 3.0 && var4 >= 0.0) {
            mc.gameMode.attack(mc.player, this.f17);
            mc.player.swing(InteractionHand.MAIN_HAND);
            if (var3) {
               mc.getConnection().send(new ServerboundInteractPacket(this.f17.getId(), InteractionHand.MAIN_HAND, Vec3.ZERO, false));
            }
         }
      }
   }

   private void pm$65() {
      this.f289 = false;
      mc.getConnection().send(new ServerboundPlayerActionPacket(Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
      this.f289 = true;
      this.f16 = false;
   }

   private void pm$58(Entity var1, float var2, float var3) {
      this.f289 = false;
      this.f15 = true;
      String var4 = this.f278.m224();
      byte var5 = -1;
      int var10000 = var4.hashCode();
      if (var10000 == 2182005) {
         if (var4.equals(f260)) {
            var5 = 1;
         }
      } else if (var10000 == 2590131) {
         if (var4.equals(f264)) {
            var5 = 5;
         }
      } else if (var10000 == 65579206) {
         if (var4.equals(f266)) {
            var5 = 2;
         }
      } else if (var10000 == 80294111) {
         if (var4.equals(f265)) {
            var5 = 6;
         }
      } else if (var10000 == 609795629) {
         if (var4.equals(f262)) {
            var5 = 3;
         }
      } else if (var10000 == 1723795365) {
         if (var4.equals(f263)) {
            var5 = 4;
         }
      } else if (var10000 == 1897755483) {
         if (var4.equals(f261)) {
            var5 = 0;
         }
      }

      switch (var5) {
         case 0:
            mc.getConnection().send(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, 0, mc.player.getYRot(), mc.player.getXRot()));
         case 1:
         default:
            break;
         case 2:
            this.f289 = true;
            this.f294++;
            if (this.f294 < 3) {
               return;
            }

            if (this.f291) {
               this.pm$61(mc.player.getInventory().getSelectedSlot());
            }

            this.pm$63(this.f300, this.f301, true);
            this.pm$64();
            mc.player.startUsingItem(InteractionHand.MAIN_HAND);
            PacketBlinkQueue.m23();
            this.f296 = true;
            if (!this.f291) {
               this.pm$61(mc.player.getInventory().getSelectedSlot() % 8 + 1);
            }

            PacketBlinkQueue.m22();
            this.f294 = 0;
            break;
         case 3:
            this.f289 = true;
            this.f294++;
            if (this.f294 < 2) {
               if (this.f16) {
                  mc.player.startUsingItem(InteractionHand.MAIN_HAND);
               }

               return;
            }

            this.pm$65();
            this.pm$63(this.f300, this.f301, true);
            this.pm$64();
            mc.player.startUsingItem(InteractionHand.MAIN_HAND);
            this.f294 = 0;
            break;
         case 4:
            this.f289 = true;
            this.f294++;
            if (this.f294 < 2) {
               if (this.f16) {
                  mc.player.startUsingItem(InteractionHand.MAIN_HAND);
               }

               return;
            }

            this.pm$65();
            boolean var6 = this.pm$59(var1, 45.0);
            if (mc.player.hurtTime <= 5 && var6 && !((double)mc.player.distanceTo(var1) > 3.0)) {
               this.pm$63(this.f300, this.f301, true);
               this.pm$64();
               mc.player.startUsingItem(InteractionHand.MAIN_HAND);
            } else {
               this.pm$63(this.f300, this.f301, false);
            }

            this.f294 = 0;
            break;
         case 5:
            this.f289 = true;
            this.f294++;
            if (this.f294 < 3) {
               if (this.f294 != 2 && this.f16) {
                  mc.player.startUsingItem(InteractionHand.MAIN_HAND);
               }

               return;
            }

            this.pm$60();
            if (this.f297 != -1 && this.f298 != -1) {
               int var7 = this.f299 ? this.f298 : this.f297;
               this.f299 = !this.f299;
               this.pm$61(var7);
               if (mc.player.getInventory().getItem(var7).is(ItemTags.SWORDS)) {
                  this.pm$63(this.f300, this.f301, true);
                  this.pm$64();
                  mc.player.startUsingItem(InteractionHand.MAIN_HAND);
               }

               this.f294 = 0;
            } else if (mc.player.getInventory().getItem(mc.player.getInventory().getSelectedSlot()).is(ItemTags.SWORDS)) {
               this.pm$63(this.f300, this.f301, false);
            }
            break;
         case 6:
            this.f294++;
            this.f289 = true;
            if (this.f16) {
               this.pm$65();
            }

            if (!this.f291 && this.f292) {
               this.pm$61(mc.player.getInventory().getSelectedSlot() % 8 + 1);
               return;
            }

            if (this.f294 < 3) {
               return;
            }

            if (this.f291) {
               this.pm$61(mc.player.getInventory().getSelectedSlot());
               this.pm$63(this.f300, this.f301, false);
            }

            this.f295++;
            if (this.f295 < 3) {
               this.f292 = true;
               this.f299 = !this.f299;
               this.pm$63(this.f300, this.f301, true);
               this.pm$64();
            } else {
               this.f292 = false;
               this.f299 = !this.f299;
               if (this.f299) {
                  this.pm$63(this.f300, this.f301, true);
                  this.f293 = true;
               } else {
                  this.pm$63(this.f300, this.f301, false);
               }

               this.f295 = 0;
            }

            this.f294 = 0;
      }
   }

   private float pm$71(float var1, float var2) {
      float var3 = this.pm$70();
      float var4 = var1 - var2;
      var4 -= var4 % var3;
      return var2 + var4;
   }

   private Vec3 pm$68(float var1, float var2) {
      float var3 = Mth.cos((double)(-var2 * (float) (Math.PI / 180.0) - (float) Math.PI));
      float var4 = Mth.sin((double)(-var2 * (float) (Math.PI / 180.0) - (float) Math.PI));
      float var5 = -Mth.cos((double)(-var1 * (float) (Math.PI / 180.0)));
      float var6 = Mth.sin((double)(-var1 * (float) (Math.PI / 180.0)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }

   @Override
   public int getPriority(Event var1) {
      return var1 == Events.f3 ? -2 : 0;
   }

   private void pm$72() {
      this.f15 = false;
      this.f289 = false;
      this.f17 = null;
      if (this.f291) {
         this.pm$62();
      }

      if (this.f16) {
         this.pm$65();
      }

      if (this.f296) {
         PacketBlinkQueue.m23();
         this.f296 = false;
      }

      this.f294 = 0;
      this.f295 = 0;
   }

   private void pm$61(int var1) {
      mc.getConnection().send(new ServerboundSetCarriedItemPacket(var1));
      this.f291 = true;
      if (var1 == mc.player.getInventory().getSelectedSlot()) {
         this.f291 = false;
      }
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f2 && this.f293 && this.f17 != null) {
         this.f293 = false;
         if (!this.f278.m228(f263)) {
            this.pm$64();
         } else {
            this.pm$65();
         }
      }

      if (var1 == Events.f15) {
         System.out.println(Events.f15.m14());
         if (mc.gui.screen() == null && this.f17 != null) {
            var1.setCancelled(true);
         }
      }

      if (var1 == Events.f3) {
         this.f15 = false;
         if (mc.gui.screen() != null) {
            this.pm$72();
            return;
         }

         if (!this.f282.m215() || mc.player.getAttackStrengthScale(0.0F) >= 1.0F) {
            this.f288++;
         }

         if (!this.f286.m215()) {
            this.f17 = TargetFinder.m48(this.f281.m220(), true);
         } else {
            this.f17 = TargetFinder.m47(this.f281.m220(), true);
         }

         if (this.f287.m215()) {
            Entity var2 = this.pm$67(3.0);
            if (var2 != null) {
               this.pm$69(var2);
               Events.f3.m77(this.f300);
               Events.f3.m83(this.f301);
               mc.gameMode.attack(mc.player, var2);
               mc.player.swing(InteractionHand.MAIN_HAND);
               this.pm$72();
               return;
            }
         }

         if (this.f284.m215() && !InventoryUtil.m39()) {
            this.f17 = null;
         }

         if (this.f17 != null) {
            this.pm$69(this.f17);
            Events.f3.m77(this.f300);
            Events.f3.m83(this.f301);
            double var6 = this.f290 ? this.f280.m220() : this.f279.m220();
            if (ModuleManager.f28.f18.isEmpty() && (double)this.f288 > var6 && (!this.f282.m215() || mc.player.getAttackStrengthScale(0.0F) >= 1.0F)) {
               if (this.f283.m215() && this.f17 instanceof Player var4 && var4.isUsingItem() && var4.getUseItem().is(Items.SHIELD)) {
                  System.out.println(f276);
                  return;
               }

               if (!this.f278.m228(f262) && !this.f278.m228(f263) && !this.f278.m228(f264) && !this.f278.m228(f265) && !this.f278.m228(f266)
                  || !this.pm$73() && !this.f16) {
                  this.f288 = 0;
                  this.pm$63(this.f300, this.f301, false);
               }

               this.f290 = !this.f290;
            }

            if (!this.f278.m228(f259) && mc.player.getMainHandItem().is(ItemTags.SWORDS)) {
               if (this.pm$73()) {
                  mc.options.keyUse.setDown(false);
                  this.pm$58(this.f17, this.f300, this.f301);
               } else {
                  if (this.f16) {
                     this.pm$65();
                  }

                  if (this.f296) {
                     this.f296 = false;
                     PacketBlinkQueue.m23();
                  }
               }
            }
         } else {
            this.pm$72();
         }
      }

      if (var1 == Events.f10 && Events.f10.m44() instanceof ServerboundPlayerActionPacket && this.f289) {
         var1.setCancelled(true);
      }
   }

   private boolean pm$59(Entity var1, double var2) {
      Vec3 var4 = var1.getEyePosition(1.0F);
      Vec3 var5 = var1.getViewVector(1.0F).normalize();
      Vec3 var6 = mc.player.getEyePosition(1.0F).subtract(var4).normalize();
      double var7 = var5.dot(var6);
      return var7 >= Math.cos(Math.toRadians(var2));
   }

   private boolean pm$73() {
      long var1 = mc.getWindow().handle();
      boolean var3 = GLFW.glfwGetMouseButton(var1, 1) == 1;
      return !this.f285.m215() || var3;
   }

   private Entity pm$67(double var1) {
      AABB var3 = mc.player.getBoundingBox().inflate(var1);
      Entity var4 = null;
      double var5 = Double.MAX_VALUE;

      for (Entity var8 : mc.level.getEntities(mc.player, var3)) {
         if (var8 instanceof Fireball || var8 instanceof SmallFireball || var8 instanceof LargeFireball) {
            double var9 = this.pm$66(var8, this.f300, this.f301, 10.0);
            if (var9 <= var1 && var9 < var5) {
               var4 = var8;
               var5 = var9;
            }
         }
      }

      return var4;
   }

   @Override
   public void onDisable() {
      this.pm$72();
   }

   private void pm$69(Entity var1) {
      double var2;
      double var4;
      double var6;
      if (this.f277.m228(f257)) {
         AABB var8 = var1.getBoundingBox();
         var2 = Mth.clamp(mc.player.getX(), var8.minX, var8.maxX);
         var4 = Mth.clamp(mc.player.getEyeY(), var8.minY, var8.maxY);
         var6 = Mth.clamp(mc.player.getZ(), var8.minZ, var8.maxZ);
      } else {
         var2 = var1.getX();
         var4 = var1.getEyeY();
         var6 = var1.getZ();
      }

      double var18 = var2 - mc.player.getX();
      double var10 = var4 - mc.player.getEyeY();
      double var12 = var6 - mc.player.getZ();
      double var14 = Math.sqrt(var18 * var18 + var12 * var12);
      float var16 = (float)Math.toDegrees(Math.atan2(var12, var18)) - 90.0F;
      float var17 = (float)(-Math.toDegrees(Math.atan2(var10, var14)));
      var16 = mc.player.getYRot() + Mth.wrapDegrees(var16 - mc.player.getYRot());
      this.f300 = this.pm$71(var16, mc.player.getYRot());
      this.f301 = this.pm$71(var17, mc.player.getXRot());
   }

   private void pm$60() {
      this.f297 = -1;
      this.f298 = -1;

      for (int var1 = 0; var1 < 9; var1++) {
         if (mc.player.getInventory().getItem(var1).is(ItemTags.SWORDS)) {
            if (this.f297 != -1) {
               this.f298 = var1;
               break;
            }

            this.f297 = var1;
         }
      }
   }

   public KillAura() {
      super(f254, Category.COMBAT);
      this.f278 = new ModeSetting(f258, this, f259, new String[]{f259, f260, f261, f262, f263, f264, f265, f266});
      this.f279 = new NumberSetting(f267, this, 3.0, 0.0, 10.0, 1.0);
      this.f280 = new NumberSetting(f268, this, 3.0, 0.0, 10.0, 1.0);
      this.f281 = new NumberSetting(f269, this, 5.0, 3.0, 10.0, 0.5);
      this.f282 = new BooleanSetting(f270, this, true);
      this.f283 = new BooleanSetting(f271, this, false);
      this.f284 = new BooleanSetting(f272, this, false);
      this.f285 = new BooleanSetting(f273, this, false);
      this.f286 = new BooleanSetting(f274, this, true);
      this.f287 = new BooleanSetting(f275, this, false);
      this.f297 = -1;
      this.f298 = -1;
   }

   private double pm$66(Entity var1, float var2, float var3, double var4) {
      Vec3 var6 = mc.player.getEyePosition(1.0F);
      AABB var7 = var1.getBoundingBox();
      if (var7.contains(var6)) {
         return 0.0;
      } else {
         Vec3 var8 = this.pm$68(var3, var2);
         Vec3 var9 = new Vec3(var6.x + var8.x * var4, var6.y + var8.y * var4, var6.z + var8.z * var4);
         Optional var10 = var7.clip(var6, var9);
         return var10.isPresent() ? var6.distanceTo((Vec3)var10.get()) : -0.1;
      }
   }
}
