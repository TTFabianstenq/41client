package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class TickMonitor extends Module {
   private long lastTime = System.nanoTime();
   private double tpsEstimate = 20.0;

   public TickMonitor() {
      super("Tick Monitor", "Rough client tick rate estimate.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      long now = System.nanoTime();
      double delta = (now - this.lastTime) / 1.0E9;
      this.lastTime = now;
      if (delta > 0.0) {
         double instant = 1.0 / delta;
         this.tpsEstimate = this.tpsEstimate * 0.9 + instant * 0.1;
      }
   }

   @Override
   public String getDisplay() {
      return String.format("Client TPS: %.1f", Math.min(20.0, this.tpsEstimate));
   }
}
