package com.cryptix.config;

import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.ModeSetting;
import com.cryptix.setting.NumberSetting;
import com.cryptix.setting.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;

public class ConfigManager {
   private static final String f754 = "cryptix/configs";
   private static final String f748 = "enabled";
   private static final String f753 = new String(new byte[0], StandardCharsets.UTF_8);
   private static final String f752 = ".json";
   private static final Gson f755 = new GsonBuilder().setPrettyPrinting().create();
   private static final String f749 = "hidden";
   private static final String f750 = "key";
   private static final File f756 = new File(Minecraft.getInstance().gameDirectory, f754);
   private static final String f751 = "settings";

   public static void init() {
      if (!f756.exists()) {
         f756.mkdirs();
      }
   }

   public static boolean m38(String var0) {
      return m29(var0).exists();
   }

   public static void m34(String var0, JsonObject var1) {
      try (FileWriter var2 = new FileWriter(m29(var0))) {
         f755.toJson(var1, var2);
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public static File getConfigDir() {
      return f756;
   }

   public static File m29(String var0) {
      return new File(f756, var0 + ".json");
   }

   public static void m33(String var0) {
      try {
         JsonObject var1 = m35(var0);

         for (Module var3 : ModuleManager.getModules()) {
            if (var1.has(var3.getName())) {
               JsonObject var4 = var1.getAsJsonObject(var3.getName());
               var3.setEnabled(var4.get(f748).getAsBoolean());
               var3.setHidden(var4.get(f749).getAsBoolean());
               var3.setKey(var4.get(f750).getAsInt());
               JsonObject var5 = var4.getAsJsonObject(f751);

               for (Setting var7 : var3.settings) {
                  if (var5.has(var7.getName())) {
                     if (var7 instanceof BooleanSetting var8) {
                        var8.m217(var5.get(var7.getName()).getAsBoolean());
                     } else if (var7 instanceof NumberSetting var9) {
                        var9.m223(var5.get(var7.getName()).getAsDouble());
                     } else if (var7 instanceof ModeSetting var10) {
                        var10.m226(var5.get(var7.getName()).getAsString());
                     }
                  }
               }
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }
   }

   public static void m31(String var0) {
      for (Module var2 : ModuleManager.getModules()) {
         var2.setEnabled(false);
         var2.setHidden(false);
         var2.setKey(var2.getDefaultKey());

         for (Setting var4 : var2.settings) {
            if (var4 instanceof BooleanSetting var5) {
               ((BooleanSetting)var4).m217(var5.m216());
            } else if (var4 instanceof NumberSetting var6) {
               ((NumberSetting)var4).m223(var6.m221());
            } else if (var4 instanceof ModeSetting var7) {
               ((ModeSetting)var4).m226(var7.m225());
            }
         }
      }

      m32(var0);
   }

   public static JsonObject m35(String var0) {
      File var1 = m29(var0);
      if (!var1.exists()) {
         return new JsonObject();
      } else {
         try {
            JsonObject var3;
            try (FileReader var2 = new FileReader(var1)) {
               var3 = JsonParser.parseReader(var2).getAsJsonObject();
            }

            return var3;
         } catch (Exception var7) {
            var7.printStackTrace();
            return new JsonObject();
         }
      }
   }

   public static boolean m36(String var0) {
      return m29(var0).delete();
   }

   public static void m32(String var0) {
      JsonObject var1 = new JsonObject();

      for (Module var3 : ModuleManager.getModules()) {
         JsonObject var4 = new JsonObject();
         var4.addProperty(f748, var3.isEnabled());
         var4.addProperty(f749, var3.isHidden());
         var4.addProperty(f750, var3.getKey());
         JsonObject var5 = new JsonObject();

         for (Setting var7 : var3.settings) {
            if (var7 instanceof BooleanSetting var8) {
               var5.addProperty(var7.getName(), var8.m215());
            } else if (var7 instanceof NumberSetting var9) {
               var5.addProperty(var7.getName(), var9.m220());
            } else if (var7 instanceof ModeSetting var10) {
               var5.addProperty(var7.getName(), var10.m224());
            }
         }

         var4.add(f751, var5);
         var1.add(var3.getName(), var4);
      }

      m34(var0, var1);
   }

   public static String[] getConfigNames() {
      File[] var0 = f756.listFiles((var0x, var1x) -> var1x.endsWith(f752));
      if (var0 == null) {
         return new String[0];
      } else {
         String[] var1 = new String[var0.length];

         for (int var2 = 0; var2 < var0.length; var2++) {
            var1[var2] = var0[var2].getName().replace(f752, f753);
         }

         return var1;
      }
   }
}
