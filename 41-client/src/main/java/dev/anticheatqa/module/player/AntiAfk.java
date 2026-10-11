package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AntiAfk extends Module {
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 30.0, 5.0, 120.0, 1.0);
   private long last;

   public AntiAfk() {
      super("Anti AFK", "Small periodic movements to avoid AFK kicks.", Category.MISC);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         long now = System.currentTimeMillis();
         if (!(now - this.last < this.interval.get() * 1000.0)) {
            this.last = now;
            client.field_1724.method_36456(client.field_1724.method_36454() + 0.5F);
            if (client.field_1724.method_24828()) {
               client.field_1724.method_6043();
            }
         }
      }
   }
}
