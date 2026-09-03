package com.cryptix.module.visual;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.event.impl.EventRender2D;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.module.combat.KillAura;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.ui.hud.TargetHudEditScreen;
import com.cryptix.util.ColorUtil;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class TargetHudModule extends Module {
   private static final String f676 = "HP: 4";
   private float f698;
   private String f706;
   private static final String f665 = "Show Win or Loss";
   private Player f703;
   private final BooleanSetting f693;
   private static final String f666 = "Health Animation";
   private static final String f688 = "HP: 16";
   private static final String f685 = "HP: 13";
   private float f700;
   private static final String f671 = "HP: 20";
   private static final String f691 = "HP: 19";
   private final BooleanSetting f696;
   private static final String f682 = "HP: 10";
   private Player f705;
   private final BooleanSetting f692;
   private int f708;
   private static final String f664 = "Edit Position";
   private int f701;
   private static final String f677 = "HP: 5";
   private static final String f674 = "HP: 2";
   private static final String f683 = "HP: 11";
   private static final String f681 = "HP: 9";
   private int f702;
   private int f709;
   private float f697;
   private static final String f687 = "HP: 15";
   private final BooleanSetting f695;
   private static final String f690 = "HP: 18";
   private static final String f668 = "\u00a7aW";
   private static final String f663 = "Outline";
   private TargetHudEditScreen f712;
   private static final String f675 = "HP: 3";
   private static final String f689 = "HP: 17";
   private Identifier f704;
   private static final String[] f713 = new String[]{
      TargetHudModule.f672,
      TargetHudModule.f673,
      f674,
      f675,
      f676,
      f677,
      TargetHudModule.f678,
      TargetHudModule.f679,
      TargetHudModule.f680,
      f681,
      f682,
      f683,
      TargetHudModule.f684,
      f685,
      TargetHudModule.f686,
      f687,
      f688,
      f689,
      f690,
      f691,
      f671
   };
   private static final String f667 = new String(new byte[0], StandardCharsets.UTF_8);
   private static final String f662 = "Theme Color";
   private static final String f680 = "HP: 8";
   private static final String f672 = "HP: 0";
   private static final String f679 = "HP: 7";
   private final BooleanSetting f694;
   private static final String f684 = "HP: 12";
   private static final String f669 = "\u00a7cL";
   private static final String f686 = "HP: 14";
   private int f707;
   private static final String f678 = "HP: 6";
   private int f711;
   private static final String f673 = "HP: 1";
   private int f710;
   private float f699;
   private static final String f670 = "Player";
   private static final String f661 = "TargetHUD";

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f5) {
         if (mc.gui.screen() == this.f712) {
            return;
         }

         this.render(Events.f5);
      }

      if (var1 == Events.f3) {
         if (this.f694.m215()) {
            mc.gui.setScreen(this.f712);
            this.f694.m217(!this.f694.m215());
         }

         KillAura var2 = ModuleManager.f26;
         boolean var3 = var2 != null && var2.f17 instanceof Player;
         float var4 = var3 ? 255.0F : 0.0F;
         this.f702 = this.f701;
         this.f701 = (int)Mth.lerp(0.5F, (float)this.f701, var4);
         if (!var3) {
            return;
         }

         Player var5 = (Player)var2.f17;
         this.f699 = this.f698;
         this.f698 = this.f697;
         this.f697 = this.f700;
         this.f700 = var5.getHealth();
      }
   }

   public int m209() {
      return this.f709;
   }

   public int m208() {
      return this.f708;
   }

   public void m207(GuiGraphicsExtractor var1, float var2, Player var3) {
      int var4 = mc.getWindow().getGuiScaledWidth();
      int var5 = mc.getWindow().getGuiScaledHeight();
      int var6 = (var4 >> 1) + this.f708;
      int var7 = (var5 >> 1) + this.f709;
      String var8 = f670;
      int var9 = mc.font.width(var8);
      int var10 = Math.max(80, 38 + var9);
      this.f710 = var10;
      this.f711 = 42;
      byte var11 = -1;
      var1.fill(var6, var7, var6 + var10, var7 + 42, -1442840576);
      var1.text(mc.font, var8, var6 + 34, var7 + 5, -1, false);
      var1.text(mc.font, f671, var6 + var10 - 35, var7 + 22, var11, false);
      var1.fill(var6 + 4, var7 + 34, var6 + 8 + var10 - 12, var7 + 38, var11);
   }

   private void render(EventRender2D var1) {
      KillAura var2 = ModuleManager.f26;
      if (var2 != null) {
         boolean var3 = var2.f17 instanceof Player || this.f703 != null && this.f701 > 10;
         if (var3) {
            Player var4 = var2.f17 instanceof Player ? (Player)var2.f17 : this.f703;
            if (this.f705 != var4) {
               this.f705 = var4;
               this.f704 = null;
               this.f706 = var4.getDisplayName().getString();
               this.f707 = mc.font.width(this.f706);
               mc.getSkinManager().get(var4.getGameProfile()).thenAccept(var1x -> var1x.ifPresent(var1xx -> this.f704 = var1xx.body().id()));
            }

            int var5 = mc.getWindow().getGuiScaledWidth();
            int var6 = mc.getWindow().getGuiScaledHeight();
            int var7 = (var5 >> 1) + this.m208();
            int var8 = (var6 >> 1) + this.m209();
            int var9 = Math.max(80, 48 + this.f707);
            float var10 = Mth.lerp(var1.m91(), this.f699, this.f698);
            float var11 = Mth.lerp(var1.m91(), this.f697, this.f700);
            float var12 = var4.getMaxHealth();
            float var13 = var11 / var12;
            float var14 = var10 / var12;
            GuiGraphicsExtractor var15 = var1.m89();
            int var16 = ColorUtil.m27();
            int var17 = this.f692.m215() ? var16 : ColorUtil.m26(var13);
            int var18 = this.f692.m215() ? var16 : ColorUtil.m26(var13);
            var18 = var18 & 16777215 | 1677721600;
            int var19 = (int)((float)this.f702 + (float)(this.f701 - this.f702) * var1.m91());
            var17 = var19 << 24 | var17 & 16777215;
            int var20 = var19 * 170 / 255 << 24;
            int var21 = Math.round(var11);
            if (var21 < 0) {
               var21 = 0;
            } else if (var21 > 20) {
               var21 = 20;
            }

            String var22 = f713[var21];
            var15.fill(var7, var8, var7 + var9, var8 + 42, var20);
            if (this.f693.m215()) {
               var15.fill(var7, var8, var7 + 1, var8 + 42, var19 << 24 | var16 & 16777215);
               var15.fill(var7 + var9 - 1, var8, var7 + var9, var8 + 42, var19 << 24 | var16 & 16777215);
               var15.fill(var7, var8, var7 + var9, var8 + 1, var19 << 24 | var16 & 16777215);
               var15.fill(var7, var8 + 41, var7 + var9, var8 + 42, var19 << 24 | var16 & 16777215);
            }

            if (this.f696.m215() && this.f701 > 200) {
               var15.fill(var7 + 4, var8 + 34, var7 + 8 + (int)((float)(var9 - 12) * var14), var8 + 38, var18);
            }

            var15.fill(var7 + 4, var8 + 34, var7 + 8 + (int)((float)(var9 - 12) * var13), var8 + 38, var17);
            if (this.f695.m215()) {
               String var23 = var4.getHealth() <= mc.player.getHealth() ? f668 : f669;
               var15.text(mc.font, var23, var7 + var9 - 10, var8 + 5, var19 << 24 | 16777215, false);
            }

            var15.text(mc.font, this.f706, var7 + 34, var8 + 5, var19 << 24 | 16777215, false);
            var15.text(mc.font, var22, var7 + var9 - 35, var8 + 22, var17, false);
            if (this.f704 != null) {
               var15.blit(RenderPipelines.GUI_TEXTURED, this.f704, var7 + 4, var8 + 4, 8.0F, 8.0F, 26, 26, 8, 8, 64, 64, var19 << 24 | 16777215);
            }

            this.f703 = var4;
         }
      }
   }

   public int m210() {
      return this.f710;
   }

   public int m211() {
      return this.f711;
   }

   public TargetHudModule() {
      super(f661, Category.VISUAL);
      this.f692 = new BooleanSetting(f662, this, false);
      this.f693 = new BooleanSetting(f663, this, false);
      this.f694 = new BooleanSetting(f664, this, false);
      this.f695 = new BooleanSetting(f665, this, false);
      this.f696 = new BooleanSetting(f666, this, false);
      this.f706 = f667;
      this.f712 = new TargetHudEditScreen(this);
   }

   public void m212(int var1, int var2) {
      this.f708 = var1;
      this.f709 = var2;
   }
}
