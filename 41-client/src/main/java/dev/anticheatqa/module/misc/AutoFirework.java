package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class AutoFirework extends Module {
   public final Module.NumberSetting delay = new Module.NumberSetting("Delay Ticks", 10.0, 1.0, 40.0, 1.0);
   private int cd;

   public AutoFirework() {
      super("Auto Firework", "Uses firework rockets while gliding with elytra.", Category.MISC);
      this.settings.add(this.delay);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1761 != null) {
         if (c.field_1724.method_6128()) {
            if (this.cd > 0) {
               this.cd--;
            } else {
               boolean has = c.field_1724.method_6047().method_31574(class_1802.field_8639) || c.field_1724.method_6079().method_31574(class_1802.field_8639);
               if (!has) {
                  for (int i = 0; i < 9; i++) {
                     if (c.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8639)) {
                        c.field_1724.method_31548().method_61496(i);
                        has = true;
                        break;
                     }
                  }
               }

               if (has) {
                  class_1268 h = c.field_1724.method_6047().method_31574(class_1802.field_8639) ? class_1268.field_5808 : class_1268.field_5810;
                  c.field_1761.method_2919(c.field_1724, h);
                  this.cd = this.delay.get().intValue();
               }
            }
         }
      }
   }
}
