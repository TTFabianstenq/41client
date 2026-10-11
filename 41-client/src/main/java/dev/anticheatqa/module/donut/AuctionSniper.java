package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AuctionSniper extends Module {
   public final Module.NumberSetting maxPrice = new Module.NumberSetting("Max Price", 10000.0, 1.0, 1.0E9, 1.0);
   public final Module.TextSetting item = new Module.TextSetting("Item Contains", "spawner");
   public final Module.NumberSetting interval = new Module.NumberSetting("Refresh Sec", 3.0, 1.0, 15.0, 1.0);
   private long last;

   public AuctionSniper() {
      super("Auction Sniper", "Periodically opens /ah search for a target item under max price.", Category.DONUT);
      this.settings.add(this.maxPrice);
      this.settings.add(this.item);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         long now = System.currentTimeMillis();
         if (!(now - this.last < this.interval.get() * 1000.0)) {
            this.last = now;
            String q = this.item.get().trim();
            if (!q.isEmpty()) {
               c.method_1562().method_45730("ah search " + q);
            }
         }
      }
   }
}
