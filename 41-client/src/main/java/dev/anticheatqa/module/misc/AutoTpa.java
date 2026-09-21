package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AutoTpa extends Module {
   public final Module.TextSetting player = new Module.TextSetting("Player", "");
   public final Module.NumberSetting interval = new Module.NumberSetting("Interval Sec", 5.0, 1.0, 30.0, 1.0);
   private long last;

   public AutoTpa() {
      super("Auto TFA", "Periodically runs /tpa <player>.", Category.MISC);
      this.settings.add(this.player);
      this.settings.add(this.interval);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.method_1562() != null) {
         String n = this.player.get().trim();
         if (!n.isEmpty()) {
            long now = System.currentTimeMillis();
            if (!(now - this.last < this.interval.get() * 1000.0)) {
               this.last = now;
               c.method_1562().method_45730("tpa " + n);
            }
         }
      }
   }
}
