package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1657;
import net.minecraft.class_310;

public class PlayerAlert extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Alert Range", 64.0, 16.0, 128.0, 8.0);
   public final Module.NumberSetting closeRange = new Module.NumberSetting("Close Range", 24.0, 8.0, 64.0, 4.0);
   private int nearby;
   private int close;
   private String closestName = "-";
   private double closestDist = 999.0;

   public PlayerAlert() {
      super("Player Alert", "Counts nearby players + closest name/distance. Raid awareness.", Category.MONITOR);
      this.settings.add(this.range);
      this.settings.add(this.closeRange);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1687 != null && client.field_1724 != null) {
         double max = this.range.get();
         double closeMax = this.closeRange.get();
         int n = 0;
         int c = 0;
         String best = "-";
         double bestD = 999.0;

         for (class_1657 p : client.field_1687.method_18456()) {
            if (p != client.field_1724 && p.method_5805()) {
               double d = client.field_1724.method_5739(p);
               if (!(d > max)) {
                  n++;
                  if (d < closeMax) {
                     c++;
                  }

                  if (d < bestD) {
                     bestD = d;
                     best = p.method_5477().getString();
                  }
               }
            }
         }

         this.nearby = n;
         this.close = c;
         this.closestName = best;
         this.closestDist = bestD == 999.0 ? 0.0 : bestD;
      } else {
         this.nearby = this.close = 0;
         this.closestName = "-";
         this.closestDist = 999.0;
      }
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         return this.nearby == 0 ? "Players:0" : String.format("Players:%d close:%d | %s %.0fm", this.nearby, this.close, this.closestName, this.closestDist);
      }
   }
}
