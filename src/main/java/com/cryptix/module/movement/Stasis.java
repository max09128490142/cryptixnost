package com.cryptix.module.movement;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;

public class Stasis extends Module {
   private static final String f428 = "Delay";
   private int f430;
   private static final String f427 = "Stasis";
   private final NumberSetting f429 = new NumberSetting(f428, this, 15.0, 10.0, 50.0, 1.0);

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         this.f430++;
         if ((double)this.f430 >= this.f429.m220()) {
            this.f430 = 0;
            return;
         }

         var1.setCancelled(true);
      }

      if (var1 == Events.f1) {
         mc.player.yRotO = mc.player.getYRot();
         mc.player.xRotO = mc.player.getXRot();
      }
   }

   public Stasis() {
      super(f427, Category.MOVEMENT);
   }

   @Override
   public void onEnable() {
      this.f430 = 0;
   }
}
