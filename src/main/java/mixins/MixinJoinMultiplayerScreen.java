package mixins;

import com.cryptix.protection.TokenLoginScreen;
import com.cryptix.util.VideoPlayer;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({JoinMultiplayerScreen.class})
public class MixinJoinMultiplayerScreen {
   private static final String f209 = "Token Login";

   @Inject(
      method = {"init"},
      at = {@At("TAIL")}
   )
   private void pm$35(CallbackInfo var1) {
      JoinMultiplayerScreen var2 = (JoinMultiplayerScreen)(Object)this;
      ((ScreenAccessor)var2)
         .invokeAddRenderableWidget(
            Button.builder(Component.literal(f209), var1x -> ((ScreenAccessor)var2).getMinecraft().gui.setScreen(new TokenLoginScreen()))
               .bounds(var2.width - 110, 5, 105, 20)
               .build()
         );
   }

   // JoinMultiplayerScreen overrides removed() and does not call super, so the video
   // audio must be stopped here when leaving the screen.
   @Inject(
      method = {"removed"},
      at = {@At("HEAD")}
   )
   private void cryptix$stopVideo(CallbackInfo var1) {
      VideoPlayer.MULTIPLAYER.stop();
   }
}
