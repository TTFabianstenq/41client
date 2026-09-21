package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_640;
import net.minecraft.class_642;

public class PingMonitor extends Module {
   private int ping = -1;

   public PingMonitor() {
      super("Ping Monitor", "Displays real server latency.", Category.MONITOR);
   }

   @Override
   public void onTick(class_310 client) {
      try {
         if (client.field_1724 == null) {
            this.ping = -1;
            return;
         }

         class_634 handler = client.method_1562();
         if (handler == null) {
            this.ping = 0;
            return;
         }

         int best = -1;
         class_640 entry = handler.method_2871(client.field_1724.method_5667());
         if (entry != null) {
            int lat = entry.method_2959();
            if (lat >= 0) {
               best = lat;
            }
         }

         class_642 info = client.method_1558();
         if (info != null && info.field_3758 > 0L && (best < 0 || info.field_3758 < best) && best <= 0) {
            best = (int)info.field_3758;
         }

         if (best < 0 && handler.method_48296() != null && handler.method_48296().method_10758()) {
            best = 0;
         }

         this.ping = best;
      } catch (Throwable var6) {
         this.ping = -1;
      }
   }

   @Override
   public String getDisplay() {
      if (this.ping < 0) {
         return "Ping: N/A";
      } else {
         return this.ping == 0 ? "Ping: 0ms (local)" : "Ping: " + this.ping + "ms";
      }
   }
}
