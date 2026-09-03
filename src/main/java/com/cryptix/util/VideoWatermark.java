package com.cryptix.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

/**
 * The CRYPTIXNOST wordmark with a video running inside the letters.
 *
 * <p>The letters come from the 5x7 bitmap font below, blown up into an alpha mask; every video
 * frame is sampled through that mask into one texture, so the video only shows where a letter is.
 * The whole thing is capped at an eighth of the screen area.
 */
public final class VideoWatermark {
   private static final String TEXT = "CRYPTIXNOST";
   private static final int GLYPH_W = 5;
   private static final int GLYPH_H = 7;
   private static final int GLYPH_GAP = 1;
   /** Mask pixels per font pixel — keeps the letter edges crisp once the texture is stretched. */
   private static final int MASK_SCALE = 8;
   private static final int FRAMES = 200;
   private static final int FPS = 10;
   private static final Identifier TEXTURE_ID = Identifier.fromNamespaceAndPath("cryptix", "video/watermark");

   // 5x7 glyphs for the letters CRYPTIXNOST needs, row by row, MSB-left.
   private static final String[] C = {"01110", "10001", "10000", "10000", "10000", "10001", "01110"};
   private static final String[] R = {"11110", "10001", "10001", "11110", "10100", "10010", "10001"};
   private static final String[] Y = {"10001", "10001", "01010", "00100", "00100", "00100", "00100"};
   private static final String[] P = {"11110", "10001", "10001", "11110", "10000", "10000", "10000"};
   private static final String[] T = {"11111", "00100", "00100", "00100", "00100", "00100", "00100"};
   private static final String[] I = {"11111", "00100", "00100", "00100", "00100", "00100", "11111"};
   private static final String[] X = {"10001", "10001", "01010", "00100", "01010", "10001", "10001"};
   private static final String[] N = {"10001", "11001", "10101", "10011", "10001", "10001", "10001"};
   private static final String[] O = {"01110", "10001", "10001", "10001", "10001", "10001", "01110"};
   private static final String[] S = {"01111", "10000", "10000", "01110", "00001", "00001", "11110"};

   private static boolean[][] mask;
   private static int maskW;
   private static int maskH;

   private static DynamicTexture texture;
   private static int uploadedFrame = -1;
   private static long startTime = 0L;

   private VideoWatermark() {
   }

   public static void render(GuiGraphicsExtractor graphics) {
      buildMask();

      long now = System.currentTimeMillis();
      if (startTime == 0L) {
         startTime = now;
      }

      long durationMs = (long)FRAMES * 1000L / (long)FPS;
      long elapsed = (now - startTime) % durationMs;
      int frame = Math.min((int)(elapsed * (long)FPS / 1000L), FRAMES - 1);
      if (!upload(frame)) {
         return;
      }

      int screenW = graphics.guiWidth();
      int screenH = graphics.guiHeight();

      // Start from 40% of the screen width, then shrink until the wordmark covers at most 1/8 of it.
      int drawW = (int)(screenW * 0.4F);
      int drawH = Math.max(1, drawW * maskH / maskW);
      long budget = (long)screenW * (long)screenH / 8L;
      while (drawW > 16 && (long)drawW * (long)drawH > budget) {
         drawW -= 4;
         drawH = Math.max(1, drawW * maskH / maskW);
      }

      int x = (screenW - drawW) / 2;
      graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE_ID, x, 4, 0.0F, 0.0F, drawW, drawH, maskW, maskH, maskW, maskH, -1);
   }

   /** Samples the frame through the letter mask and pushes the result to the GPU. */
   private static boolean upload(int frame) {
      if (texture != null && uploadedFrame == frame) {
         return true;
      }

      Identifier frameId = Identifier.fromNamespaceAndPath("cryptix", String.format("textures/videowatermark/frame_%04d.png", frame + 1));
      NativeImage source = ModTextures.read(frameId);
      if (source == null) {
         return texture != null;
      }

      NativeImage out = new NativeImage(maskW, maskH, false);
      try {
         for (int y = 0; y < maskH; y++) {
            int sy = y * source.getHeight() / maskH;
            for (int x = 0; x < maskW; x++) {
               if (!mask[y][x]) {
                  out.setPixel(x, y, 0);
                  continue;
               }

               int sx = x * source.getWidth() / maskW;
               out.setPixel(x, y, source.getPixel(sx, sy) | 0xFF000000);
            }
         }
      } finally {
         source.close();
      }

      if (texture == null) {
         texture = new DynamicTexture(TEXTURE_ID::toString, out);
         Minecraft.getInstance().getTextureManager().register(TEXTURE_ID, texture);
      } else {
         texture.setPixels(out);
         texture.upload();
      }

      uploadedFrame = frame;
      return true;
   }

   private static void buildMask() {
      if (mask != null) {
         return;
      }

      int cols = TEXT.length() * (GLYPH_W + GLYPH_GAP) - GLYPH_GAP;
      maskW = cols * MASK_SCALE;
      maskH = GLYPH_H * MASK_SCALE;
      mask = new boolean[maskH][maskW];

      for (int i = 0; i < TEXT.length(); i++) {
         String[] glyph = glyph(TEXT.charAt(i));
         int originX = i * (GLYPH_W + GLYPH_GAP);
         for (int gy = 0; gy < GLYPH_H; gy++) {
            for (int gx = 0; gx < GLYPH_W; gx++) {
               if (glyph[gy].charAt(gx) != '1') {
                  continue;
               }

               for (int y = 0; y < MASK_SCALE; y++) {
                  for (int x = 0; x < MASK_SCALE; x++) {
                     mask[(gy * MASK_SCALE) + y][((originX + gx) * MASK_SCALE) + x] = true;
                  }
               }
            }
         }
      }
   }

   private static String[] glyph(char c) {
      switch (c) {
         case 'C':
            return C;
         case 'R':
            return R;
         case 'Y':
            return Y;
         case 'P':
            return P;
         case 'T':
            return T;
         case 'I':
            return I;
         case 'X':
            return X;
         case 'N':
            return N;
         case 'O':
            return O;
         case 'S':
            return S;
         default:
            return new String[] {"00000", "00000", "00000", "00000", "00000", "00000", "00000"};
      }
   }
}
