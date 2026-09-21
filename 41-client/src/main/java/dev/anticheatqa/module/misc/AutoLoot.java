package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class AutoLoot extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 4.0, 1.0, 8.0, 0.5);

   public AutoLoot() {
      super("Auto Loot", "Walks toward nearby dropped items.", Category.MISC);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1687 != null) {
         class_1542 best = null;
         double bd = this.range.get();

         for (class_1297 e : c.field_1687.method_18112()) {
            if (e instanceof class_1542 ie) {
               double d = c.field_1724.method_5739(ie);
               if (d < bd) {
                  bd = d;
                  best = ie;
               }
            }
         }

         if (best != null) {
            class_243 dir = new class_243(best.method_23317() - c.field_1724.method_23317(), 0.0, best.method_23321() - c.field_1724.method_23321())
               .method_1029()
               .method_1021(0.15);
            c.field_1724.method_5762(dir.field_1352, 0.0, dir.field_1350);
         }
      }
   }
}
