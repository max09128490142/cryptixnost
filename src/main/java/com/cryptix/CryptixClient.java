package com.cryptix;

import com.cryptix.command.CommandManager;
import com.cryptix.config.ConfigManager;
import com.cryptix.module.ModuleManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class CryptixClient implements ModInitializer {
   public void onInitialize() {
      ModuleManager.registerModules();
      CommandManager.m6();
      ConfigManager.init();
   }

   public static void sendMessage(String var0) {
      Minecraft.getInstance().player.sendSystemMessage(Component.literal(var0));
   }

   public static void sendPrefixedMessage(String var0) {
      var0 = "§f[§aCryptix§f] " + var0;
      Minecraft.getInstance().player.sendSystemMessage(Component.literal(var0));
   }
}
