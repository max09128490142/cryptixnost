package com.cryptix.ui.clickgui;

import com.cryptix.module.Category;
import com.cryptix.module.ModuleManager;
import com.cryptix.ui.config.ConfigScreen;
import com.cryptix.ui.config.ConfigScreenBase;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ClickGuiScreen extends Screen {
   private ConfigScreenBase f150;
   private static final String f144 = "Movement";
   private static final String f148 = "Configs";
   private static final String f142 = "ClickGUI";
   private static final String f143 = "Combat";
   private int f152;
   private static final String f147 = "Misc";
   private int f151;
   private static final String f145 = "Player";
   private static final String f146 = "Visual";
   private List<ConfigScreenBase> f149 = new ArrayList<>();

   public ClickGuiScreen() {
      super(Component.literal(f142));
      this.f149.add(new ConfigScreenBase(f143, 50, 50, ModuleManager.m19(Category.COMBAT)));
      this.f149.add(new ConfigScreenBase(f144, 160, 50, ModuleManager.m19(Category.MOVEMENT)));
      this.f149.add(new ConfigScreenBase(f145, 270, 50, ModuleManager.m19(Category.PLAYER)));
      this.f149.add(new ConfigScreenBase(f146, 380, 50, ModuleManager.m19(Category.VISUAL)));
      this.f149.add(new ConfigScreenBase(f147, 490, 50, ModuleManager.m19(Category.MISC)));
      this.f149.add(new ConfigScreen(f148, 600, 50));
   }

   public boolean keyPressed(KeyEvent var1) {
      for (ConfigScreenBase var3 : this.f149) {
         boolean var4 = false;

         for (KeybindButton var6 : var3.m107()) {
            if (var6.f13) {
               var4 = true;
            }
         }

         var3.m104(var1.key());
         if (var4) {
            return false;
         }
      }

      return super.keyPressed(var1);
   }

   public boolean mouseReleased(MouseButtonEvent var1) {
      if (var1.button() == 0) {
         this.f150 = null;

         for (ConfigScreenBase var3 : this.f149) {
            var3.m106();
         }
      }

      return super.mouseReleased(var1);
   }

   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      double var6 = var1.x();
      double var8 = var1.y();

      for (ConfigScreenBase var11 : this.f149) {
         var11.m105(var6, var8);
      }

      if (this.f150 != null) {
         this.f150.f4 = (int)var6 - this.f151;
         this.f150.f5 = (int)var8 - this.f152;
      }

      return super.mouseDragged(var1, var2, var4);
   }

   private void startDrag(ConfigScreenBase var1, double var2, double var4) {
      this.f150 = var1;
      this.f151 = (int)var2 - var1.f4;
      this.f152 = (int)var4 - var1.f5;
   }

   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      super.extractRenderState(var1, var2, var3, var4);
      if (this.f150 != null) {
         if (GLFW.glfwGetMouseButton(this.minecraft.getWindow().handle(), 0) == 1) {
            this.f150.f4 = var2 - this.f151;
            this.f150.f5 = var3 - this.f152;
         } else {
            this.f150 = null;
         }
      }

      for (ConfigScreenBase var6 : this.f149) {
         var6.m100(var1);
      }
   }

   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      double var3 = var1.x();
      double var5 = var1.y();
      int var7 = var1.button();
      if (var7 == 0) {
         for (ConfigScreenBase var9 : this.f149) {
            if (var9.m101(var3, var5)) {
               this.startDrag(var9, var3, var5);
            }
         }
      }

      for (ConfigScreenBase var11 : this.f149) {
         var11.m103(var3, var5, var7);
      }

      return super.mouseClicked(var1, var2);
   }
}
