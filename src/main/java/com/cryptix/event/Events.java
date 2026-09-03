package com.cryptix.event;

import com.cryptix.event.impl.EventEntityOutline;
import com.cryptix.event.impl.EventHurtCamera;
import com.cryptix.event.impl.EventMouseButton;
import com.cryptix.event.impl.EventMoveInput;
import com.cryptix.event.impl.EventPacketReceive;
import com.cryptix.event.impl.EventPacketSend;
import com.cryptix.event.impl.EventPostMotion;
import com.cryptix.event.impl.EventPostMoveInput;
import com.cryptix.event.impl.EventPreMotion;
import com.cryptix.event.impl.EventRender2D;
import com.cryptix.event.impl.EventRenderNameTag;
import com.cryptix.event.impl.EventRotation;
import com.cryptix.event.impl.EventSlowdown;
import com.cryptix.event.impl.EventSound;
import com.cryptix.event.impl.EventSprint;
import com.cryptix.event.impl.EventTick;
import java.util.List;
import net.minecraft.client.Minecraft;

public class Events {
   public static final EventPreMotion f1 = new EventPreMotion();
   public static final EventSprint f12 = new EventSprint();
   public static final EventMouseButton f15 = new EventMouseButton();
   public static final EventTick f4 = new EventTick();
   public static final EventPostMoveInput f8 = new EventPostMoveInput();
   public static final EventSound f16 = new EventSound();
   public static final EventPostMotion f2 = new EventPostMotion();
   public static final EventHurtCamera f6 = new EventHurtCamera();
   public static final EventPacketReceive f11 = new EventPacketReceive();
   public static final EventRender2D f5 = new EventRender2D();
   public static final EventSlowdown f9 = new EventSlowdown();
   public static final EventMoveInput f7 = new EventMoveInput();
   public static final EventRotation f3 = new EventRotation();
   public static final EventRenderNameTag f13 = new EventRenderNameTag();
   public static final EventEntityOutline f14 = new EventEntityOutline();
   public static final EventPacketSend f10 = new EventPacketSend();
   // Must come AFTER all event fields above: List.of rejects nulls, and the
   // decompiler had ordered this before some referenced fields were initialized.
   public static final List<Event> f17 = List.of(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16);

   public static void m11() {
      if (Minecraft.getInstance().player != null) {
         for (Event var1 : f17) {
            var1.sortModules();
         }
      }
   }
}
