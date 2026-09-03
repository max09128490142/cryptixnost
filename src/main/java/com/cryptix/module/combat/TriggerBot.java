package com.cryptix.module.combat;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import java.nio.charset.StandardCharsets;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;

public class TriggerBot extends Module {
   private static final String f314 = "TriggerBot";

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         Entity var3 = mc.crosshairPickEntity;
         if (var3 instanceof Entity && mc.player != null && mc.player.isAlive() && mc.player.getAttackStrengthScale(0.0F) >= 1.0F) {
            mc.gameMode.attack(mc.player, var3);
            mc.player.swing(InteractionHand.MAIN_HAND);
         }
      }
   }

   public TriggerBot() {
      super(f314, Category.COMBAT);
   }
}
