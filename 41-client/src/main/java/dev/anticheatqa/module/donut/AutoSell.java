package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AutoSell extends Module {
   public final Module.NumberSetting price = new Module.NumberSetting("Price", 1000.0, 1.0, 1000000.0, 1.0);
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 3.0, 1.0, 30.0, 1.0);
   private long last;

   public AutoSell() {
      super("Auto Sell", "Runs /ah sell <price> for held item on an interval.", Category.DONUT);
      this.settings.add(this.price);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         if (!c.field_1724.method_6047().method_7960()) {
            long now = System.currentTimeMillis();
            if (!(now - this.last < this.interval.get() * 1000.0)) {
               this.last = now;
               c.method_1562().method_45730("ah sell " + this.price.get().intValue());
            }
         }
      }
   }
}
