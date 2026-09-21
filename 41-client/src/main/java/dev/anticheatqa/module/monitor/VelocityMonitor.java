package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class VelocityMonitor extends Module {
   private class_243 velocity = class_243.field_1353;
   private double lastMagnitude;

   public VelocityMonitor() {
      super("Velocity Monitor", "Displays player velocity (useful after knockback).", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         this.velocity = client.field_1724.method_18798();
         this.lastMagnitude = this.velocity.method_1033();
      }
   }

   @Override
   public String getDisplay() {
      return String.format("Vel: %.3f %.3f %.3f  Mag:%.3f", this.velocity.field_1352, this.velocity.field_1351, this.velocity.field_1350, this.lastMagnitude);
   }
}
