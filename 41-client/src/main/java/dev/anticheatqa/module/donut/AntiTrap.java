package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1297;
import net.minecraft.class_1531;
import net.minecraft.class_1688;
import net.minecraft.class_243;
import net.minecraft.class_310;

public class AntiTrap extends Module {
   public final Module.NumberSetting range = new Module.NumberSetting("Range", 3.0, 1.0, 6.0, 0.5);

   public AntiTrap() {
      super("Anti Trap", "Pushes you out of nearby armor stands / minecarts.", Category.DONUT);
      this.settings.add(this.range);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1687 != null) {
         for (class_1297 e : c.field_1687.method_18112()) {
            if ((e instanceof class_1531 || e instanceof class_1688) && !(c.field_1724.method_5739(e) > this.range.get())) {
               class_243 d = new class_243(c.field_1724.method_23317() - e.method_23317(), 0.2, c.field_1724.method_23321() - e.method_23321())
                  .method_1029()
                  .method_1021(0.4);
               c.field_1724.method_5762(d.field_1352, 0.2, d.field_1350);
            }
         }
      }
   }
}
