package com.cryptix.module.player;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;
import mixins.MultiPlayerGameModeAccessor;

public class FastMine extends Module {
   private final BooleanSetting f499;
   private final NumberSetting f498;
   private static final String f497 = "Remove Delay";
   private static final String f495 = "FastMine";
   private static final String f496 = "Speed";

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         if (mc.gameMode == null) {
            return;
         }

         MultiPlayerGameModeAccessor var2 = (MultiPlayerGameModeAccessor)mc.gameMode;
         if (this.f499.m215()) {
            var2.setDestroyDelay(0);
         }

         float var3 = var2.getDestroyProgress();
         if (var3 > 0.0F && var3 < 0.99F) {
            var2.setDestroyProgress((float)((double)var3 * this.f498.m220()));
         }
      }
   }

   public FastMine() {
      super(f495, Category.PLAYER);
      this.f498 = new NumberSetting(f496, this, 1.5, 1.0, 5.0, 0.1);
      this.f499 = new BooleanSetting(f497, this, true);
   }
}
