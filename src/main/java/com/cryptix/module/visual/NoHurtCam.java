package com.cryptix.module.visual;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import java.nio.charset.StandardCharsets;

public class NoHurtCam extends Module {
   private static final String f649 = "NoHurtCam";

   public NoHurtCam() {
      super(f649, Category.VISUAL);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f6) {
         var1.setCancelled(true);
      }
   }
}
