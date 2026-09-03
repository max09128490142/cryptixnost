package com.cryptix.module.visual;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.event.impl.EventRender2D;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.ModeSetting;
import com.cryptix.setting.NumberSetting;
import com.cryptix.ui.hud.HudEditScreen;
import com.cryptix.util.ClientColors;
import com.cryptix.util.ColorUtil;
import java.nio.charset.StandardCharsets;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class HudModule extends Module {
   private static final String f642 = HudModule.f630.substring(1);
   private static final String f620 = "HUD";
   private int f639;
   private static final String f627 = "Edit Position";
   private static final String f624 = "Right";
   private int f638;
   private static final String f623 = "Left";
   private static final String f626 = "Fade Distance";
   private static final String f629 = "Watermark";
   private static final String f630 = "Cryptix";
   private final NumberSetting f632;
   private static final String f622 = "Disabled";
   private static final String f621 = "Outline";
   private static final String f641 = f630.substring(0, 1);
   private static final String f625 = "Full";
   private int f640;
   private final BooleanSetting f635;
   private static int f643;
   private final ModeSetting f631 = new ModeSetting(f621, this, f622, new String[]{f622, f623, f624, f625});
   private int f637;
   private static final String f628 = "Background";
   private final BooleanSetting f633;
   private final HudEditScreen f636;
   private final BooleanSetting f634;

   public void m202(int var1, int var2) {
      this.f637 = var1;
      this.f638 = var2;
   }

   public int m199() {
      return this.f638;
   }

   public int m198() {
      return this.f637;
   }

   @Override
   public void onEnable() {
      f643 = mc.font.width(f641);
   }

   public int m200() {
      return this.f639;
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3 && this.f633.m215()) {
         mc.gui.setScreen(this.f636);
         this.f633.m217(!this.f633.m215());
      }

      if (var1 == Events.f5) {
         if (mc.gui.screen() == this.f636) {
            return;
         }

         EventRender2D var2 = Events.f5;
         GuiGraphicsExtractor var3 = var2.m89();
         this.m197(var3);
      }
   }

   public int m201() {
      return this.f640;
   }

   public HudModule() {
      super(f620, Category.VISUAL);
      this.f632 = new NumberSetting(f626, this, 1.0, 0.2, 2.0, 0.1);
      this.f633 = new BooleanSetting(f627, this, false);
      this.f634 = new BooleanSetting(f628, this, false);
      this.f635 = new BooleanSetting(f629, this, false);
      this.f636 = new HudEditScreen(this);
   }

   public void m197(GuiGraphicsExtractor var1) {
      if (this.f635.m215()) {
         var1.text(mc.font, f641, 5, 5, ColorUtil.m27());
         var1.text(mc.font, f642, 5 + f643, 5, -1);
      }

      int var2 = this.f638 + 5;
      int var3 = mc.getWindow().getGuiScaledWidth();
      int var4 = -1;
      int var5 = 0;
      int var6 = 0;
      int var7 = 9 + 1;
      int var8 = var3 - 5 + this.f637;
      String var9 = this.f631.m224();
      boolean var10 = this.f634.m215();
      boolean var11 = !var9.equals(f622);
      List var12 = ModuleManager.m18();
      float var13 = (float)this.f632.m220();
      int var14 = 0;

      for (int var15 = var12.size(); var14 < var15; var14++) {
         Module var16 = (Module)var12.get(var14);
         if (var16.isEnabled() && !var16.isHidden()) {
            var5++;
            String var17 = var16.getDisplayName();
            int var18 = mc.font.width(var17);
            var6 = var18;
            if (var5 == 1) {
               this.f639 = var18;
            }

            int var19 = var8 - var18;
            if (var10) {
               var1.fill(var19 - 3, var2 - 2, var3 - 2 + this.f637, var2 + 9 + 1, Integer.MIN_VALUE);
            }

            int var20 = ClientColors.m52((int)((double)(var2 - this.f638) * 0.5 * (double)var13));
            if (var11) {
               byte var22 = -1;
               int var10000 = var9.hashCode();
               if (var10000 == 2201263) {
                  if (var9.equals(f625)) {
                     var22 = 2;
                  }
               } else if (var10000 == 2364455) {
                  if (var9.equals(f623)) {
                     var22 = 0;
                  }
               } else if (var10000 == 78959100) {
                  if (var9.equals(f624)) {
                     var22 = 1;
                  }
               }

               switch (var22) {
                  case 0:
                     var1.fill(var19 - 4, var2 - 2, var19 - 2, var2 + var7, var20);
                     break;
                  case 1:
                     var1.fill(var3 - 3, var2 - 2, var3 - 1 + this.f637, var2 + var7, var20);
                     break;
                  case 2:
                     if (var2 == 5 + this.f638) {
                        var1.fill(var19 - 4, var2 - 3, var3 - 2 + this.f637, var2 - 2, var20);
                     }

                     if (var4 != -1) {
                        int var23 = var18 - var4;
                        var1.fill(var19 - 3, var2 - 3, var19 + var23 - 3, var2 - 2, var20);
                     }

                     var1.fill(var19 - 4, var2 - 2, var19 - 3, var2 + var7, var20);
                     var1.fill(var8 + 2, var2 - 2, var8 + 3, var2 + 10, var20);
               }
            }

            var1.text(mc.font, var17, var19, var2, var20);
            var2 += 12;
            var4 = var18;
            this.f640 = var5 * 12;
         }
      }

      if (this.f631.m228(f625) && var6 != 0) {
         var14 = var3 - var6 - 5 + this.f637;
         int var26 = 9 + 1;
         var1.fill(
            var14 - 4, var2 + var26 - 12, var3 - 2 + this.f637, var2 + var26 - 11, ClientColors.m52((int)((double)(var2 - this.f638) * 0.5 * (double)var13))
         );
      }
   }
}
