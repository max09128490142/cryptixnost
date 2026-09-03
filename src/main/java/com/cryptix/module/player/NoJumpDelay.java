package com.cryptix.module.player;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import java.nio.charset.StandardCharsets;
import mixins.LivingEntityAccessor;

public class NoJumpDelay extends Module {
   private static final String f538 = "NoJumpDelay";

   public NoJumpDelay() {
      super(f538, Category.PLAYER);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f7) {
         ((LivingEntityAccessor)mc.player).setNoJumpDelay(0);
      }
   }
}
