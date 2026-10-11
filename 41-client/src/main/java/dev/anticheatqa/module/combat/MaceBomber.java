package dev.anticheatqa.module.combat;

import dev.anticheatqa.module.Category;
import dev.anticheatqa.module.Module;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class MaceBomber extends Module {
   public MaceBomber() {
      super("Mace Bomber", "When holding mace and falling, attacks entity under crosshair for smash potential.", Category.COMBAT);
   }

   @Override
   public void onTick(class_310 c) {
      if (c.field_1724 != null && c.field_1761 != null && c.field_1755 == null) {
         if (c.field_1724.method_6047().method_31574(class_1802.field_49814)) {
            if (!(c.field_1724.field_6017 < 1.5)) {
               if (c.field_1765 instanceof class_3966 ehr) {
                  c.field_1761.method_2918(c.field_1724, ehr.method_17782());
                  c.field_1724.method_6104(class_1268.field_5808);
               }
            }
         }
      }
   }
}
