package com.cryptix.util;

public class MoveDirectionUtil implements Wrapper {
   public static float m45() {
      float var0 = Wrapper.mc.player.getYRot();
      boolean var1 = Wrapper.mc.options.keyUp.isDown();
      boolean var2 = Wrapper.mc.options.keyDown.isDown();
      boolean var3 = Wrapper.mc.options.keyLeft.isDown();
      boolean var4 = Wrapper.mc.options.keyRight.isDown();
      if (var1 && !var2) {
         if (var3 && !var4) {
            var0 -= 45.0F;
         } else if (var4 && !var3) {
            var0 += 45.0F;
         }
      } else if (var2 && !var1) {
         var0 += 180.0F;
         if (var3 && !var4) {
            var0 += 45.0F;
         } else if (var4 && !var3) {
            var0 -= 45.0F;
         }
      } else if (var3 && !var4) {
         var0 -= 90.0F;
      } else if (var4 && !var3) {
         var0 += 90.0F;
      }

      var0 %= 360.0F;
      if (var0 < 0.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static boolean m46() {
      float var0 = m45() % 90.0F;
      return var0 > 20.0F && var0 < 70.0F;
   }
}
