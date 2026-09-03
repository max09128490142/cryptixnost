package com.cryptix.ui.hud;

import com.cryptix.module.visual.TargetHudModule;
import com.cryptix.util.Wrapper;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class TargetHudEditScreen extends Screen implements Wrapper {
   private final TargetHudModule f184;
   private double f186;
   private boolean f185;
   private static final String f181 = "TargetHUD Position";
   private double f187;
   private static final String f183 = "Edit the TargetHUD position by dragging.";
   private static final String f182 = "Reset Position";

   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      var1.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
      this.f184.m207(var1, var4, Wrapper.mc.player);
      int var5 = Wrapper.mc.getWindow().getGuiScaledWidth();
      int var6 = Wrapper.mc.getWindow().getGuiScaledHeight();
      if (this.pm$13((double)var2, (double)var3)) {
         int var7 = Wrapper.mc.getWindow().getGuiScaledWidth();
         int var8 = Wrapper.mc.getWindow().getGuiScaledHeight();
         int var9 = this.f184.m208() + var7 / 2;
         int var10 = this.f184.m209() + var8 / 2;
         int var11 = this.f184.m210();
         int var12 = this.f184.m211();
         var1.outline(var9, var10, var11, var12, -1);
      }

      String var13 = f183;
      var1.text(Wrapper.mc.font, var13, var5 / 2 - Wrapper.mc.font.width(var13) / 2, var6 / 2 - 25, -1);
      if (this.getFocused() != null) {
         this.setFocused(null);
      }

      super.extractRenderState(var1, var2, var3, var4);
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void init() {
      Button var1 = Button.builder(Component.literal(f182), var1x -> this.f184.m212(0, 0)).bounds(this.width - 90, this.height - 25, 85, 20).build();
      this.addRenderableWidget(var1);
   }

   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      double var6 = var1.x();
      double var8 = var1.y();
      int var10 = var1.button();
      if (this.f185 && var10 == 0) {
         int var11 = Wrapper.mc.getWindow().getGuiScaledWidth();
         short var12 = 150;
         double var13 = var6 - this.f186;
         double var15 = var8 - this.f187;
         int var17 = (int)(var13 + (double)var12 - (double)var11);
         int var18 = (int)var15;
         this.f184.m212(var17, var18);
         return true;
      } else {
         return super.mouseDragged(var1, var2, var4);
      }
   }

   public TargetHudEditScreen(TargetHudModule var1) {
      super(Component.literal(f181));
      this.f184 = var1;
   }

   public boolean mouseReleased(MouseButtonEvent var1) {
      if (var1.button() == 0 && this.f185) {
         this.f185 = false;
         this.f184.m212(this.f184.m208(), this.f184.m209());
         return true;
      } else {
         return super.mouseReleased(var1);
      }
   }

   private boolean pm$13(double var1, double var3) {
      int var5 = Wrapper.mc.getWindow().getGuiScaledWidth();
      int var6 = Wrapper.mc.getWindow().getGuiScaledHeight();
      int var7 = this.f184.m208() + var5 / 2;
      int var8 = this.f184.m209() + var6 / 2;
      int var9 = this.f184.m210();
      int var10 = this.f184.m211();
      return var1 >= (double)var7 && var1 <= (double)(var7 + var9) && var3 >= (double)var8 && var3 <= (double)(var8 + var10);
   }

   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      double var3 = var1.x();
      double var5 = var1.y();
      int var7 = var1.button();
      if (var7 == 0 && this.pm$13(var3, var5)) {
         this.f185 = true;
         int var8 = Wrapper.mc.getWindow().getGuiScaledWidth();
         short var9 = 150;
         double var10 = (double)(var8 + this.f184.m208() - var9);
         this.f186 = var1.x() - var10;
         this.f187 = var1.y() - (double)this.f184.m209();
         return true;
      } else {
         return super.mouseClicked(var1, var2);
      }
   }
}
