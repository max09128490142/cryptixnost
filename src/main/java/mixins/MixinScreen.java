package mixins;

import com.cryptix.util.VideoPlayer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.ChatOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.MouseSettingsScreen;
import net.minecraft.client.gui.screens.options.OnlineOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinScreen {
   // Draw the looping video stretched over screens that inherit Screen.extractRenderState,
   // behind their widgets (widgets are extracted after this HEAD injection).
   @Inject(method = "extractRenderState", at = @At("HEAD"))
   private void cryptix$menuVideo(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partial, CallbackInfo ci) {
      Screen self = (Screen)(Object)this;
      VideoPlayer video = cryptix$videoFor(self);
      if (video != null) {
         video.render(graphics, self.width, self.height);
      }
   }

   private static VideoPlayer cryptix$videoFor(Screen screen) {
      if (screen instanceof JoinMultiplayerScreen) {
         return VideoPlayer.MULTIPLAYER;
      } else if (screen instanceof OptionsScreen) {
         return VideoPlayer.SETTINGS;
      } else if (screen instanceof VideoSettingsScreen) {
         return VideoPlayer.VIDEO;
      } else if (screen instanceof SelectWorldScreen) {
         return VideoPlayer.SINGLE;
      } else if (screen instanceof SoundOptionsScreen) {
         return VideoPlayer.SOUND;
      } else if (screen instanceof CreateWorldScreen) {
         return VideoPlayer.CREATEWORLD;
      } else if (screen instanceof SkinCustomizationScreen) {
         return VideoPlayer.SKIN;
      } else if (screen instanceof ControlsScreen || screen instanceof MouseSettingsScreen || screen instanceof KeyBindsScreen) {
         return VideoPlayer.CONTROLS;
      } else if (screen instanceof LanguageSelectScreen) {
         return VideoPlayer.LANGUAGE;
      } else if (screen instanceof ChatOptionsScreen) {
         return VideoPlayer.CHAT;
      } else if (screen instanceof PackSelectionScreen) {
         return VideoPlayer.PACKS;
      } else if (screen instanceof AccessibilityOptionsScreen) {
         return VideoPlayer.ACCESSIBILITY;
      } else if (screen instanceof OnlineOptionsScreen) {
         return VideoPlayer.ONLINE;
      } else {
         return null;
      }
   }

   // Sodium's VideoSettingsScreen inherits Screen.removed(); stop its video here (guarded by name).
   @Inject(method = "removed", at = @At("HEAD"))
   private void cryptix$stopVideos(CallbackInfo ci) {
      if (this.getClass().getName().equals("net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen")) {
         VideoPlayer.VIDEO.stop();
      } else if ((Object)this instanceof ManageServerScreen) {
         VideoPlayer.ADDSERVER.stop();
      }
   }
}
