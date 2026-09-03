package com.cryptix.util;

import net.minecraft.network.protocol.Packet;

public class TimedPacket {
   public final Packet f36;
   private final long f757;

   public TimedPacket(Packet var1, long var2) {
      this.f36 = var1;
      this.f757 = System.currentTimeMillis() + var2;
   }

   public boolean m229() {
      return System.currentTimeMillis() >= this.f757;
   }
}
