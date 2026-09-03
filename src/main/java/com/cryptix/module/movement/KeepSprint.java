package com.cryptix.module.movement;

import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;

public class KeepSprint extends Module {
   private static final String f376 = "KeepSprint";
   private NumberSetting f378;
   private static final String f377 = "Speed";

   public KeepSprint() {
      super(f376, Category.MOVEMENT);
      this.f378 = new NumberSetting(f377, this, 0.6, 0.6, 1.0, 0.05);
   }

   public double m156() {
      return !this.isEnabled() ? 0.6 : this.f378.m220();
   }
}
