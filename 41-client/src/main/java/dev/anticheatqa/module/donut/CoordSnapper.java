package dev.anticheatqa.module.donut;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class CoordSnapper extends Module {
   public CoordSnapper() {
      super("Coord Snapper", "Copies your coordinates to clipboard when toggled.", Category.MISC);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 == null) {
         this.setEnabled(false);
      } else {
         String s = String.format("%d %d %d", c.field_1724.method_31477(), c.field_1724.method_31478(), c.field_1724.method_31479());
         c.field_1774.method_1455(s);
         c.field_1724.method_7353(class_2561.method_43470("\u00a7b[SC] Coords copied: \u00a7f" + s), false);
         this.setEnabled(false);
      }
   }
}
