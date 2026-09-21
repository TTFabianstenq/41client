package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.util.LogManager;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class TeleportLogger extends Module {
   public final Module.NumberSetting threshold = new Module.NumberSetting("Jump Threshold (m)", 3.0, 0.5, 32.0, 0.5);
   public final Module.BooleanSetting logToFile = new Module.BooleanSetting("Log To File", true);
   private class_243 lastPos;
   private double lastJump;
   private int jumpCount;
   private int ticker;

   public TeleportLogger() {
      super("Teleport Logger", "Flags sudden position jumps (setback / teleport / lag). Logs jumps.", Category.MONITOR);
      this.settings.add(this.threshold);
      this.settings.add(this.logToFile);
   }

   @Override
   protected void onEnable() {
      this.lastPos = null;
      this.lastJump = 0.0;
      this.jumpCount = 0;
      this.ticker = 0;
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null && client.field_1687 != null) {
         class_243 now = new class_243(client.field_1724.method_23317(), client.field_1724.method_23318(), client.field_1724.method_23321());
         if (this.lastPos == null) {
            this.lastPos = now;
         } else {
            double dx = now.field_1352 - this.lastPos.field_1352;
            double dy = now.field_1351 - this.lastPos.field_1351;
            double dz = now.field_1350 - this.lastPos.field_1350;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double thr = this.threshold.get();
            if (dist >= thr) {
               this.lastJump = dist;
               this.jumpCount++;
               String msg = String.format(
                  "TELEPORT/JUMP %.2fm  from (%.1f,%.1f,%.1f) -> (%.1f,%.1f,%.1f)",
                  dist,
                  this.lastPos.field_1352,
                  this.lastPos.field_1351,
                  this.lastPos.field_1350,
                  now.field_1352,
                  now.field_1351,
                  now.field_1350
               );
               if (this.logToFile.get()) {
                  LogManager.log(msg);
               }
            }

            this.lastPos = now;
            this.ticker++;
         }
      } else {
         this.lastPos = null;
      }
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         return this.jumpCount == 0 ? "TP-Log:0 jumps" : String.format("TP-Log:%d  last:%.1fm", this.jumpCount, this.lastJump);
      }
   }
}
