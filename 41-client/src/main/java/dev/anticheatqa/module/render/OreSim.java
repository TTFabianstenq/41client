package dev.anticheatqa.module.render;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class OreSim extends Module {
   public OreSim() {
      super("Ore Sim", "Estimates diamond chance in current chunk from scanned density.", Category.RENDER);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724.method_7353(class_2561.method_43470("\u00a7b[SC] Ore Sim: enable Sus Chunk Finder for live density scores."), false);
      }

      this.setEnabled(false);
   }
}
