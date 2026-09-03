package com.cryptix.setting;

import com.cryptix.module.Module;

public abstract class Setting {
   private final Module module;
   private final String name;

   public String getName() {
      return this.name;
   }

   public Module getModule() {
      return this.module;
   }

   public Setting(String var1, Module var2) {
      this.name = var1;
      this.module = var2;
      this.module.settings.add(this);
   }
}
