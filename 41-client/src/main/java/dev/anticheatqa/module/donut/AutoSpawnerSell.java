package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class AutoSpawnerSell extends Module {
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 5.0, 1.0, 30.0, 1.0);
   private long last;

   public AutoSpawnerSell() {
      super("Auto Spawner Sell", "Drops bones and runs sell commands for spawner loot.", Category.DONUT);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         long now = System.currentTimeMillis();
         if (!(now - this.last < this.interval.get() * 1000.0)) {
            this.last = now;

            for (int i = 0; i < 9; i++) {
               if (c.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8606)
                  || c.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8511)) {
                  c.field_1724.method_31548().method_61496(i);
                  c.method_1562().method_45730("sell hand");
                  return;
               }
            }
         }
      }
   }
}
