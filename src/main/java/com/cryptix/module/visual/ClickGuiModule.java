package com.cryptix.module.visual;

import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.ui.clickgui.ClickGuiScreen;
import java.nio.charset.StandardCharsets;

public class ClickGuiModule extends Module {
   private static final String f616 = "ClickGUI";
   private ClickGuiScreen f617;

   @Override
   public void onEnable() {
      if (this.f617 == null) {
         this.f617 = new ClickGuiScreen();
      }

      mc.gui.setScreen(this.f617);
      this.setEnabled(false);
   }

   public ClickGuiModule() {
      super(f616, 344, Category.VISUAL);
   }
}
