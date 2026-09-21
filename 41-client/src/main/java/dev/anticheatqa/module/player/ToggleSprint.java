package dev.anticheatqa.module.player;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_310;

public class ToggleSprint extends Module {
   public ToggleSprint() {
      super("Sprint", "Keeps sprint held while moving forward.", Category.MISC);
   }

   @Override
   public void onTick(class_310 client) {
      if (client.field_1724 != null) {
         if (client.field_1724.field_6250 > 0.1F && !client.field_1724.method_5715() && !client.field_1724.method_6115()) {
            client.field_1724.method_5728(true);
         }
      }
   }
}
