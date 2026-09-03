package com.cryptix.command;

import com.cryptix.CryptixClient;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class ToggleCommand extends Command {
   private static final String f76 = "Disabled";
   private static final String f73 = "toggle";
   private static final String f77 = "Invalid module";
   private static final String f75 = "Enabled";
   private static final String f72 = "toggle <name>";
   private static final String f74 = "t";
   private static final String f71 = "Toggle";

   public ToggleCommand() {
      super(f71, f72, new String[]{f73, f74});
   }

   @Override
   public void execute(String[] var1, String var2) {
      if (var1.length > 1) {
         String var3 = var1[1];
         boolean var4 = false;

         for (Module var6 : ModuleManager.getModules()) {
            if (var6.getName().equalsIgnoreCase(var3)) {
               var6.toggle();
               CryptixClient.sendPrefixedMessage((var6.isEnabled() ? f75 : f76) + " " + var3);
               var4 = true;
               break;
            }
         }

         if (!var4) {
            CryptixClient.sendPrefixedMessage(f77);
         }
      } else {
         CryptixClient.sendPrefixedMessage("Usage: ." + this.syntax);
      }
   }

   @Override
   public Collection complete(String[] var1) {
      return var1.length <= 1 ? ModuleManager.getModules().stream().map(Module::getName).toList() : List.of();
   }
}
