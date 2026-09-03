package com.cryptix.module;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.setting.Setting;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;

public abstract class Module {
   protected static final Minecraft mc = Minecraft.getInstance();
   public List<Setting> settings = new ArrayList<>();
   private boolean enabled;
   private int defaultKey = 0;
   private final Category category;
   private int keyCode;
   private final String name;
   private String suffix;
   private boolean hidden;
   private static final String f218 = new String(new byte[0], StandardCharsets.UTF_8);
   private String displayName;

   public String getDisplayName() {
      return this.displayName;
   }

   public void onEvent(Event var1) {
   }

   public int getDefaultKey() {
      return this.defaultKey;
   }

   public String getName() {
      return this.name;
   }

   public void setKey(int var1) {
      this.keyCode = var1;
   }

   public void setEnabled(boolean var1) {
      if (this.enabled != var1) {
         this.toggle();
      }
   }

   public void onDisable() {
   }

   public int getPriority(Event var1) {
      return 0;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setSuffix(String var1) {
      if (!Objects.equals(this.suffix, var1)) {
         this.suffix = var1;
         this.displayName = var1 == null ? this.name : this.name + "§7 " + var1;
         ModuleManager.sortModules();
      }
   }

   public void setHidden(boolean var1) {
      this.hidden = var1;
   }

   public void toggle() {
      this.enabled = !this.enabled;
      if (this.enabled) {
         this.onEnable();
      } else {
         this.onDisable();
      }

      Events.m11();
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public Module(String var1, int var2, Category var3) {
      this.category = var3;
      this.name = var1;
      this.displayName = var1;
      this.suffix = f218;
      this.keyCode = var2;
      this.defaultKey = var2;
      this.enabled = false;
   }

   public void onEnable() {
   }

   public String getSuffix() {
      return this.suffix;
   }

   public Module(String var1, Category var2) {
      this.category = var2;
      this.name = var1;
      this.displayName = var1;
      this.suffix = f218;
      this.keyCode = 0;
      this.enabled = false;
   }

   public int getKey() {
      return this.keyCode;
   }

   public boolean isHidden() {
      return this.hidden;
   }
}
