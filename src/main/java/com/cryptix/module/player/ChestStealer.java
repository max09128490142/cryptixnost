package com.cryptix.module.player;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;

public class ChestStealer extends Module {
   private static final String f479 = new String(new byte[0], StandardCharsets.UTF_8);
   private final BooleanSetting f481;
   private static final String f476 = "Delay";
   private final NumberSetting f480 = new NumberSetting(f476, this, 2.0, 0.0, 5.0, 1.0);
   private static final String f475 = "ChestStealer";
   private int f482;
   private static final String f477 = "Hypixel Chest";
   private static final String f478 = "Chest";

   public ChestStealer() {
      super(f475, Category.PLAYER);
      this.f481 = new BooleanSetting(f477, this, false);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         if (mc.player.containerMenu instanceof ChestMenu var2) {
            if (this.f481.m215() && mc.gui.screen() != null) {
               Component var6 = mc.gui.screen().getTitle();
               if (!var6.getString().equals(f478) && !var6.getString().equals(f479)) {
                  this.f482 = 0;
                  return;
               }
            }

            this.f482++;
            if ((double)this.f482 > this.f480.m220()) {
               boolean var7 = true;

               for (int var4 = 0; var4 < var2.getContainer().getContainerSize(); var4++) {
                  ItemStack var5 = var2.getSlot(var4).getItem();
                  if (!var5.isEmpty()) {
                     mc.gameMode.handleContainerInput(var2.containerId, var4, 0, ContainerInput.QUICK_MOVE, mc.player);
                     this.f482 = 0;
                     var7 = false;
                     break;
                  }
               }

               if (var7) {
                  mc.player.closeContainer();
               }
            }
         } else {
            this.f482 = 0;
         }
      }
   }
}
