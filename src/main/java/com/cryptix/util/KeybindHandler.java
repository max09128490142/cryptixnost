package com.cryptix.util;

import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler implements Wrapper {
   private static final boolean[] f48 = new boolean[349];

   public static void m5() {
      if (Wrapper.mc.gui.screen() == null) {
         long var0 = Wrapper.mc.getWindow().handle();
         byte var2 = 1;

         for (Module var4 : ModuleManager.getModules()) {
            int var5 = var4.getKey();
            if (var5 != -1 && var5 != 0) {
               boolean var6 = GLFW.glfwGetKey(var0, var5) == var2;
               if (!var6) {
                  f48[var5] = false;
               } else if (!f48[var5]) {
                  for (Module var8 : ModuleManager.getModules()) {
                     if (var8.getKey() == var5) {
                        var8.toggle();
                     }
                  }

                  f48[var5] = true;
               }
            }
         }
      }
   }
}
