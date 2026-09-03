package com.cryptix.module.movement;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.BooleanSetting;
import java.nio.charset.StandardCharsets;

public class Speed extends Module {
   private static final String f423 = "Jump";
   private static final String f422 = "Rotate";
   private static final String f421 = "Speed";
   private BooleanSetting f424 = new BooleanSetting(f422, this, false);
   private BooleanSetting f425 = new BooleanSetting(f423, this, false);

   @Override
   public int getPriority(Event var1) {
      return var1 == Events.f3 ? -3 : 0;
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3
         && this.f424.m215()
         && !mc.options.keyRight.isDown()
         && !mc.options.keyLeft.isDown()
         && (!mc.player.onGround() || !mc.options.keyJump.isDown() && !this.f425.m215())) {
         Events.f3.m77(mc.player.getYRot() + 45.0F);
      }

      if (var1 == Events.f8 && this.f425.m215()) {
         mc.player.input.makeJump();
      }
   }

   public Speed() {
      super(f421, Category.MOVEMENT);
   }
}
