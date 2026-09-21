package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class TotemOffhand extends Module {
   public TotemOffhand() {
      super("Totem Offhand", "Always keeps totem in offhand when available.", Category.COMBAT);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1761 != null && c.field_1755 == null) {
         if (!c.field_1724.method_6079().method_31574(class_1802.field_8288)) {
            for (int i = 9; i < 45; i++) {
               if (c.field_1724.field_7498.method_7611(i).method_7677().method_31574(class_1802.field_8288)) {
                  c.field_1761.method_2906(c.field_1724.field_7498.field_7763, i, 40, class_1713.field_7791, c.field_1724);
                  return;
               }
            }
         }
      }
   }
}
