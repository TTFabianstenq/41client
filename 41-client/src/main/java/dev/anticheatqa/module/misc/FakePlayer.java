package dev.anticheatqa.module.misc;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class FakePlayer extends Module {
   public FakePlayer() {
      super("Fakeplayer", "Spawns a local client-side reference at your position (coords only).", Category.MISC);
   }

   @Override
   protected void onEnable() {
      class_310 c = class_310.method_1551();
      if (c.field_1724 != null) {
         c.field_1724
            .method_7353(
               class_2561.method_43470(
                  String.format(
                     "\u00a77[SC] Fake marker @ %.1f %.1f %.1f", c.field_1724.method_23317(), c.field_1724.method_23318(), c.field_1724.method_23321()
                  )
               ),
               false
            );
      }

      this.setEnabled(false);
   }
}
