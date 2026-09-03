package com.cryptix.util;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/**
 * Plays one OGG resource through Java's own audio output (system volume), independent of
 * Minecraft's sound settings — audible even when in-game sound is off. Decoded once with
 * LWJGL's STBVorbis, then looped on a thread.
 */
public final class AudioTrack {
   /** While set, every track writes silence instead of its samples (the intro jingle owns the speakers). */
   public static volatile boolean muted = false;

   private final String resource;
   private final boolean loop;
   private final boolean respectMute;

   private volatile boolean playing = false;
   private Thread thread;
   private byte[] pcm;
   private int sampleRate;
   private int channels;

   public AudioTrack(String resource) {
      this(resource, true, true);
   }

   public AudioTrack(String resource, boolean loop, boolean respectMute) {
      this.resource = resource;
      this.loop = loop;
      this.respectMute = respectMute;
   }

   public boolean isPlaying() {
      return playing;
   }

   public synchronized void start() {
      stop();
      if (pcm == null && !decode()) {
         return;
      }
      playing = true;
      thread = new Thread(this::run, "cryptix-audio");
      thread.setDaemon(true);
      thread.start();
   }

   public synchronized void stop() {
      playing = false;
      if (thread != null) {
         thread.interrupt();
         thread = null;
      }
   }

   private boolean decode() {
      ByteBuffer data = null;
      try (InputStream in = AudioTrack.class.getResourceAsStream(resource)) {
         if (in == null) {
            System.out.println("[Cryptix] audio: resource missing " + resource);
            return false;
         }
         byte[] bytes = in.readAllBytes();
         data = MemoryUtil.memAlloc(bytes.length);
         data.put(bytes).flip();

         try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ch = stack.mallocInt(1);
            IntBuffer sr = stack.mallocInt(1);
            ShortBuffer samples = STBVorbis.stb_vorbis_decode_memory(data, ch, sr);
            if (samples == null) {
               System.out.println("[Cryptix] audio: STBVorbis decode failed for " + resource);
               return false;
            }
            channels = ch.get(0);
            sampleRate = sr.get(0);
            int n = samples.remaining();
            byte[] out = new byte[n * 2];
            for (int i = 0; i < n; i++) {
               short s = samples.get(i);
               out[i * 2] = (byte)(s & 0xFF);
               out[i * 2 + 1] = (byte)((s >> 8) & 0xFF);
            }
            pcm = out;
            return true;
         }
      } catch (Throwable t) {
         System.out.println("[Cryptix] audio decode threw " + t);
         t.printStackTrace();
         return false;
      } finally {
         if (data != null) {
            MemoryUtil.memFree(data);
         }
      }
   }

   private void run() {
      SourceDataLine line = null;
      try {
         AudioFormat fmt = new AudioFormat((float)sampleRate, 16, channels, true, false);
         line = AudioSystem.getSourceDataLine(fmt);
         line.open(fmt);
         line.start();
         byte[] buf = pcm;
         byte[] silence = new byte[8192];
         do {
            int off = 0;
            while (playing && off < buf.length) {
               int chunk = Math.min(silence.length, buf.length - off);
               if (muted && respectMute) {
                  line.write(silence, 0, chunk);
               } else {
                  line.write(buf, off, chunk);
               }
               off += chunk;
            }
         } while (playing && loop);

         playing = false;
      } catch (Throwable t) {
         System.out.println("[Cryptix] audio playback threw " + t);
         t.printStackTrace();
      } finally {
         if (line != null) {
            line.stop();
            line.flush();
            line.close();
         }
      }
   }
}
