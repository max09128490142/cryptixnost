package com.cryptix.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

/**
 * Plays a pre-extracted frame sequence stretched over a screen, with looping audio.
 *
 * <p>The frames live in the mod jar, which Minecraft's resource manager never mounts (see
 * {@link ModTextures}), so each frame is decoded from the classpath and pushed into one
 * {@link DynamicTexture} that is registered under a private Identifier. Only frame changes
 * touch the texture, so a 12 fps video costs 12 uploads per second regardless of framerate.
 */
public final class VideoPlayer {
   // Declared before the players below so their constructors can register themselves.
   private static final java.util.List<VideoPlayer> ALL = new java.util.ArrayList<>();

   public static final VideoPlayer MULTIPLAYER =
      new VideoPlayer("multiplayer", "textures/video/frame_%04d.png", 199, 12, 176, 313, "/assets/cryptix/sounds/video.ogg");
   public static final VideoPlayer DIRECT =
      new VideoPlayer("direct", "textures/videodirect/frame_%04d.png", 174, 12, 176, 313, "/assets/cryptix/sounds/videodirect.ogg");
   public static final VideoPlayer SETTINGS =
      new VideoPlayer("settings", "textures/videosettings/frame_%04d.png", 282, 12, 176, 132, "/assets/cryptix/sounds/videosettings.ogg");
   public static final VideoPlayer VIDEO =
      new VideoPlayer("video", "textures/videovideo/frame_%04d.png", 347, 9, 160, 281, "/assets/cryptix/sounds/videovideo.ogg");
   public static final VideoPlayer SINGLE =
      new VideoPlayer("single", "textures/videosingle/frame_%04d.png", 108, 10, 176, 313, "/assets/cryptix/sounds/videosingle.ogg");
   public static final VideoPlayer ADDSERVER =
      new VideoPlayer("addserver", "textures/videoaddserver/frame_%04d.png", 534, 5, 96, 172, "/assets/cryptix/sounds/videoaddserver.ogg");
   public static final VideoPlayer SOUND =
      new VideoPlayer("sound", "textures/videosound/frame_%04d.png", 330, 10, 176, 322, "/assets/cryptix/sounds/videosound.ogg");
   public static final VideoPlayer CREATEWORLD =
      new VideoPlayer("createworld", "textures/videocreate/frame_%04d.png", 200, 10, 176, 322, "/assets/cryptix/sounds/videocreate.ogg");
   public static final VideoPlayer SKIN =
      new VideoPlayer("skin", "textures/videoskin/frame_%04d.png", 200, 10, 176, 322, "/assets/cryptix/sounds/videoskin.ogg");
   public static final VideoPlayer CONTROLS =
      new VideoPlayer("controls", "textures/videocontrols/frame_%04d.png", 150, 10, 176, 322, "/assets/cryptix/sounds/videocontrols.ogg");
   public static final VideoPlayer LANGUAGE =
      new VideoPlayer("language", "textures/videolang/frame_%04d.png", 140, 10, 176, 322, "/assets/cryptix/sounds/videolang.ogg");
   public static final VideoPlayer CHAT =
      new VideoPlayer("chat", "textures/videochat/frame_%04d.png", 200, 10, 176, 176, "/assets/cryptix/sounds/videochat.ogg");
   public static final VideoPlayer PACKS =
      new VideoPlayer("packs", "textures/videopacks/frame_%04d.png", 170, 10, 176, 312, "/assets/cryptix/sounds/videopacks.ogg");
   public static final VideoPlayer ACCESSIBILITY =
      new VideoPlayer("accessibility", "textures/videoaccess/frame_%04d.png", 140, 10, 176, 322, "/assets/cryptix/sounds/videoaccess.ogg");
   public static final VideoPlayer ONLINE =
      new VideoPlayer("online", "textures/videoonline/frame_%04d.png", 180, 10, 176, 322, "/assets/cryptix/sounds/videoonline.ogg");
   public static final VideoPlayer CLICKGUI =
      new VideoPlayer("clickgui", "textures/videoclickgui/frame_%04d.png", 200, 10, 176, 132, "/assets/cryptix/sounds/videoclickgui.ogg");

   private final Identifier[] frames;
   private final Identifier textureId;
   private final int fps;
   private final int texW;
   private final int texH;
   private final long durationMs;
   private final AudioTrack audio;
   private long startTime = 0L;

   private DynamicTexture texture;
   private int uploadedFrame = -1;
   private long lastRenderMs = 0L;

   public VideoPlayer(String name, String framePathFormat, int frameCount, int fps, int texW, int texH, String audioResource) {
      this.frames = new Identifier[frameCount];
      for (int i = 0; i < frameCount; i++) {
         this.frames[i] = Identifier.fromNamespaceAndPath("cryptix", String.format(framePathFormat, i + 1));
      }
      this.textureId = Identifier.fromNamespaceAndPath("cryptix", "video/" + name);
      this.fps = fps;
      this.texW = texW;
      this.texH = texH;
      this.durationMs = (long)frameCount * 1000L / (long)fps;
      this.audio = new AudioTrack(audioResource);
      ALL.add(this);
   }

   /**
    * Stops any video that no screen has drawn recently. Screens come and go through a dozen
    * different removed() overrides, so letting the video time itself out is far more reliable
    * than hooking each one.
    */
   public static void tickAll() {
      long now = System.currentTimeMillis();
      for (VideoPlayer player : ALL) {
         if (player.startTime != 0L && now - player.lastRenderMs > 250L) {
            player.stop();
         }
      }
   }

   public void render(GuiGraphicsExtractor graphics, int width, int height) {
      long now = System.currentTimeMillis();
      lastRenderMs = now;
      if (startTime == 0L) {
         startTime = now;
         audio.start();
      }

      long elapsed = now - startTime;
      if (elapsed >= durationMs) {
         startTime = now;
         elapsed = 0L;
      }

      int frame = (int)(elapsed * (long)fps / 1000L);
      if (frame >= frames.length) {
         frame = frames.length - 1;
      }

      if (!upload(frame)) {
         return;
      }

      graphics.blit(RenderPipelines.GUI_TEXTURED, textureId, 0, 0, 0.0F, 0.0F, width, height, texW, texH, texW, texH, -1);
   }

   /** Puts the given frame on the GPU if it is not already there; false when it could not be read. */
   private boolean upload(int frame) {
      if (texture != null && uploadedFrame == frame) {
         return true;
      }

      NativeImage image = ModTextures.read(frames[frame]);
      if (image == null) {
         return texture != null;
      }

      if (texture != null && (texture.getPixels().getWidth() != image.getWidth() || texture.getPixels().getHeight() != image.getHeight())) {
         Minecraft.getInstance().getTextureManager().release(textureId);
         texture = null;
      }

      if (texture == null) {
         texture = new DynamicTexture(textureId::toString, image);
         Minecraft.getInstance().getTextureManager().register(textureId, texture);
      } else {
         texture.setPixels(image);
         texture.upload();
      }

      uploadedFrame = frame;
      return true;
   }

   public void stop() {
      startTime = 0L;
      audio.stop();
   }
}
