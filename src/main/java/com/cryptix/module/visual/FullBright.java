package com.cryptix.module.visual;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import java.nio.charset.StandardCharsets;
import mixins.OptionInstanceAccessor;

public class FullBright extends Module {
   private double f619;
   private static final String f618 = "FullBright";

   public FullBright() {
      super(f618, Category.VISUAL);
   }

   @Override
   public void onEnable() {
      this.f619 = (Double)mc.options.gamma().get();
      ((OptionInstanceAccessor)(Object)mc.options.gamma()).setValue(100.0);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         ((OptionInstanceAccessor)(Object)mc.options.gamma()).setValue(100.0);
      }
   }

   @Override
   public void onDisable() {
      mc.options.gamma().set(this.f619);
   }
}
