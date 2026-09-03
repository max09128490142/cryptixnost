package com.cryptix.module.visual;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;

public class NameTags extends Module {
   private static final String f645 = "Scale";
   private static final String f646 = "Y";
   private static final String f644 = "NameTags";
   private NumberSetting f648;
   private NumberSetting f647 = new NumberSetting(f645, this, 1.0, 0.1, 2.0, 0.1);

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f13) {
         Events.f13.m35(true);
         Events.f13.m34(15728880);
         float var2 = (float)this.f647.m220();
         Events.f13.m29().scale(var2, var2, var2);
         float var3 = (float)this.f647.m220();
         Events.f13.m29().translate(0.0, this.f648.m220(), 0.0);
      }
   }

   public NameTags() {
      super(f644, Category.VISUAL);
      this.f648 = new NumberSetting(f646, this, 0.0, -10.0, 10.0, 0.1);
   }
}
