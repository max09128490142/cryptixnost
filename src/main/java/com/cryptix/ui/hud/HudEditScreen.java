package com.cryptix.ui.hud;

import com.cryptix.module.visual.HudModule;
import com.cryptix.util.Wrapper;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class HudEditScreen extends Screen implements Wrapper {
   private double f180;
   private static final String f176 = "Edit the HUD position by dragging.";
   private double f179;
   private boolean f178;
   private static final String f175 = "Reset Position";
   private static final String f174 = "HUD Position";
   private final HudModule f177;

   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      double var6 = var1.x();
      double var8 = var1.y();
      int var10 = var1.button();
      if (this.f178 && var10 == 0) {
         int var11 = Wrapper.mc.getWindow().getGuiScaledWidth();
         short var12 = 150;
         double var13 = var6 - this.f179;
         double var15 = var8 - this.f180;
         int var17 = (int)(var13 + (double)var12 - (double)var11);
         int var18 = (int)var15;
         this.f177.m202(var17, var18);
         return true;
      } else {
         return super.mouseDragged(var1, var2, var4);
      }
   }

   private boolean pm$12(double var1, double var3) {
      int var5 = Wrapper.mc.getWindow().getGuiScaledWidth();
      int var6 = this.f177.m198() + var5 - this.f177.m200() - 10;
      int var7 = this.f177.m199() + 1;
      int var8 = this.f177.m200() + 9;
      int var9 = this.f177.m201() + 3;
      return var1 >= (double)var6 && var1 <= (double)(var6 + var8) && var3 >= (double)var7 && var3 <= (double)(var7 + var9);
   }

   public boolean isPauseScreen() {
      return false;
   }

   public boolean mouseReleased(MouseButtonEvent var1) {
      if (var1.button() == 0 && this.f178) {
         this.f178 = false;
         this.f177.m202(this.f177.m198(), this.f177.m199());
         return true;
      } else {
         return super.mouseReleased(var1);
      }
   }

   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      double var3 = var1.x();
      double var5 = var1.y();
      int var7 = var1.button();
      if (var7 == 0 && this.pm$12(var3, var5)) {
         this.f178 = true;
         int var8 = Wrapper.mc.getWindow().getGuiScaledWidth();
         short var9 = 150;
         double var10 = (double)(var8 + this.f177.m198() - var9);
         this.f179 = var1.x() - var10;
         this.f180 = var1.y() - (double)this.f177.m199();
         return true;
      } else {
         return super.mouseClicked(var1, var2);
      }
   }

   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      var1.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
      this.f177.m197(var1);
      int var5 = Wrapper.mc.getWindow().getGuiScaledWidth();
      int var6 = Wrapper.mc.getWindow().getGuiScaledHeight();
      if (this.pm$12((double)var2, (double)var3)) {
         int var7 = this.f177.m198() + var5 - this.f177.m200() - 10;
         int var8 = this.f177.m199() + 1;
         int var9 = this.f177.m200() + 9;
         int var10 = this.f177.m201() + 3;
         var1.outline(var7, var8, var9, var10, -1);
      }

      String var11 = f176;
      var1.text(Wrapper.mc.font, var11, var5 / 2 - Wrapper.mc.font.width(var11) / 2, var6 / 2, -1);
      if (this.getFocused() != null) {
         this.setFocused(null);
      }

      super.extractRenderState(var1, var2, var3, var4);
   }

   public void init() {
      Button var1 = Button.builder(Component.literal(f175), var1x -> this.f177.m202(0, 0)).bounds(this.width - 90, this.height - 25, 85, 20).build();
      this.addRenderableWidget(var1);
   }

   public HudEditScreen(HudModule var1) {
      super(Component.literal(f174));
      this.f177 = var1;
   }
}
