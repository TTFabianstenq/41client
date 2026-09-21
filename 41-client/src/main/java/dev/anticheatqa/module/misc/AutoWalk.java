package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class AutoWalk extends Module {
   public AutoWalk() {
      super("Auto Walk", "Holds forward movement key.", Category.MISC);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null) {
         c.field_1690.field_1894.method_23481(true);
      }
   }

   @Override
   protected void onDisable() {
      class_310 c = class_310.method_1551();
      if (c.field_1690 != null) {
         c.field_1690.field_1894.method_23481(false);
      }
   }
}
