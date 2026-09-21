package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;

public class ReachMonitor extends Module {
   private String lastTarget = "None";
   private double lastDistance;

   public ReachMonitor() {
      super("Reach Monitor", "Measures actual distance to targeted entity.", Category.RENDER);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1765 != null) {
         if (client.field_1765.method_17783() == class_240.field_1331) {
            class_3966 ehr = (class_3966)client.field_1765;
            this.lastTarget = ehr.method_17782().method_5477().getString();
            this.lastDistance = client.field_1724.method_33571().method_1022(ehr.method_17784());
         } else {
            this.lastTarget = "None";
            this.lastDistance = 0.0;
         }
      } else {
         this.lastTarget = "None";
         this.lastDistance = 0.0;
      }
   }

   @Override
   public String getDisplay() {
      return String.format("Target: %s  Distance: %.2f", this.lastTarget, this.lastDistance);
   }

   public double getLastDistance() {
      return this.lastDistance;
   }
}
