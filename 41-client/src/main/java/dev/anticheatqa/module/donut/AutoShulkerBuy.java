package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AutoShulkerBuy extends Module {
   public final Module.NumberSetting price = new Module.NumberSetting("Max Price", 5000.0, 1.0, 1.0E9, 1.0);
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 5.0, 1.0, 30.0, 1.0);
   private long last;

   public AutoShulkerBuy() {
      super("Auto Shulker Buy", "Runs /ah search shulker on an interval for buying.", Category.DONUT);
      this.settings.add(this.price);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         long now = System.currentTimeMillis();
         if (!(now - this.last < this.interval.get() * 1000.0)) {
            this.last = now;
            c.method_1562().method_45730("ah search shulker");
         }
      }
   }
}
