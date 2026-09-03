package com.cryptix.module.visual;

import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;

public class Animations extends Module {
   public NumberSetting f28;
   private static final String f597 = "Scale";
   public NumberSetting f27 = new NumberSetting(f597, this, 1.0, 0.1, 2.0, 0.1);
   private static final String f598 = "X";
   private static final String f600 = "Z";
   public NumberSetting f29;
   private static final String f599 = "Y";
   private static final String f596 = "Animations";
   public NumberSetting f30;

   public Animations() {
      super(f596, Category.VISUAL);
      this.f28 = new NumberSetting(f598, this, 0.0, -2.0, 2.0, 0.05);
      this.f29 = new NumberSetting(f599, this, 0.0, -2.0, 2.0, 0.05);
      this.f30 = new NumberSetting(f600, this, 0.0, -2.0, 2.0, 0.05);
   }
}
