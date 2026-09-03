package com.cryptix.util;

import net.minecraft.network.protocol.Packet;

public class QueuedPacket {
   public Packet packet;
   public long time;

   public QueuedPacket(Packet var1, long var2) {
      this.packet = var1;
      this.time = var2;
   }
}
