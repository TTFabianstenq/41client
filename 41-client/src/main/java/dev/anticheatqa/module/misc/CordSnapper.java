package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class CordSnapper extends Module {
   public CordSnapper() {
      super("Cord Snapper", "Copies current coordinates to clipboard.", Category.MISC);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         String s = String.format("%d %d %d", c.field_1724.method_31477(), c.field_1724.method_31478(), c.field_1724.method_31479());
         c.field_1774.method_1455(s);
         c.field_1724.method_7353(class_2561.method_43470("\u00a7a[SC] Cords copied: \u00a7f" + s), false);
      }

      this.setEnabled(false);
   }
}
