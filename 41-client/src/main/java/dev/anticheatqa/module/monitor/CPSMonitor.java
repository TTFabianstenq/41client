package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.class_310;

public class CPSMonitor extends Module {
   private final Deque<Long> leftClicks = new ArrayDeque<>();
   private final Deque<Long> rightClicks = new ArrayDeque<>();
   private boolean lastLeft;
   private boolean lastRight;

   public CPSMonitor() {
      super("CPS Monitor", "Measures actual left/right click activity.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      long now = System.currentTimeMillis();

      while (!this.leftClicks.isEmpty() && now - this.leftClicks.peekFirst() > 1000L) {
         this.leftClicks.pollFirst();
      }

      while (!this.rightClicks.isEmpty() && now - this.rightClicks.peekFirst() > 1000L) {
         this.rightClicks.pollFirst();
      }

      boolean left = client.field_1690.field_1886.method_1434();
      boolean right = client.field_1690.field_1904.method_1434();
      if (left && !this.lastLeft) {
         this.leftClicks.addLast(now);
      }

      if (right && !this.lastRight) {
         this.rightClicks.addLast(now);
      }

      this.lastLeft = left;
      this.lastRight = right;
   }

   public int getLeftCPS() {
      return this.leftClicks.size();
   }

   public int getRightCPS() {
      return this.rightClicks.size();
   }

   @Override
   public String getDisplay() {
      return "L-CPS: " + this.getLeftCPS() + "  R-CPS: " + this.getRightCPS();
   }
}
