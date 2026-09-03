package com.cryptix.module.player;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.NumberSetting;
import com.cryptix.util.InventoryUtil;
import java.nio.charset.StandardCharsets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.glfw.GLFW;

public class Eagle extends Module {
   private final BooleanSetting f489;
   private boolean f492;
   private final NumberSetting f491;
   private final BooleanSetting f490;
   private static final String f484 = "Block Check";
   private static final String f485 = "Pitch Check";
   private final BooleanSetting f488 = new BooleanSetting(f484, this, true);
   private int f494;
   private static final String f483 = "Eagle";
   private static final String f486 = "Fix Double Sneak";
   private static final String f487 = "Delay";
   private int f493;

   private void pm$87(BlockState var1) {
      if (var1.isAir() && mc.player.onGround()) {
         mc.options.keyShift.setDown(true);
         this.f492 = true;
      } else if (this.f492) {
         this.f494++;
         if ((double)this.f494 >= this.f491.m220()) {
            this.pm$88();
         }
      }
   }

   public Eagle() {
      super(f483, Category.PLAYER);
      this.f489 = new BooleanSetting(f485, this, true);
      this.f490 = new BooleanSetting(f486, this, false);
      this.f491 = new NumberSetting(f487, this, 1.0, 0.0, 4.0, 1.0);
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         if (this.f488.m215() && !InventoryUtil.m40()) {
            this.pm$88();
            return;
         }

         if (this.f489.m215() && mc.player.getXRot() < 70.0F) {
            this.pm$88();
            return;
         }

         double var2 = mc.player.getX();
         double var4 = mc.player.getY() - 0.05;
         double var6 = mc.player.getZ();
         BlockState var8 = mc.level.getBlockState(BlockPos.containing(var2, var4, var6));
         if (!this.f490.m215()) {
            this.pm$87(var8);
         } else {
            this.f493++;
            if (this.f493 >= 4) {
               this.pm$87(var8);
            } else {
               this.pm$88();
            }
         }
      }
   }

   private void pm$88() {
      if (this.f492) {
         this.f493 = 0;
         this.f494 = 0;
         long var1 = mc.getWindow().handle();
         boolean var3 = GLFW.glfwGetKey(var1, 340) == 1 || GLFW.glfwGetKey(var1, 344) == 1;
         if (!var3) {
            mc.options.keyShift.setDown(false);
         }

         this.f492 = false;
      }
   }
}
