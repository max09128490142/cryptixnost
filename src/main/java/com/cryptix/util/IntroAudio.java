package com.cryptix.util;

/**
 * Plays the intro jingle once when the client starts. Every menu video is muted for as long as it
 * runs, then the videos get their sound back.
 */
public final class IntroAudio {
   private static final AudioTrack TRACK = new AudioTrack("/assets/cryptix/sounds/intro.ogg", false, false);

   private static boolean started = false;
   private static boolean finished = false;

   private IntroAudio() {
   }

   /** Called every client tick. */
   public static void tick() {
      if (!started) {
         started = true;
         AudioTrack.muted = true;
         TRACK.start();
         return;
      }

      if (!finished && !TRACK.isPlaying()) {
         finished = true;
         AudioTrack.muted = false;
      }
   }
}
