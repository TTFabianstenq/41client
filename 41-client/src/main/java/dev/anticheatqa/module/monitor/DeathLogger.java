package dev.anticheatqa.module.monitor;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import dev.anticheatqa.util.LogManager;
import net.minecraft.class_2338;
import net.minecraft.class_310;

public class DeathLogger extends Module {
   private boolean wasAlive = true;
   private int deathCount;
   private String lastDeath = "-";
   public final Module.BooleanSetting logToFile = new Module.BooleanSetting("Log To File", true);

   public DeathLogger() {
      super("Death Logger", "Logs XYZ when you die. Recover gear after raids.", Category.MONITOR);
      this.settings.add(this.logToFile);
   }

   @Override
   protected void onEnable() {
      this.wasAlive = true;
      class_310 client = class_310.method_1551();
      if (client.field_1724 != null) {
         this.wasAlive = client.field_1724.method_5805();
      }
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         boolean alive = client.field_1724.method_5805() && client.field_1724.method_6032() > 0.0F;
         if (this.wasAlive && !alive) {
            class_2338 pos = client.field_1724.method_24515();
            this.lastDeath = String.format(
               "%d %d %d (%s)",
               pos.method_10263(),
               pos.method_10264(),
               pos.method_10260(),
               client.field_1687 != null ? client.field_1687.method_27983().method_29177().method_12832() : "?"
            );
            this.deathCount++;
            String msg = "DEATH at " + this.lastDeath;
            if (this.logToFile.get()) {
               LogManager.log(msg);
            }
         }

         this.wasAlive = alive;
      }
   }

   @Override
   public String getDisplay() {
      if (!this.isEnabled()) {
         return "";
      } else {
         return this.deathCount == 0 ? "Deaths:0" : "Deaths:" + this.deathCount + " @ " + this.lastDeath;
      }
   }
}
