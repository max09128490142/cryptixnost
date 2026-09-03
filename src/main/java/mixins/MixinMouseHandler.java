package mixins;

import com.cryptix.event.Events;
import com.cryptix.event.impl.EventMouseButton;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MouseHandler.class})
public class MixinMouseHandler {
   @Inject(
      method = {"onButton"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void pm$41(long var1, MouseButtonInfo var3, int var4, CallbackInfo var5) {
      EventMouseButton var6 = Events.f15.m12(var3.button(), var4, var3.modifiers());
      var6.call();
      if (var6.isCancelled()) {
         var5.cancel();
      }
   }
}
