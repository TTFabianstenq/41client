package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_310;

public class ItemDropper extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ticks", 2.0, 0.0, 10.0, 1.0);
   private int cd;
   private int slot = 9;

   public ItemDropper() {
      super("Item Dropper", "Drops inventory items one by one.", Category.DONUT);
      this.settings.add(this.delay);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1761 != null && c.field_1755 == null) {
         if (this.cd > 0) {
            this.cd--;
         } else {
            for (int i = this.slot; i < 45; i++) {
               if (!c.field_1724.field_7498.method_7611(i).method_7677().method_7960()) {
                  c.field_1761.method_2906(c.field_1724.field_7498.field_7763, i, 1, class_1713.field_7795, c.field_1724);
                  this.slot = i + 1;
                  this.cd = this.delay.get().intValue();
                  return;
               }
            }

            this.slot = 9;
            this.setEnabled(false);
         }
      }
   }
}
