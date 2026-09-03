package com.cryptix.setting;

import com.cryptix.module.Module;

public class ModeSetting extends Setting {
   private final String[] f736;
   private String f737;
   private String f738;

   public String m224() {
      return this.f737;
   }

   public String[] m227() {
      return this.f736;
   }

   public String m225() {
      return this.f738;
   }

   public boolean m228(String var1) {
      return this.f737.equals(var1);
   }

   public ModeSetting(String var1, Module var2, String var3, String[] var4) {
      super(var1, var2);
      this.f737 = var3;
      this.f738 = this.f737;
      this.f736 = var4;
   }

   public void m226(String var1) {
      this.f737 = var1;
   }
}
