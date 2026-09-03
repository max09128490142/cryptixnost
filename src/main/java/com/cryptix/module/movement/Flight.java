package com.cryptix.module.movement;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;

public class Flight extends Module {
   private static final String f371 = "Speed";
   private static final String f370 = "Flight";
   private NumberSetting f372 = new NumberSetting(f371, this, 1.0, 1.0, 10.0, 0.5);

   public Flight() {
      super(f370, Category.MOVEMENT);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f1) {
         mc.player.getAbilities().flying = true;
         mc.player.getAbilities().setFlyingSpeed((float)this.f372.m220() * 0.1F);
      }
   }
}
