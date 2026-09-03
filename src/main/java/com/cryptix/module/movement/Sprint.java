package com.cryptix.module.movement;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import java.nio.charset.StandardCharsets;

public class Sprint extends Module {
   private static final String f426 = "Sprint";

   public Sprint() {
      super(f426, Category.MOVEMENT);
   }

   @Override
   public void onDisable() {
      mc.options.keySprint.setDown(false);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         mc.options.keySprint.setDown(true);
      }
   }
}
